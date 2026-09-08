package com.example.data.prayer

import com.example.data.model.CalculationMethod
import com.example.data.model.NextPrayerInfo
import com.example.data.model.PrayerName
import com.example.data.model.PrayerTimes
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import kotlin.math.*

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
     * Calculates prayer times dynamically for given lat, lng, date, calculation method, and Hanafi setting.
     */
    fun calculatePrayerTimes(
        calendar: Calendar = Calendar.getInstance(),
        latitude: Double,
        longitude: Double,
        timezoneOffsetHours: Double = (calendar.timeZone.rawOffset + calendar.timeZone.dstSavings).toDouble() / (1000 * 60 * 60),
        method: CalculationMethod = CalculationMethod.KARACHI,
        isHanafiAsr: Boolean = true,
        use24HourFormat: Boolean = false
    ): PrayerTimes {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val jd = julianDate(year, month, day)
        val d = jd - 2451545.0

        // Sun position
        val g = fixAngle(357.529 + 0.98560028 * d)
        val q = fixAngle(280.459 + 0.98564736 * d)
        val l = fixAngle(q + 1.915 * sin(Math.toRadians(g)) + 0.020 * sin(Math.toRadians(2 * g)))
        val e = 23.439 - 0.00000036 * d
        val dec = Math.toDegrees(asin(sin(Math.toRadians(e)) * sin(Math.toRadians(l))))

        var ra = Math.toDegrees(atan2(cos(Math.toRadians(e)) * sin(Math.toRadians(l)), cos(Math.toRadians(l)))) / 15.0
        ra = fixHour(ra)

        val eqt = q / 15.0 - ra

        // Solar noon (Dhuhr)
        val noon = fixHour(12.0 + timezoneOffsetHours - (longitude / 15.0) - eqt)

        // Sunrise & Sunset (approx -0.833 deg for refraction & sun disc)
        val sunriseHourAngle = hourAngle(-0.833, latitude, dec)
        val sunriseTime = if (!sunriseHourAngle.isNaN()) noon - sunriseHourAngle / 15.0 else noon - 6.0
        val sunsetTime = if (!sunriseHourAngle.isNaN()) noon + sunriseHourAngle / 15.0 else noon + 6.0

        // Fajr
        val fajrHourAngle = hourAngle(-method.fajrAngle, latitude, dec)
        val fajrTime = if (!fajrHourAngle.isNaN()) noon - fajrHourAngle / 15.0 else sunriseTime - 1.5

        // Asr: Hanafi shadow multiplier is 2, Shafi is 1
        val shadowFactor = if (isHanafiAsr) 2.0 else 1.0
        val asrAltitude = Math.toDegrees(atan(1.0 / (shadowFactor + tan(Math.toRadians(abs(latitude - dec))))))
        val asrHourAngle = hourAngle(asrAltitude, latitude, dec)
        val asrTime = if (!asrHourAngle.isNaN()) noon + asrHourAngle / 15.0 else noon + 3.5

        // Maghrib (Sunset + 3 min safety margin for Ahle Sunnat/Hanafi practice)
        val maghribTime = sunsetTime + (3.0 / 60.0)

        // Isha
        val ishaTime = if (method == CalculationMethod.UMM_AL_QURA) {
            maghribTime + 1.5 // 90 min after Maghrib
        } else {
            val ishaHourAngle = hourAngle(-method.ishaAngle, latitude, dec)
            if (!ishaHourAngle.isNaN()) noon + ishaHourAngle / 15.0 else maghribTime + 1.5
        }

        // Sehri ends 10 mins before Fajr
        val sehriTime = fajrTime - (10.0 / 60.0)
        val iftarTime = maghribTime

        // Calculate epoch milliseconds for today's prayers
        val calBase = calendar.clone() as Calendar
        calBase.set(Calendar.HOUR_OF_DAY, 0)
        calBase.set(Calendar.MINUTE, 0)
        calBase.set(Calendar.SECOND, 0)
        calBase.set(Calendar.MILLISECOND, 0)
        val baseMillis = calBase.timeInMillis

        fun timeToMillis(hours: Double): Long {
            return baseMillis + (hours * 3600 * 1000).toLong()
        }

        return PrayerTimes(
            fajr = formatTime(fajrTime, use24HourFormat),
            sunrise = formatTime(sunriseTime, use24HourFormat),
            dhuhr = formatTime(noon + (2.0 / 60.0), use24HourFormat), // +2 min after zawal
            asr = formatTime(asrTime, use24HourFormat),
            maghrib = formatTime(maghribTime, use24HourFormat),
            isha = formatTime(ishaTime, use24HourFormat),
            sehri = formatTime(sehriTime, use24HourFormat),
            iftar = formatTime(iftarTime, use24HourFormat),
            fajrMillis = timeToMillis(fajrTime),
            sunriseMillis = timeToMillis(sunriseTime),
            dhuhrMillis = timeToMillis(noon + (2.0 / 60.0)),
            asrMillis = timeToMillis(asrTime),
            maghribMillis = timeToMillis(maghribTime),
            ishaMillis = timeToMillis(ishaTime)
        )
    }

    fun getNextPrayer(prayerTimes: PrayerTimes, currentMillis: Long = System.currentTimeMillis()): NextPrayerInfo {
        val list = listOf(
            Triple(PrayerName.FAJR, prayerTimes.fajrMillis, prayerTimes.fajr),
            Triple(PrayerName.SUNRISE, prayerTimes.sunriseMillis, prayerTimes.sunrise),
            Triple(PrayerName.DHUHR, prayerTimes.dhuhrMillis, prayerTimes.dhuhr),
            Triple(PrayerName.ASR, prayerTimes.asrMillis, prayerTimes.asr),
            Triple(PrayerName.MAGHRIB, prayerTimes.maghribMillis, prayerTimes.maghrib),
            Triple(PrayerName.ISHA, prayerTimes.ishaMillis, prayerTimes.isha)
        )

        for (i in list.indices) {
            val (name, millis, formatted) = list[i]
            if (currentMillis < millis) {
                val remaining = millis - currentMillis
                val prevMillis = if (i > 0) list[i - 1].second else list[0].second - 8 * 3600 * 1000
                val totalSpan = max(1L, millis - prevMillis)
                val progress = ((totalSpan - remaining).toFloat() / totalSpan).coerceIn(0f, 1f)
                return NextPrayerInfo(name, formatted, remaining, progress)
            }
        }

        // Past Isha, next is tomorrow's Fajr
        val nextFajrMillis = prayerTimes.fajrMillis + 24 * 3600 * 1000
        val remaining = nextFajrMillis - currentMillis
        return NextPrayerInfo(PrayerName.FAJR, prayerTimes.fajr, remaining, 0.1f)
    }

    private fun julianDate(year: Int, month: Int, day: Int): Double {
        var y = year
        var m = month
        if (m <= 2) {
            y -= 1
            m += 12
        }
        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)
        return floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + b - 1524.5
    }

    private fun hourAngle(altitude: Double, latitude: Double, declination: Double): Double {
        val altRad = Math.toRadians(altitude)
        val latRad = Math.toRadians(latitude)
        val decRad = Math.toRadians(declination)
        val cosH = (sin(altRad) - sin(latRad) * sin(decRad)) / (cos(latRad) * cos(decRad))
        if (cosH < -1.0 || cosH > 1.0) return Double.NaN
        return Math.toDegrees(acos(cosH))
    }

    private fun fixAngle(angle: Double): Double {
        var a = angle - 360.0 * floor(angle / 360.0)
        if (a < 0) a += 360.0
        return a
    }

    private fun fixHour(hour: Double): Double {
        var h = hour - 24.0 * floor(hour / 24.0)
        if (h < 0) h += 24.0
        return h
    }

    private fun formatTime(hoursDecimal: Double, use24Hour: Boolean): String {
        var h = hoursDecimal
        if (h < 0) h += 24.0
        if (h >= 24.0) h -= 24.0

        val hours = floor(h).toInt()
        val minutes = floor((h - hours) * 60.0).toInt()

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
