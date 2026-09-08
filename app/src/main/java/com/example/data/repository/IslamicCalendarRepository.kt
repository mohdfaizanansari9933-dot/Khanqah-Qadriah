package com.example.data.repository

import com.example.data.model.HijriDate
import com.example.data.model.IslamicCalendarDay
import com.example.data.model.IslamicEvent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.floor

object IslamicCalendarRepository {

    val HIJRI_MONTHS_URDU = listOf(
        "محرم الحرام",
        "صفر المظفر",
        "ربیع الاول",
        "ربیع الثانی (گیارہویں شریف)",
        "جمادی الاول",
        "جمادی الثانی",
        "رجب المرجب",
        "شعبان المعظم",
        "رمضان المبارک",
        "شوال المکرم",
        "ذوالقعدۃ الحرام",
        "ذوالحجۃ الحرام"
    )

    val HIJRI_MONTHS_ENGLISH = listOf(
        "Muharram",
        "Safar",
        "Rabi-ul-Awwal",
        "Rabi-us-Sani",
        "Jamadi-ul-Awwal",
        "Jamadi-us-Sani",
        "Rajab",
        "Shaban",
        "Ramadan",
        "Shawwal",
        "Zul-Qadah",
        "Zul-Hijjah"
    )

    val DAYS_URDU = listOf(
        "اتوار",    // Sunday
        "پیر",      // Monday
        "منگل",     // Tuesday
        "بدھ",      // Wednesday
        "جمعرات",   // Thursday
        "جمعۃ المبارک", // Friday
        "ہفتہ"      // Saturday
    )

    val AHLE_SUNNAT_EVENTS = listOf(
        IslamicEvent(1, 1, "آغازِ نیا ہجری سال", "Islamic New Year", "یکم محرم الحرام - آغازِ سالِ نو ہجری اور یومِ شہادت امیر المؤمنین حضرت عمر فاروق رضی اللہ عنہ"),
        IslamicEvent(10, 1, "یومِ عاشورہ و شہادت امام حسین ؓ", "Youm-e-Ashura", "10 محرم - شہادتِ نواسہ رسول حضرت امام حسین رضی اللہ عنہ اور شہدائے کربلا", true),
        IslamicEvent(20, 2, "چہلم امام حسین ؓ", "Chehlum Imam Hussain", "20 صفر المظفر - چہلم حضرت امام حسین علیہ السلام"),
        IslamicEvent(28, 2, "یومِ وصالِ حسن مجتبیٰ ؓ", "Wisal Imam Hassan", "28 صفر - وصالِ باکمال حضرت امام حسن مجتبیٰ رضی اللہ عنہ"),
        IslamicEvent(12, 3, "عید میلاد النبی ﷺ (جشنِ ولادت)", "Eid Milad-un-Nabi ﷺ", "12 ربیع الاول شریف - ولادتِ باسعادت سرورِ کائنات، فخرِ موجودات حضرت محمد مصطفیٰ ﷺ", true),
        IslamicEvent(11, 4, "گیارہویں شریف (حضور غوثِ اعظم ؓ)", "Ghyarween Shareef", "11 ربیع الثانی - عرسِ مبارک سلطان الاولیاء، قطبِ ربانی سیدنا شیخ عبدالقادر جیلانی رضی اللہ عنہ (غوث الاعظم)", true),
        IslamicEvent(13, 5, "یومِ سیدہ فاطمۃ الزہراء ؓ", "Youm-e-Syeda Fatima", "13 جمادی الاول - ذکرِ جمیل خاتونِ جنت حضرت سیدہ فاطمۃ الزہراء سلام اللہ علیہا"),
        IslamicEvent(22, 7, "کونڈے امام جعفر صادق ؓ", "Niyaz Imam Jafar Sadiq", "22 رجب - نیاز و ختمِ مبارک حضرت امام جعفر صادق رضی اللہ عنہ"),
        IslamicEvent(27, 7, "شبِ معراج النبی ﷺ", "Shab-e-Meraj", "27 رجب المرجب - معراج شریف کا عظیم الشان اور روح پرور واقعہ", true),
        IslamicEvent(15, 8, "شبِ برات (مغفرت کی رات)", "Shab-e-Barat", "15 شعبان المعظم - لیلۃ البرات، رحمت اور بخشش کی مبارک رات", true),
        IslamicEvent(1, 9, "آغازِ رمضان المبارک", "First of Ramadan", "یکم رمضان المبارک - رحمتوں، برکتوں اور مغفرت کے بابرکت مہینے کا آغاز", true),
        IslamicEvent(10, 9, "یومِ وصال ام المؤمنین حضرت خدیجہ ؓ", "Wisal Syeda Khadija", "10 رمضان - وصالِ سیدہ خدیجۃ الکبریٰ رضی اللہ عنہا"),
        IslamicEvent(17, 9, "یومِ فتح و غزوہ بدر", "Youm-e-Badr", "17 رمضان المبارک - یومِ الفرقان، پہلی فتح اسلام غزوہ بدر"),
        IslamicEvent(21, 9, "یومِ شہادت حضرت علی المرتضیٰ ؓ", "Shahadat Hazrat Ali", "21 رمضان - شہادتِ مولائے کائنات اسد اللہ الغالب حضرت علی بن ابی طالب کرم اللہ وجہہ الکریم"),
        IslamicEvent(27, 9, "شبِ قدر (لیلتہ القدر)", "Shab-e-Qadr", "27 رمضان المبارک - ہزار مہینوں سے افضل رات لیلتہ القدر", true),
        IslamicEvent(1, 10, "عید الفطر المبارک", "Eid-ul-Fitr", "یکم شوال المکرم - جشنِ سعید عید الفطر", true),
        IslamicEvent(25, 10, "عرسِ اعلیٰ حضرت امام احمد رضا خان ؒ", "Urs-e-Razvi", "25 شوال - عرسِ مبارک مجددِ دین و ملت امام اہل سنت امام احمد رضا خان قادری بریلوی رحمہ اللہ علیہ"),
        IslamicEvent(8, 12, "آغازِ ایامِ حج", "Start of Hajj", "8 ذوالحجہ - یومِ ترویہ، حجاج کرام کا منیٰ کی طرف روانہ ہونا"),
        IslamicEvent(9, 12, "یومِ عرفہ / وقوفِ عرفات", "Youm-e-Arafah", "9 ذوالحجہ - یومِ عرفہ، حج کا سب سے بڑا رکن"),
        IslamicEvent(10, 12, "عید الاضحیٰ المبارک", "Eid-ul-Adha", "10 ذوالحجہ - سنتِ ابراہیمی، قربانی اور عید الاضحیٰ", true),
        IslamicEvent(18, 12, "یومِ شہادت سیدنا عثمان غنی ؓ", "Shahadat Hazrat Usman", "18 ذوالحجہ - شہادتِ جامع القرآن، ذوالنورین سیدنا عثمان غنی رضی اللہ عنہ")
    )

    /**
     * Converts Gregorian Calendar to Hijri Date (Kuwaiti algorithm + custom offset)
     */
    fun getHijriDate(calendar: Calendar = Calendar.getInstance(), dayAdjustment: Int = 0): HijriDate {
        val cal = calendar.clone() as Calendar
        if (dayAdjustment != 0) {
            cal.add(Calendar.DAY_OF_MONTH, dayAdjustment)
        }

        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val dayOfWeekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1 // 0 = Sun

        var y = year
        var m = month
        if (m < 3) {
            y -= 1
            m += 12
        }

        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)
        val jd = floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + b - 1524.5

        val z = jd - 1948440.0 + 10632.0
        val n = floor((z - 1) / 10631.0)
        val z1 = z - 10631.0 * n + 354.0
        val j = (floor((10985.0 - z1) / 5316.0)) * (floor((50.0 * z1) / 17719.0)) +
                (floor(z1 / 5670.0)) * (floor((43.0 * z1) / 15238.0))
        val z2 = z1 - (floor((30.0 - j) / 15.0)) * (floor((17719.0 * j) / 50.0)) -
                (floor(j / 16.0)) * (floor((15238.0 * j) / 43.0)) + 29.0
        val mHijri = floor((24.0 * z2) / 709.0).toInt()
        val dHijri = (z2 - floor((709.0 * mHijri) / 24.0)).toInt()
        val yHijri = (30.0 * n + j - 30.0).toInt()

        val safeMonth = (mHijri).coerceIn(1, 12)
        val monthUrdu = HIJRI_MONTHS_URDU[safeMonth - 1]
        val monthEng = HIJRI_MONTHS_ENGLISH[safeMonth - 1]
        val dayOfWeekUrdu = DAYS_URDU[dayOfWeekIndex.coerceIn(0, 6)]
        val dayOfWeekEng = SimpleDateFormat("EEEE", Locale.ENGLISH).format(cal.time)
        val gregFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH).format(cal.time)

        return HijriDate(
            day = dHijri,
            monthNumber = safeMonth,
            monthUrdu = monthUrdu,
            monthEnglish = monthEng,
            year = yHijri,
            dayOfWeekUrdu = dayOfWeekUrdu,
            dayOfWeekEnglish = dayOfWeekEng,
            gregorianFormatted = gregFormat
        )
    }

    fun getDaysForMonth(
        gregorianYear: Int,
        gregorianMonth: Int // 0-based
    ): List<IslamicCalendarDay> {
        val days = mutableListOf<IslamicCalendarDay>()
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, gregorianYear)
        cal.set(Calendar.MONTH, gregorianMonth)
        cal.set(Calendar.DAY_OF_MONTH, 1)

        val maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val today = Calendar.getInstance()

        for (d in 1..maxDays) {
            cal.set(Calendar.DAY_OF_MONTH, d)
            val hijri = getHijriDate(cal)
            val event = AHLE_SUNNAT_EVENTS.firstOrNull {
                it.hijriDay == hijri.day && it.hijriMonthNumber == hijri.monthNumber
            }
            val isToday = today.get(Calendar.YEAR) == gregorianYear &&
                    today.get(Calendar.MONTH) == gregorianMonth &&
                    today.get(Calendar.DAY_OF_MONTH) == d

            days.add(
                IslamicCalendarDay(
                    gregorianDay = d,
                    gregorianMonth = gregorianMonth + 1,
                    gregorianYear = gregorianYear,
                    hijriDay = hijri.day,
                    hijriMonthUrdu = hijri.monthUrdu,
                    hijriYear = hijri.year,
                    dayOfWeek = cal.get(Calendar.DAY_OF_WEEK),
                    event = event,
                    isToday = isToday
                )
            )
        }
        return days
    }
}
