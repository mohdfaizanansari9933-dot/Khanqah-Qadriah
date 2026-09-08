package com.example.data.model

data class DhikrItem(
    val id: String,
    val textArabic: String,
    val textUrdu: String,
    val translationUrdu: String,
    val defaultTarget: Int = 33,
    val virturesUrdu: String = ""
) {
    val arabicText: String get() = textArabic
    val nameUrdu: String get() = textUrdu
    val meaningUrdu: String get() = translationUrdu
    val targetCount: Int get() = defaultTarget
    val virtueUrdu: String get() = virturesUrdu
}

data class TasbeehSession(
    val dhikrId: String,
    val currentCount: Int,
    val targetCount: Int,
    val totalHistoricalCount: Long,
    val todayTotalCount: Int
)
