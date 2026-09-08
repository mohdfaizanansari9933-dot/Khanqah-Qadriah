package com.example.data.prayer

import com.example.data.model.CalculationMethod
import com.example.data.model.NextPrayerInfo
import com.example.data.model.PrayerName
import com.example.data.model.PrayerTimes
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import kotlin.math.*

/**
 * High-Precision Astronomical & Hanafi Prayer Calculation Engine
 * Calibrated specifically for Khanqah-e-Aaliya Qadriah Badaun Shareef (28.0339° N, 79.1278° E)
 * Implements Jean Meeus Astronomical Solar Coordinates + Karachi University standard + Hanafi Mislayn Asr.
 */
object PrayerCalculationEngine {

    const val KAABA_LATITUDE = 21.422487
    const val KAABA_LONGITUDE = 39.826206

    /**
     * Calculates the Qibla azimuth angle (0° to 360° from True North)
     */
    fun calculateQiblaDirection(latitude: Double, longitude: Double): Double {
        val latRad = Math.toRadians(latitude)
        val kaabaLatRad = Math.toRadians(KAABA_LATITUDE)
        val dLongRad = Math.toRadians(KAABA_LONGITUDE - longitude)

        val y = sin(dLongRad) * cos(kaabaLatRad)
        val x = cos(latRad) * sin(kaabaLatRad) - sin(latRad) * cos(kaabaLatRad) * cos(dLongRad)

        var qibla = Math.toDegrees(atan2(y, x))
        qibla = (qibla + 360.0) % 360.0
        return qibla
    }

    /**
     * Calculates distance to Kaaba in kilometers
     */
    fun calculateDistanceToKaabaKm(latitude: Double, longitude: Double): Double {
        val r = 6371.0 // Earth radius in km
        val dLat = Math.toRadians(KAABA_LATITUDE - latitude)
        val dLon = Math.toRadians(KAABA_LONGITUDE - longitude)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(latitude)) * cos(Math.toRadians(KAABA_LATITUDE)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }

    /**
     * Resolve geographical timezone to eliminate cloud/emulator UTC drift
     */
    fun resolveTimeZone(latitude: Double, longitude: Double): TimeZone {
        return when {
            // India & Sri Lanka (Badaun Shareef, Bareilly, Delhi, UP, etc.)
            longitude in 68.0..97.5 && latitude in 8.0..37.5 -> TimeZone.getTimeZone("Asia/Kolkata")
            // Pakistan
            longitude in 60.0..78.0 && latitude in 23.5..37.5 -> TimeZone.getTimeZone("Asia/Karachi")
            // Saudi Arabia
            longitude in 34.0..55.0 && latitude in 16.0..32.0 -> TimeZone.getTimeZone("Asia/Riyadh")
            // Iraq
            longitude in 38.0..48.5 && latitude in 29.0..37.5 -> TimeZone.getTimeZone("Asia/Baghdad")
            // UK / Western Europe
            longitude in -10.0..2.0 && latitude in 50.0..60.0 -> TimeZone.getTimeZone("Europe/London")
            else -> TimeZone.getDefault()
        }
    }

    /**
     * Calculates complete prayer times (start & end) with astronomical precision
     */
    fun calculatePrayerTimes(
        calendar: Calendar = Calendar.getInstance(),
        latitude: Double,
        longitude: Double,
        method: CalculationMethod = CalculationMethod.KARACHI,
        isHanafiAsr: Boolean = true,
        use24HourFormat: Boolean = false
    ): PrayerTimes {
        val targetTz = resolveTimeZone(latitude, longitude)
        val localCal = Calendar.getInstance(targetTz).apply {
            timeInMillis = calendar.timeInMillis
        }

        val year = localCal.get(Calendar.YEAR)
        val month = localCal.get(Calendar.MONTH) + 1
        val day = localCal.get(Calendar.DAY_OF_MONTH)

        val tzOffsetHours = targetTz.getOffset(localCal.timeInMillis).toDouble() / (1000 * 60 * 60)

        // Julian Date
        val y = if (month <= 2) year - 1 else year
        val m = if (month <= 2) month + 12 else month
        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)
        val jd = floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + b - 1524.5

        val t = (jd - 2451545.0) / 36525.0
        val l0 = fixAngle(280.46646 + 36000.76983 * t + 0.0003032 * t * t)
        val mAnom = fixAngle(357.52911 + 35999.05029 * t - 0.0001537 * t * t)
        val e = 0.016708634 - 0.000042037 * t - 0.0000001267 * t * t
        val c = (1.914602 - 0.004817 * t - 0.000014 * t * t) * sin(Math.toRadians(mAnom)) +
                (0.019993 - 0.000101 * t) * sin(Math.toRadians(2 * mAnom)) +
                0.000289 * sin(Math.toRadians(3 * mAnom))
        val trueLong = fixAngle(l0 + c)
        val apparentLong = trueLong - 0.00569 - 0.00478 * sin(Math.toRadians(125.04 - 1934.136 * t))

        val eps0 = 23.0 + (26.0 + (21.448 - 46.815 * t - 0.00059 * t * t + 0.001813 * t * t * t) / 60.0) / 60.0
        val eps = eps0 + 0.00256 * cos(Math.toRadians(125.04 - 1934.136 * t))

        val dec = Math.toDegrees(asin(sin(Math.toRadians(eps)) * sin(Math.toRadians(apparentLong))))
        val ra = fixAngle(Math.toDegrees(atan2(cos(Math.toRadians(eps)) * sin(Math.toRadians(apparentLong)), cos(Math.toRadians(apparentLong)))))

        // Equation of Time in minutes
        var eqtMinutes = (l0 - ra) * 4.0
        if (eqtMinutes > 20.0) eqtMinutes -= 1440.0
        if (eqtMinutes < -20.0) eqtMinutes += 1440.0

        // Solar Noon (Zawal)
        val solarNoonHours = 12.0 + tzOffsetHours - (longitude / 15.0) - (eqtMinutes / 60.0)

        fun hourAngle(altitudeAngle: Double): Double {
            val altRad = Math.toRadians(altitudeAngle)
            val latRad = Math.toRadians(latitude)
            val decRad = Math.toRadians(dec)
            val cosH = (sin(altRad) - sin(latRad) * sin(decRad)) / (cos(latRad) * cos(decRad))
            if (cosH < -1.0 || cosH > 1.0) return Double.NaN
            return Math.toDegrees(acos(cosH))
        }

        // Sunrise & Sunset (-0.8333° for atmospheric refraction and sun's radius)
        val h0 = hourAngle(-0.8333)
        val sunriseHours = solarNoonHours - (if (!h0.isNaN()) h0 / 15.0 else 6.0)
        val sunsetHours = solarNoonHours + (if (!h0.isNaN()) h0 / 15.0 else 6.0)

        // Fajr (Subah Sadiq)
        val hFajr = hourAngle(-method.fajrAngle)
        val fajrHours = solarNoonHours - (if (!hFajr.isNaN()) hFajr / 15.0 else 7.5)

        // Dhuhr: Zawal + 2 mins ihtiyat for Ahle Sunnat
        val dhuhrHours = solarNoonHours + (2.0 / 60.0)

        // Asr: Hanafi shadow multiplier = 2 (Mislayn), Shafi = 1
        val shadowFactor = if (isHanafiAsr) 2.0 else 1.0
        val asrAltitude = Math.toDegrees(atan(1.0 / (shadowFactor + tan(Math.toRadians(abs(latitude - dec))))))
        val hAsr = hourAngle(asrAltitude)
        val asrHours = solarNoonHours + (if (!hAsr.isNaN()) hAsr / 15.0 else 3.5)

        // Maghrib: Sunset + 2.5 mins ihtiyat
        val maghribHours = sunsetHours + (2.5 / 60.0)

        // Isha: 18° below horizon (Karachi standard)
        val ishaHours = if (method == CalculationMethod.UMM_AL_QURA) {
            maghribHours + 1.5
        } else {
            val hIsha = hourAngle(-method.ishaAngle)
            solarNoonHours + (if (!hIsha.isNaN()) hIsha / 15.0 else 7.5)
        }

        // Sehri ends 10 minutes before Fajr Subah Sadiq
        val sehriHours = fajrHours - (10.0 / 60.0)
        val iftarHours = maghribHours

        // Midnight base millis for today
        val midnightCal = Calendar.getInstance(targetTz).apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val baseMillis = midnightCal.timeInMillis

        fun toMillis(hours: Double): Long = baseMillis + (hours * 3600 * 1000).toLong()

        val fajrMillis = toMillis(fajrHours)
        val sunriseMillis = toMillis(sunriseHours)
        val dhuhrMillis = toMillis(dhuhrHours)
        val asrMillis = toMillis(asrHours)
        val maghribMillis = toMillis(maghribHours)
        val ishaMillis = toMillis(ishaHours)

        val fajrFmt = formatTime(fajrHours, use24HourFormat)
        val sunriseFmt = formatTime(sunriseHours, use24HourFormat)
        val dhuhrFmt = formatTime(dhuhrHours, use24HourFormat)
        val asrFmt = formatTime(asrHours, use24HourFormat)
        val maghribFmt = formatTime(maghribHours, use24HourFormat)
        val ishaFmt = formatTime(ishaHours, use24HourFormat)
        val zawalFmt = formatTime(solarNoonHours, use24HourFormat)
        val sehriFmt = formatTime(sehriHours, use24HourFormat)
        val iftarFmt = formatTime(iftarHours, use24HourFormat)

        return PrayerTimes(
            fajr = fajrFmt,
            sunrise = sunriseFmt,
            dhuhr = dhuhrFmt,
            asr = asrFmt,
            maghrib = maghribFmt,
            isha = ishaFmt,
            sehri = sehriFmt,
            iftar = iftarFmt,
            fajrMillis = fajrMillis,
            sunriseMillis = sunriseMillis,
            dhuhrMillis = dhuhrMillis,
            asrMillis = asrMillis,
            maghribMillis = maghribMillis,
            ishaMillis = ishaMillis,
            fajrStart = fajrFmt,
            fajrEnd = sunriseFmt,
            dhuhrStart = dhuhrFmt,
            dhuhrEnd = asrFmt,
            asrStart = asrFmt,
            asrEnd = maghribFmt,
            maghribStart = maghribFmt,
            maghribEnd = ishaFmt,
            ishaStart = ishaFmt,
            ishaEnd = fajrFmt,
            zawal = zawalFmt
        )
    }

    /**
     * Determines current active prayer and countdown to next prayer.
     * Note: SUNRISE is an astronomical event, NOT a namaz.
     * Prayers are FAJR, DHUHR, ASR, MAGHRIB, ISHA.
     */
    fun getNextPrayer(prayerTimes: PrayerTimes, currentMillis: Long = System.currentTimeMillis()): NextPrayerInfo {
        val f = prayerTimes.fajrMillis
        val s = prayerTimes.sunriseMillis
        val d = prayerTimes.dhuhrMillis
        val a = prayerTimes.asrMillis
        val m = prayerTimes.maghribMillis
        val i = prayerTimes.ishaMillis

        return when {
            // Case 1: Early morning before Fajr (Between midnight and Fajr)
            currentMillis < f -> {
                val remaining = f - currentMillis
                val total = 6 * 3600 * 1000L
                val progress = ((total - remaining).toFloat() / total).coerceIn(0.05f, 0.95f)
                NextPrayerInfo(
                    prayer = PrayerName.FAJR,
                    timeFormatted = prayerTimes.fajr,
                    remainingMillis = remaining,
                    progressFraction = progress,
                    currentPrayer = PrayerName.ISHA,
                    currentPrayerEndsIn = formatRemaining(remaining)
                )
            }
            // Case 2: During Fajr (Between Fajr and Sunrise)
            currentMillis < s -> {
                val remainingUntilEnd = s - currentMillis
                val remainingUntilDhuhr = d - currentMillis
                val totalSpan = max(1L, s - f)
                val progress = ((totalSpan - remainingUntilEnd).toFloat() / totalSpan).coerceIn(0f, 1f)
                NextPrayerInfo(
                    prayer = PrayerName.DHUHR,
                    timeFormatted = prayerTimes.dhuhr,
                    remainingMillis = remainingUntilDhuhr,
                    progressFraction = progress,
                    currentPrayer = PrayerName.FAJR,
                    currentPrayerEndsIn = formatRemaining(remainingUntilEnd)
                )
            }
            // Case 3: Ishraq / Chasht / Morning (Between Sunrise and Dhuhr)
            currentMillis < d -> {
                val remaining = d - currentMillis
                val totalSpan = max(1L, d - s)
                val progress = ((totalSpan - remaining).toFloat() / totalSpan).coerceIn(0f, 1f)
                NextPrayerInfo(
                    prayer = PrayerName.DHUHR,
                    timeFormatted = prayerTimes.dhuhr,
                    remainingMillis = remaining,
                    progressFraction = progress,
                    currentPrayer = null,
                    currentPrayerEndsIn = null
                )
            }
            // Case 4: During Dhuhr (Between Dhuhr and Asr)
            currentMillis < a -> {
                val remaining = a - currentMillis
                val totalSpan = max(1L, a - d)
                val progress = ((totalSpan - remaining).toFloat() / totalSpan).coerceIn(0f, 1f)
                NextPrayerInfo(
                    prayer = PrayerName.ASR,
                    timeFormatted = prayerTimes.asr,
                    remainingMillis = remaining,
                    progressFraction = progress,
                    currentPrayer = PrayerName.DHUHR,
                    currentPrayerEndsIn = formatRemaining(remaining)
                )
            }
            // Case 5: During Asr (Between Asr and Maghrib)
            currentMillis < m -> {
                val remaining = m - currentMillis
                val totalSpan = max(1L, m - a)
                val progress = ((totalSpan - remaining).toFloat() / totalSpan).coerceIn(0f, 1f)
                NextPrayerInfo(
                    prayer = PrayerName.MAGHRIB,
                    timeFormatted = prayerTimes.maghrib,
                    remainingMillis = remaining,
                    progressFraction = progress,
                    currentPrayer = PrayerName.ASR,
                    currentPrayerEndsIn = formatRemaining(remaining)
                )
            }
            // Case 6: During Maghrib (Between Maghrib and Isha)
            currentMillis < i -> {
                val remaining = i - currentMillis
                val totalSpan = max(1L, i - m)
                val progress = ((totalSpan - remaining).toFloat() / totalSpan).coerceIn(0f, 1f)
                NextPrayerInfo(
                    prayer = PrayerName.ISHA,
                    timeFormatted = prayerTimes.isha,
                    remainingMillis = remaining,
                    progressFraction = progress,
                    currentPrayer = PrayerName.MAGHRIB,
                    currentPrayerEndsIn = formatRemaining(remaining)
                )
            }
            // Case 7: During Isha / Night until tomorrow's Fajr
            else -> {
                val tomorrowFajr = f + 24 * 3600 * 1000L
                val remaining = max(0L, tomorrowFajr - currentMillis)
                val totalSpan = max(1L, tomorrowFajr - i)
                val progress = ((totalSpan - remaining).toFloat() / totalSpan).coerceIn(0f, 1f)
                NextPrayerInfo(
                    prayer = PrayerName.FAJR,
                    timeFormatted = prayerTimes.fajr,
                    remainingMillis = remaining,
                    progressFraction = progress,
                    currentPrayer = PrayerName.ISHA,
                    currentPrayerEndsIn = formatRemaining(remaining)
                )
            }
        }
    }

    private fun formatRemaining(remainingMillis: Long): String {
        val totalSec = max(0L, remainingMillis / 1000)
        val hours = totalSec / 3600
        val mins = (totalSec % 3600) / 60
        return if (hours > 0) "${hours}h ${mins}m" else "${mins}m"
    }

    private fun fixAngle(angle: Double): Double {
        var a = angle % 360.0
        if (a < 0) a += 360.0
        return a
    }

    private fun formatTime(hoursDecimal: Double, use24Hour: Boolean): String {
        var h = hoursDecimal % 24.0
        if (h < 0) h += 24.0

        var hours = floor(h).toInt()
        var minutes = round((h - hours) * 60.0).toInt()
        if (minutes >= 60) {
            hours = (hours + 1) % 24
            minutes = 0
        }

        return if (use24Hour) {
            String.format(Locale.getDefault(), "%02d:%02d", hours, minutes)
        } else {
            val period = if (hours >= 12) "PM" else "AM"
            val displayHour = when {
                hours == 0 -> 12
                hours > 12 -> hours - 12
                else -> hours
            }
            String.format(Locale.getDefault(), "%02d:%02d %s", displayHour, minutes, period)
        }
    }
}
