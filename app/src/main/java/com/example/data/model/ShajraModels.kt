package com.example.data.model

data class ShajraElder(
    val stepNumber: Int,
    val nameUrdu: String,
    val nameHindi: String,
    val nameEnglish: String,
    val titleUrdu: String,
    val restingPlaceUrdu: String, // Mazar / Maqam
    val restingPlaceEnglish: String,
    val eraHijri: String,
    val spiritualFaizUrdu: String,
    val verseUrdu: String = "" // Manzoom Shajra verse line
)

data class ShajraVerse(
    val id: Int,
    val arabicUrduText: String,
    val transliteration: String = "",
    val translationUrdu: String = ""
)
