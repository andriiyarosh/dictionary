package com.work.dictionarry.model.networking.models

import com.google.gson.annotations.SerializedName

data class Pronunciation(
    @SerializedName(value = "all") val pronunciation: String
)