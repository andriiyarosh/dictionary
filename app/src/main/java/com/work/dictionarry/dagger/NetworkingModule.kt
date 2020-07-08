package com.work.dictionarry.dagger

import com.work.dictionarry.model.networking.retrofit.NetworkService
import com.work.dictionarry.model.networking.retrofit.UrbanDictionaryApi
import com.work.dictionarry.model.networking.retrofit.WordsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkingModule {

    @Singleton
    @Provides
    @Named("Word")
    fun provideRetrofit() = NetworkService.wordsApiRetrofitClient

    @Singleton
    @Provides
    @Named("Urban")
    fun provideRetrofitUrban() = NetworkService.urbanDictionaryRetrofitClient


    @Singleton
    @Provides
    @Named("Word")
    fun provideWordsApi(@Named("Word")retrofit: Retrofit) =
        retrofit.create(WordsApi::class.java)

    @Singleton
    @Provides
    @Named("Urban")
    fun provideUrbanDictionaryApi(@Named("Urban")retrofit: Retrofit) =
        retrofit.create(UrbanDictionaryApi::class.java)
}