package com.work.dictionarry.dagger

import com.work.dictionarry.presentation.addition.WordAdditionsViewModel
import com.work.dictionarry.presentation.info.InfoViewModel
import com.work.dictionarry.presentation.search.SearchViewModel
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkingModule::class,
        ComponentModule::class]
)
@Singleton
interface Component {

    fun inject(viewModel: WordAdditionsViewModel)
    fun inject(viewModel: SearchViewModel)
    fun inject(viewModel: InfoViewModel)
}