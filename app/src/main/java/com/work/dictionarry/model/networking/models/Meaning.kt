package com.work.dictionarry.model.networking.models

import com.google.gson.annotations.SerializedName

data class Meaning (
    @SerializedName(value = "definition") val definition: String
)