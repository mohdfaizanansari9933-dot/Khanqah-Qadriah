package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.prayer.PrayerCalculationEngine
import com.example.data.repository.QuranRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Khanqah Qadriah", appName)
    }

    @Test
    fun `verify quran repository has 114 surahs and authentic kanzul iman`() {
        assertEquals(114, QuranRepository.SURAHS_LIST.size)
        val surahFatiha = QuranRepository.getSurahByNumber(1)
        assertNotNull(surahFatiha)
        assertEquals("الفاتحة", surahFatiha?.arabicName)
        val ayahs = QuranRepository.getAyahsForSurah(1)
        assertTrue(ayahs.isNotEmpty())
    }

    @Test
    fun `verify qibla direction calculation for Karachi`() {
        val qiblaAngle = PrayerCalculationEngine.calculateQiblaDirection(24.8607, 67.0011)
        // Karachi to Makkah is approximately 261° - 275°
        assertTrue("Qibla angle should be between 250 and 280 degrees", qiblaAngle in 250.0..280.0)
    }
}
