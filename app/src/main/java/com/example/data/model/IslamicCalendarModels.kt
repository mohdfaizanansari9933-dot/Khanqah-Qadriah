package com.example.data.model

data class HijriDate(
    val day: Int,
    val monthNumber: Int,
    val monthUrdu: String,
    val monthEnglish: String,
    val year: Int,
    val dayOfWeekUrdu: String,
    val dayOfWeekEnglish: String,
    val gregorianFormatted: String
) {
    val formattedUrdu: String get() = "$day $monthUrdu $year ھ"
}

data class IslamicEvent(
    val hijriDay: Int,
    val hijriMonthNumber: Int,
    val titleUrdu: String,
    val titleEnglish: String,
    val descriptionUrdu: String,
    val isMajorHoliday: Boolean = false
) {
    val isMajor: Boolean get() = isMajorHoliday
    val hijriMonthUrdu: String get() = com.example.data.repository.IslamicCalendarRepository.HIJRI_MONTHS_URDU.getOrElse(hijriMonthNumber - 1) { "" }
}

data class IslamicCalendarDay(
    val gregorianDay: Int,
    val gregorianMonth: Int,
    val gregorianYear: Int,
    val hijriDay: Int,
    val hijriMonthUrdu: String,
    val hijriYear: Int,
    val dayOfWeek: Int,
    val event: IslamicEvent? = null,
    val isToday: Boolean = false
)
