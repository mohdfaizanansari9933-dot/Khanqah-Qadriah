package com.example.data.model

data class DailyHadith(
    val id: String,
    val textArabic: String,
    val translationUrdu: String,
    val narratorUrdu: String,
    val sourceReference: String,
    val lessonUrdu: String
)

data class IslamicArticle(
    val id: String,
    val titleUrdu: String,
    val categoryUrdu: String,
    val authorUrdu: String,
    val summaryUrdu: String,
    val contentUrdu: String,
    val dateHijri: String,
    val isFeatured: Boolean = false
)

data class MasalahQA(
    val id: String,
    val questionUrdu: String,
    val answerUrdu: String,
    val referenceUrdu: String,
    val topicUrdu: String
)
