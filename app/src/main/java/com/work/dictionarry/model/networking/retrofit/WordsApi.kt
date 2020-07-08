package com.work.dictionarry.model.networking.retrofit

import com.work.dictionarry.model.networking.models.RhymesWrapper
import com.work.dictionarry.model.networking.models.Synonyms
import com.work.dictionarry.model.networking.models.Word
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WordsApi {

    @GET("words/{word}")
    fun getWord(@Path(value = "word") word: String): Call<Word>

    @GET("words/{word}/synonyms")
    fun getSynonyms(@Path(value = "word") word: String): Call<Synonyms>

    @GET("words/{word}/rhymes")
    fun getRhymes(@Path(value = "word") word: String): Call<RhymesWrapper>
}