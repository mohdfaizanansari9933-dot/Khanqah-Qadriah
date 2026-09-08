package com.example.data.model

data class SalamItem(
    val id: String,
    val titleUrdu: String,
    val titleEnglish: String,
    val poetUrdu: String, // Shayar / Akabir
    val category: String, // Salam, Manqabat, Qasidah, Durood
    val descriptionUrdu: String,
    val verses: List<SalamVerse>,
    val audioUrl: String = ""
)

data class SalamVerse(
    val verseNumber: Int,
    val urduText: String,
    val transliteration: String = "",
    val englishMeaning: String = ""
)
