package com.work.dictionarry.dagger

import com.work.dictionarry.model.repository.WordsRepository
import com.work.dictionarry.model.repository.WordsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ComponentModule {

    @Binds
    abstract fun bindWordsRepository(wordsRepository: WordsRepositoryImpl): WordsRepository
}