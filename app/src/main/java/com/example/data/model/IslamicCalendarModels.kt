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
    val formattedEnglish: String get() = "$day $monthEnglish $year AH"
}

data class IslamicEvent(
    val hijriDay: Int,
    val hijriMonthNumber: Int,
    val titleUrdu: String,
    val titleEnglish: String,
    val descriptionUrdu: String,
    val isMajorHoliday: Boolean = false,
    val isUrsBadaun: Boolean = false,
    val isGhyarween: Boolean = false,
    val buzurgZikr: String = "",
    val fatihaDua: String = ""
) {
    val isMajor: Boolean get() = isMajorHoliday
    val hijriMonthUrdu: String get() = com.example.data.repository.IslamicCalendarRepository.HIJRI_MONTHS_URDU.getOrElse(hijriMonthNumber - 1) { "" }
}

data class IslamicCalendarDay(
    val gregorianDay: Int,
    val gregorianMonth: Int,
    val gregorianYear: Int,
    val gregorianDateFormatted: String = "",
    val hijriDay: Int,
    val hijriMonthNumber: Int = 1,
    val hijriMonthUrdu: String,
    val hijriMonthEnglish: String = "",
    val hijriYear: Int,
    val dayOfWeek: Int, // 1=Sunday, 2=Monday, ..., 7=Saturday
    val dayOfWeekUrdu: String = "",
    val dayOfWeekEnglish: String = "",
    val event: IslamicEvent? = null,
    val isToday: Boolean = false,
    val isGhyarween: Boolean = false,
    val isUrsBadaun: Boolean = false
)
