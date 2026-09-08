package com.example.data.model

data class PrayerTimes(
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val sehri: String,
    val iftar: String,
    val fajrMillis: Long = 0L,
    val sunriseMillis: Long = 0L,
    val dhuhrMillis: Long = 0L,
    val asrMillis: Long = 0L,
    val maghribMillis: Long = 0L,
    val ishaMillis: Long = 0L,
    val fajrStart: String = fajr,
    val fajrEnd: String = sunrise,
    val dhuhrStart: String = dhuhr,
    val dhuhrEnd: String = asr,
    val asrStart: String = asr,
    val asrEnd: String = maghrib,
    val maghribStart: String = maghrib,
    val maghribEnd: String = isha,
    val ishaStart: String = isha,
    val ishaEnd: String = fajr,
    val zawal: String = dhuhr
)

enum class PrayerName(val urduName: String, val englishName: String) {
    FAJR("فجر", "Fajr"),
    SUNRISE("طلوعِ آفتاب", "Sunrise"),
    DHUHR("ظہر", "Dhuhr"),
    ASR("عصر (حنفی)", "Asr (Hanafi)"),
    MAGHRIB("مغرب", "Maghrib"),
    ISHA("عشاء", "Isha")
}

data class NextPrayerInfo(
    val prayer: PrayerName,
    val timeFormatted: String,
    val remainingMillis: Long,
    val progressFraction: Float,
    val currentPrayer: PrayerName? = null,
    val currentPrayerEndsIn: String? = null
)

data class LocationInfo(
    val cityName: String,
    val countryName: String,
    val latitude: Double,
    val longitude: Double,
    val isGps: Boolean = false
)

enum class CalculationMethod(val urduTitle: String, val title: String, val fajrAngle: Double, val ishaAngle: Double) {
    KARACHI("جامعہ العلوم الاسلامیہ، کراچی (اہلِ سنت / برصغیر)", "Univ of Islamic Sciences, Karachi", 18.0, 18.0),
    MWL("رابطہ عالم اسلامی (Muslim World League)", "Muslim World League", 18.0, 17.0),
    UMM_AL_QURA("ام القریٰ، مکہ مکرمہ", "Umm al-Qura, Makkah", 18.5, 90.0), // 90 min after Maghrib
    ISNA("اسلامک سوسائٹی آف نارتھ امریکہ (ISNA)", "ISNA (North America)", 15.0, 15.0),
    EGYPTIAN("مصری جنرل اتھارٹی آف سروے", "Egyptian General Authority", 19.5, 17.5)
}

data class PrayerNotificationSettings(
    val fajr: Boolean = true,
    val sunrise: Boolean = false,
    val dhuhr: Boolean = true,
    val asr: Boolean = true,
    val maghrib: Boolean = true,
    val isha: Boolean = true,
    val sehri: Boolean = true,
    val iftar: Boolean = true,
    val soundEnabled: Boolean = true,
    val azanAlertMode: String = "AZAN" // "AZAN", "ALARM", "SILENT"
)
