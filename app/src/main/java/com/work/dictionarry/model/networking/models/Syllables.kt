package com.work.dictionarry.model.networking.models

import com.google.gson.annotations.SerializedName

data class Syllables(
    @SerializedName(value = "count") val count: Int,
    @SerializedName(value = "list") val list: List<String>
)