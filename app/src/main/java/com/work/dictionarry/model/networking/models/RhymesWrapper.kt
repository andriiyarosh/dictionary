package com.work.dictionarry.model.networking.models

data class RhymesWrapper(
    val word: String,
    val rhymes: Rhymes
): Addition {
    override fun getData(): List<String> {
        return rhymes.rhymes
    }
}