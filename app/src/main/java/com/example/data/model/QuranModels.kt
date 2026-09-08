package com.example.data.model

data class Surah(
    val number: Int,
    val arabicName: String,
    val urduName: String,
    val englishName: String,
    val revelationPlaceUrdu: String, // مکی / مدنی
    val totalAyahs: Int,
    val startParaNumber: Int,
    val meaningUrdu: String
) {
    val revelationType: String get() = revelationPlaceUrdu
}

data class Ayah(
    val surahNumber: Int,
    val ayahNumber: Int,
    val arabicText: String,
    val urduTranslation: String,
    val transliteration: String = "",
    val paraNumber: Int = 1,
    var isFavorite: Boolean = false
)

data class Para(
    val number: Int,
    val arabicName: String,
    val urduName: String,
    val startSurahNumber: Int,
    val startAyahNumber: Int
)

data class QuranBookmark(
    val id: String,
    val surahNumber: Int,
    val ayahNumber: Int,
    val surahNameUrdu: String,
    val timestamp: Long,
    val note: String = ""
)
