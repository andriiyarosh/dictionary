package com.work.dictionarry.model.repository

import com.work.dictionarry.model.networking.models.RhymesWrapper
import com.work.dictionarry.model.networking.models.Synonyms
import com.work.dictionarry.model.networking.models.UrbanWord
import com.work.dictionarry.model.networking.models.Word

interface WordsRepository {

    suspend fun findWordUrban(word: String): ResponseStateUrban
    suspend fun findWord(word: String): ResponseState
    suspend fun findSynonyms(word: String): Synonyms?
    suspend fun findRhymes(word: String): RhymesWrapper?

    sealed class ResponseState {
        object ResponseError : ResponseState()
        class ResponseSuccess(val word: Word?): ResponseState()
    }

    sealed class ResponseStateUrban {
        object ResponseError : ResponseStateUrban()
        class ResponseSuccess(val words: List<UrbanWord>): ResponseStateUrban()
    }
}