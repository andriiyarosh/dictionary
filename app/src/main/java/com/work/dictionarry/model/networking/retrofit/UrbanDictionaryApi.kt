package com.work.dictionarry.model.networking.retrofit

import com.work.dictionarry.model.networking.models.UrbanWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UrbanDictionaryApi {

    @GET("define")
    fun getWordDefinitions(@Query("term") word: String): Call<UrbanWrapper>
}