package com.work.dictionarry.model.networking.models

import com.google.gson.annotations.SerializedName

data class UrbanWord(
    @SerializedName("definition") val definition: String,
    @SerializedName("word") val word: String
)