package com.work.dictionarry.presentation.info

import android.icu.text.MessagePattern
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.work.dictionarry.dagger.ComponentHolder
import com.work.dictionarry.model.networking.models.UrbanWord
import com.work.dictionarry.model.networking.models.Word
import com.work.dictionarry.model.networking.retrofit.NetworkService
import com.work.dictionarry.model.networking.retrofit.WordsApi
import com.work.dictionarry.model.repository.WordsRepository
import com.work.dictionarry.model.repository.WordsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InfoViewModel @Inject constructor() : ViewModel() {

    val loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    val definitions: MutableLiveData<List<String>> = MutableLiveData()

    @Inject
    lateinit var wordsRepository: WordsRepository

    init {
        ComponentHolder.getApplicationComponent().inject(this)
    }

    fun getWordInfo(word: String, type: Int) {
        loadingState.value = LoadingState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (type) {
                ApiType.URBAN.ordinal -> {
                    handleApiResponse(wordsRepository.findWord(word), false)
                    handleUrbanApiResponse(wordsRepository.findWordUrban(word))
                }
                ApiType.WORD_API.ordinal -> handleApiResponse(wordsRepository.findWord(word), true)
            }
        }
    }

    private fun handleApiResponse(
        responseState: WordsRepository.ResponseState,
        setDefinitions: Boolean = false
    ) {
        when (responseState) {
            is WordsRepository.ResponseState.ResponseError -> loadingState.postValue(LoadingState.Error)
            is WordsRepository.ResponseState.ResponseSuccess -> {
                loadingState.postValue(responseState.word?.let { LoadingState.Loaded(it) }
                    ?: LoadingState.Error)
                if (setDefinitions)
                    definitions.postValue(responseState.word?.let { it.meanings.map { it.definition } } ?: emptyList())
            }
        }
    }

    private fun handleUrbanApiResponse(responseState: WordsRepository.ResponseStateUrban) {
        when (responseState) {
            is WordsRepository.ResponseStateUrban.ResponseError -> loadingState.postValue(LoadingState.Error)
            is WordsRepository.ResponseStateUrban.ResponseSuccess -> definitions.postValue(responseState.words.map { it.definition })
        }
    }

    sealed class LoadingState {
        class Loaded(val word: Word) : LoadingState()
        object Loading : LoadingState()
        object Error : LoadingState()
    }

    enum class ApiType {
        URBAN, WORD_API
    }
}