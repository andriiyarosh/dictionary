package com.work.dictionarry.model.networking.models

data class Synonyms(
    val word: String,
    val synonyms: List<String>
): Addition {
    override fun getData(): List<String> {
        return synonyms
    }
}