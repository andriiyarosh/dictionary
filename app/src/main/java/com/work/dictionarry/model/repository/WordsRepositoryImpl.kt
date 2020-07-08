package com.work.dictionarry.model.repository

import com.work.dictionarry.model.networking.models.RhymesWrapper
import com.work.dictionarry.model.networking.models.Synonyms
import com.work.dictionarry.model.networking.models.UrbanWrapper
import com.work.dictionarry.model.networking.models.Word
import com.work.dictionarry.model.networking.retrofit.UrbanDictionaryApi
import com.work.dictionarry.model.networking.retrofit.WordsApi
import com.work.dictionarry.model.repository.WordsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.work.dictionarry.model.repository.WordsRepository.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class WordsRepositoryImpl @Inject constructor(@Named("Word") private val wordsApi: WordsApi, @Named("Urban") private val urbanDictionaryApi: UrbanDictionaryApi) :
    WordsRepository {

    override suspend fun findWordUrban(word: String): WordsRepository.ResponseStateUrban = suspendCoroutine{
        urbanDictionaryApi.getWordDefinitions(word).enqueue(object : Callback<UrbanWrapper> {
            override fun onFailure(call: Call<UrbanWrapper>, t: Throwable) {
                t.printStackTrace()
                it.resume(WordsRepository.ResponseStateUrban.ResponseError)
            }

            override fun onResponse(call: Call<UrbanWrapper>, response: Response<UrbanWrapper>) {
                it.resume(WordsRepository.ResponseStateUrban.ResponseSuccess(response.body()?.list?: emptyList()))
            }
        })
    }

    override suspend fun findWord(word: String): ResponseState = suspendCoroutine {
        wordsApi.getWord(word).enqueue(object : Callback<Word> {
            override fun onFailure(call: Call<Word>, t: Throwable) {
                t.printStackTrace()
                it.resume(ResponseState.ResponseError)
            }

            override fun onResponse(call: Call<Word>, response: Response<Word>) {
                it.resume(ResponseState.ResponseSuccess(response.body()))
            }
        })
    }

    override suspend fun findSynonyms(word: String): Synonyms? = suspendCoroutine { continuation ->
        wordsApi.getSynonyms(word).enqueue(object : Callback<Synonyms> {
            override fun onFailure(call: Call<Synonyms>, t: Throwable) {
                t.printStackTrace()
                continuation.resumeWith(Result.failure(t))
            }

            override fun onResponse(call: Call<Synonyms>, response: Response<Synonyms>) {
                continuation.resumeWith(Result.success(response.body()))
            }
        })
    }

    override suspend fun findRhymes(word: String): RhymesWrapper? = suspendCoroutine{
        wordsApi.getRhymes(word).enqueue(object : Callback<RhymesWrapper>{
            override fun onFailure(call: Call<RhymesWrapper>, t: Throwable) {
                t.printStackTrace()
                it.resumeWith(Result.failure(t))
            }

            override fun onResponse(call: Call<RhymesWrapper>, response: Response<RhymesWrapper>) {
                it.resumeWith(Result.success(response.body()))
            }
        })
    }
}