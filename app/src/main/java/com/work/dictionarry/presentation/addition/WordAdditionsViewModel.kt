package com.work.dictionarry.presentation.addition

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.work.dictionarry.dagger.ComponentHolder
import com.work.dictionarry.model.networking.models.Addition
import com.work.dictionarry.model.networking.models.Word
import com.work.dictionarry.model.networking.retrofit.NetworkService
import com.work.dictionarry.model.networking.retrofit.WordsApi
import com.work.dictionarry.model.repository.WordsRepository
import com.work.dictionarry.model.repository.WordsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WordAdditionsViewModel: ViewModel() {

    val loadingState: MutableLiveData<LoadingState> = MutableLiveData()

    @Inject
    lateinit var wordsRepository: WordsRepository

    init {
        ComponentHolder.getApplicationComponent().inject(this)
    }

    fun findRhymes(word: String) {
        loadingState.value = LoadingState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            wordsRepository.findRhymes(word)?.let {
                loadingState.postValue(LoadingState.Loaded(it))
            } ?: run { loadingState.postValue(LoadingState.Error) }
        }
    }

    fun findSynonyms(word: String) {
        loadingState.value = LoadingState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            wordsRepository.findSynonyms(word)?.let {
                loadingState.postValue(LoadingState.Loaded(it))
            } ?: run { loadingState.postValue(LoadingState.Error) }
        }
    }

    sealed class LoadingState {
        object Loading : LoadingState()
        object Error : LoadingState()
        class Loaded(val data: Addition) : LoadingState()
    }
}