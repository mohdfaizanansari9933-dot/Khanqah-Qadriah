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
        "ربیع الاول شریف",
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
        "Muharram-ul-Haram",
        "Safar-ul-Muzaffar",
        "Rabi-ul-Awwal",
        "Rabi-us-Sani (Ghyarween)",
        "Jamadi-ul-Awwal",
        "Jamadi-us-Sani",
        "Rajab-ul-Murajjab",
        "Shaban-ul-Moazzam",
        "Ramadan-ul-Mubarak",
        "Shawwal-ul-Mukarram",
        "Zul-Qadah",
        "Zul-Hijjah"
    )

    val DAYS_URDU = listOf(
        "اتوار",         // Sunday
        "پیر",           // Monday
        "منگل",          // Tuesday
        "بدھ",           // Wednesday
        "جمعرات",        // Thursday
        "جمعۃ المبارک",  // Friday
        "ہفتہ"           // Saturday
    )

    val DAYS_ENGLISH = listOf(
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday"
    )

    val AHLE_SUNNAT_EVENTS = listOf(
        // Muharram
        IslamicEvent(
            hijriDay = 1,
            hijriMonthNumber = 1,
            titleUrdu = "آغازِ نیا سالِ ہجری و شہادتِ فاروقِ اعظم ؓ",
            titleEnglish = "Islamic New Year & Shahadat Farooq-e-Azam (RA)",
            descriptionUrdu = "یکم محرم الحرام - آغازِ سالِ نو ہجری اور یومِ شہادت خلیفہ دوم امیر المؤمنین سیدنا عمر فاروق اعظم رضی اللہ عنہ۔",
            isMajorHoliday = true,
            buzurgZikr = "امیر المؤمنین حضرت عمر فاروق اعظم رضی اللہ عنہ وہ عظیم خلیفہ ہیں جن کے دور میں اسلام کو بے مثال وسعت و عدل نصیب ہوا۔ آپ کی شہادت یکم محرم کو ہوئی۔",
            fatihaDua = "سورۃ فاتحہ ۱ بار، سورۃ اخلاص ۳ بار، درودِ غوثیہ ۱۱ بار تلاوت فرما کر بارگاہِ فاروقِ اعظم رضی اللہ عنہ میں ایصالِ ثواب فرمائیں۔"
        ),
        IslamicEvent(
            hijriDay = 10,
            hijriMonthNumber = 1,
            titleUrdu = "یومِ عاشورہ و شہادت امامِ عالی مقام حسین ؓ",
            titleEnglish = "Youm-e-Ashura & Shahadat Imam Hussain (RA)",
            descriptionUrdu = "۱۰ محرم - میدانِ کربلا میں نواسہ رسول، جگر گوشہ بتول، سیدنا امام حسین علیہ السلام اور جملہ شہدائے کربلا کی شہادتِ عظمیٰ۔",
            isMajorHoliday = true,
            buzurgZikr = "سیدنا امام حسین رضی اللہ عنہ نے دینِ حق کی بقا اور سنتِ مصطفیٰ ﷺ کے احیاء کے لیے اپنے اہل و عیال سمیت جان نچھاور فرمائی اور باطل کے سامنے سر نہ جھکایا۔",
            fatihaDua = "شہدائے کربلا کے حضور کثرت سے درود و سلام اور نیاز و سبیل کا اہتمام فرمائیں اور بارگاہِ الٰہی میں ثباتِ قدم کی دعا کریں۔"
        ),
        IslamicEvent(
            hijriDay = 11,
            hijriMonthNumber = 1,
            titleUrdu = "ماہانہ گیارہویں شریف حضور غوثِ اعظم ؓ",
            titleEnglish = "Monthly Ghyarween Shareef (Muharram)",
            descriptionUrdu = "ہر اسلامی ماہ کی گیارہ تاریخ کو سرکار غوثِ اعظم سیدنا شیخ عبدالقادر جیلانی رضی اللہ عنہ کا ماہانہ ختم و نیاز مبارک۔",
            isGhyarween = true,
            buzurgZikr = "سلطان الاولیاء، قطبِ ربانی، غوثِ صمدانی سیدنا شیخ عبدالقادر جیلانی حسنی و حسینی رضی اللہ عنہ کے حضور ایصالِ ثواب۔",
            fatihaDua = "۱۱ بار درودِ غوثیہ، ۱ بار سورۃ فاتحہ، ۳ بار سورۃ اخلاص، اور شجرہ عالیہ قادریہ پڑھ کر نیاز و فاتحہ کریں۔"
        ),

        // Safar
        IslamicEvent(
            hijriDay = 11,
            hijriMonthNumber = 2,
            titleUrdu = "ماہانہ گیارہویں شریف حضور غوثِ پاک ؓ",
            titleEnglish = "Monthly Ghyarween Shareef (Safar)",
            descriptionUrdu = "ماہِ صفر المظفر کی گیارہویں شریف، حضور غوثِ اعظم رضی اللہ عنہ کا نیاز و فاتحہ۔",
            isGhyarween = true,
            buzurgZikr = "سیدنا غوث الاعظم رضی اللہ عنہ کی بارگاہِ عالیہ میں ایصالِ ثواب و دعائے خیر۔",
            fatihaDua = "نیازِ غوثیہ مع تلاوتِ کلامِ پاک و درود شریف۔"
        ),
        IslamicEvent(
            hijriDay = 20,
            hijriMonthNumber = 2,
            titleUrdu = "چہلم امام حسین ؓ و عرس شاہ عین الحق بدایونی ؒ",
            titleEnglish = "Chehlum Imam Hussain & Urs Shah Ainul Haq Badayuni",
            descriptionUrdu = "۲۰ صفر - چہلمِ سید الشہداء حضرت امام حسین علیہ السلام اور عرسِ مبارک حضرت شاہ عین الحق قادری بدایونی رحمۃ اللہ علیہ (خانقاہِ قادریہ بدایوں شریف)۔",
            isMajorHoliday = true,
            isUrsBadaun = true,
            buzurgZikr = "اکابرینِ خانقاہِ قادریہ بدایوں شریف کے عظیم بزرگ حضرت شاہ عین الحق قادری رحمۃ اللہ علیہ کا عرسِ اقدس۔ آپ علم و معرفت اور طریقتِ قادریہ کے تابندہ ستارے تھے۔",
            fatihaDua = "ختمِ قادریہ و فاتحہ شریف برائے اکابرِ بدایوں شریف اور شہدائے کربلا۔"
        ),
        IslamicEvent(
            hijriDay = 28,
            hijriMonthNumber = 2,
            titleUrdu = "یومِ وصال امام حسن مجتبیٰ ؓ",
            titleEnglish = "Wisal Imam Hassan Mujtaba (RA)",
            descriptionUrdu = "۲۸ صفر - وصالِ باکمال نواسہ رسول، سید الشہداء حضرت امام حسن مجتبیٰ رضی اللہ عنہ۔",
            buzurgZikr = "سید شباب اہل الجنہ حضرت امام حسن مجتبیٰ رضی اللہ عنہ جن کے اخلاقِ کریمانہ اور صلح سے امتِ مسلمہ کا خون محفوظ ہوا۔",
            fatihaDua = "بارگاہِ اہل بیتِ اطہار علیہم الرضوان میں نذرانہ درود و سلام۔"
        ),

        // Rabi-ul-Awwal
        IslamicEvent(
            hijriDay = 11,
            hijriMonthNumber = 3,
            titleUrdu = "ماہانہ گیارہویں شریف حضور غوثِ اعظم ؓ",
            titleEnglish = "Monthly Ghyarween Shareef (Rabi-ul-Awwal)",
            descriptionUrdu = "ماہِ ربیع الاول شریف کی گیارہویں شریف، شبِ ولادتِ باسعادت کے مبارک سائے میں نیازِ غوثیہ۔",
            isGhyarween = true,
            buzurgZikr = "سلطان الاولیاء سیدنا غوثِ اعظم رضی اللہ عنہ کا بابرکت ذکرِ خیر۔",
            fatihaDua = "درودِ غوثیہ و نیاز شریف۔"
        ),
        IslamicEvent(
            hijriDay = 12,
            hijriMonthNumber = 3,
            titleUrdu = "عید میلاد النبی ﷺ (جشنِ ولادتِ مصطفیٰ ﷺ)",
            titleEnglish = "Eid Milad-un-Nabi ﷺ (Jashn-e-Wiladat)",
            descriptionUrdu = "۱۲ ربیع الاول شریف - ولادتِ باسعادت فخرِ کائنات، رحمۃ للعالمین، سرورِ کونین حضرت محمد مصطفیٰ احمدِ مجتبیٰ صلی اللہ علیہ وآلہ وسلم۔ عیدوں کی عید، اعظم ترین خوشی۔",
            isMajorHoliday = true,
            buzurgZikr = "حضور سید دو عالم ﷺ کی تشریف آوری کائنات کی سب سے بڑی نعمت ہے۔ خانقاہِ قادریہ بدایوں شریف میں جشنِ میلادِ مصطفیٰ ﷺ نہایت عقیدت و احترام سے منایا جاتا ہے۔",
            fatihaDua = "سلام 'مصطفیٰ جانِ رحمت پہ لاکھوں سلام' اور کثرت سے درودِ پاک پڑھیں، لنگر و شیرینی تقسیم کریں۔"
        ),
        IslamicEvent(
            hijriDay = 26,
            hijriMonthNumber = 3,
            titleUrdu = "عرسِ قادری دولہا حضرت شاہ عبدالمجید بدایونی ؒ",
            titleEnglish = "Urs Qadri Dulha Shah Abdul Majeed Badayuni (RA)",
            descriptionUrdu = "۲۶ ربیع الاول شریف - عرسِ مبارک مجاہدِ ملت، قادری دولہا حضرت مولانا شاہ عبدالمجید قادری بدایونی رحمۃ اللہ علیہ (خانقاہِ عالیہ قادریہ بدایوں شریف)۔",
            isMajorHoliday = true,
            isUrsBadaun = true,
            buzurgZikr = "قادری دولہا حضرت شاہ عبدالمجید قادری بدایونی رحمۃ اللہ علیہ خانقاہِ قادریہ بدایوں شریف کے عظیم پیشوا، مجاہدِ آزادی اور تحریکِ پاکستان و تحریکِ خلافت کے ممتاز رہنما تھے۔ آپ کے روحانی فیضان سے لاکھوں قلوب منور ہوئے۔",
            fatihaDua = "فاتحہ شریف برائے قادری دولہا حضرت شاہ عبدالمجید بدایونی رحمۃ اللہ علیہ، تلاوتِ قرآنِ حکیم و نعت خوانی۔"
        ),

        // Rabi-us-Sani (Ghyarween Sharif)
        IslamicEvent(
            hijriDay = 11,
            hijriMonthNumber = 4,
            titleUrdu = "عرسِ پاک حضور غوثِ اعظم ؓ (بڑی گیارہویں شریف)",
            titleEnglish = "Urs Mubarak Ghaus-e-Azam (Badi Ghyarween Shareef)",
            descriptionUrdu = "۱۱ ربیع الثانی - عرسِ اقدس و مبارک شہنشاہِ بغداد، غوث الثقلین، سلطان الاولیاء سیدنا شیخ عبدالقادر جیلانی رضی اللہ عنہ۔ خانقاہِ قادریہ بدایوں شریف کا مرکزی و عظیم ترین اجتماع۔",
            isMajorHoliday = true,
            isGhyarween = true,
            buzurgZikr = "سیدنا غوثِ اعظم رضی اللہ عنہ نے فرمایا: 'میرا قدم ہر ولی اللہ کی گردن پر ہے۔' آپ سلسلہ عالیہ قادریہ کے بانی ہیں اور خانقاہِ عالیہ قادریہ بدایوں شریف کا اصل سرچشمہ فیض بغداد شریف ہی ہے۔",
            fatihaDua = "ختمِ غوثیہ شریف، قصیدہ غوثیہ شریف کی تلاوت، ۱۱۱ بار یا شیخ عبدالقادر جیلانی شیئاً للہ، اور شیرینی کی نیاز۔"
        ),

        // Jamadi-ul-Awwal
        IslamicEvent(
            hijriDay = 11,
            hijriMonthNumber = 5,
            titleUrdu = "ماہانہ گیارہویں شریف حضور غوثِ اعظم ؓ",
            titleEnglish = "Monthly Ghyarween Shareef (Jamadi-ul-Awwal)",
            descriptionUrdu = "ماہِ جمادی الاول کی گیارہویں شریف، حضور غوث الثقلین رضی اللہ عنہ کا نیاز و فاتحہ۔",
            isGhyarween = true,
            buzurgZikr = "غوثِ پاک رضی اللہ عنہ کے حضور نیاز و عریضہ۔",
            fatihaDua = "درودِ غوثیہ ۱۱ بار، فاتحہ شریف۔"
        ),
        IslamicEvent(
            hijriDay = 13,
            hijriMonthNumber = 5,
            titleUrdu = "یومِ سیدہ فاطمۃ الزہراء سلام اللہ علیہا",
            titleEnglish = "Youm Syeda Fatima-tuz-Zahra (SA)",
            descriptionUrdu = "۱۳ جمادی الاول - ذکرِ جمیل و وصالِ باکمال خاتونِ جنت، سیدۃ نساء العالمین حضرت سیدہ فاطمۃ الزہراء رضی اللہ عنہا۔",
            isMajorHoliday = true,
            buzurgZikr = "سیدہ کائنات، رسولِ خدا ﷺ کی چہیتی لختِ جگر حضرت فاطمہ رضی اللہ عنہا جن کی رضا رسول اللہ ﷺ کی رضا ہے۔",
            fatihaDua = "بارگاہِ سیدہ زہراء میں ایصالِ ثواب اور عفت و پاکیزگی کی دعا۔"
        ),
        IslamicEvent(
            hijriDay = 28,
            hijriMonthNumber = 5,
            titleUrdu = "عرس حضرت شاہ عبدالمقتدر قادری بدایونی ؒ",
            titleEnglish = "Urs Shah Abdul Muqtadir Badayuni (RA)",
            descriptionUrdu = "۲۸ جمادی الاول - عرسِ مبارک حضرت شاہ عبدالمقتدر قادری بدایونی رحمۃ اللہ علیہ، خانقاہِ عالیہ قادریہ بدایوں شریف۔",
            isUrsBadaun = true,
            buzurgZikr = "خانقاہِ قادریہ بدایوں شریف کے برگزیدہ شیخِ طریقت جن کی علمی اور روحانی خدمات تاریخ کا سنہرا باب ہیں۔",
            fatihaDua = "فاتحہ خوانی و تلاوتِ کلامِ پاک برائے اکابرِ بدایوں شریف۔"
        ),

        // Jamadi-us-Sani
        IslamicEvent(
            hijriDay = 8,
            hijriMonthNumber = 6,
            titleUrdu = "عرس حضرت شاہ اسامہ قادری بدایونی ؒ",
            titleEnglish = "Urs Shah Usama Badayuni (RA)",
            descriptionUrdu = "۸ جمادی الثانی - عرسِ مبارک حضرت شاہ اسامہ قادری بدایونی رحمۃ اللہ علیہ (خانقاہِ قادریہ بدایوں شریف)۔",
            isUrsBadaun = true,
            buzurgZikr = "سلسلہ قادریہ رزاقیہ کے جلیل القدر بزرگ اور سجادگانِ خانقاہِ قادریہ بدایوں شریف کے مایہ ناز فرد۔",
            fatihaDua = "فاتحہ شریف و ایصالِ ثواب۔"
        ),
        IslamicEvent(
            hijriDay = 11,
            hijriMonthNumber = 6,
            titleUrdu = "ماہانہ گیارہویں شریف حضور غوثِ پاک ؓ",
            titleEnglish = "Monthly Ghyarween Shareef (Jamadi-us-Sani)",
            descriptionUrdu = "ماہِ جمادی الثانی کی گیارہویں شریف، نیاز و فاتحہ حضور غوث الاعظم رضی اللہ عنہ۔",
            isGhyarween = true,
            buzurgZikr = "ذکرِ سلطان الاولیاء سیدنا غوثِ اعظم رضی اللہ عنہ۔",
            fatihaDua = "درودِ غوثیہ و فاتحہ شریف۔"
        ),
        IslamicEvent(
            hijriDay = 14,
            hijriMonthNumber = 6,
            titleUrdu = "عرس سیف اللہ المسلول حضرت شاہ فضلِ رسول بدایونی ؒ",
            titleEnglish = "Urs Saifullah Al-Maslool Shah Fazle Rasool Badayuni (RA)",
            descriptionUrdu = "۱۴ جمادی الثانی - عرسِ اقدس و عظیم الشان محی السنۃ، سیف اللہ المسلول، مجددِ مسلکِ حق حضرت علامہ شاہ فضلِ رسول قادری بدایونی قدس سرہ العزیز (خانقاہِ قادریہ بدایوں شریف)۔",
            isMajorHoliday = true,
            isUrsBadaun = true,
            buzurgZikr = "سیف اللہ المسلول حضرت شاہ فضلِ رسول قادری بدایونی رحمۃ اللہ علیہ خانقاہِ قادریہ بدایوں شریف کے عظیم ترین تاجدار ہیں۔ آپ نے فتنہ وہابیت و نجدیہ کے رد میں بے مثل کتاب 'البوارق المحمدیہ' اور دیگر تصانیف تحریر فرمائیں اور مسلکِ اہل سنت و جماعت کا پرچم بلند فرمایا۔",
            fatihaDua = "تلاوتِ قرآن مجید، قصیدہ غوثیہ، فاتحہ برائے سیف اللہ المسلول حضرت شاہ فضلِ رسول بدایونی قدس سرہ، اور خصوصی دعائے عافیت۔"
        ),

        // Rajab
        IslamicEvent(
            hijriDay = 6,
            hijriMonthNumber = 7,
            titleUrdu = "عرس خواجہ غریب نواز ؒ (سلطان الہند اجمیر شریف)",
            titleEnglish = "Urs Khwaja Ghareeb Nawaz (Ajmer Shareef)",
            descriptionUrdu = "۶ رجب المرجب - عرسِ مبارک سلطان الہند، عطائے رسول، خواجہ سید معین الدین چشتی سنجری اجمیری رحمۃ اللہ علیہ۔",
            isMajorHoliday = true,
            buzurgZikr = "خواجہ غریب نواز رحمۃ اللہ علیہ نے ہندوستان میں اسلام کی شمع روشن کی اور لاکھوں لوگوں کو کلمہ توحید پڑھایا۔",
            fatihaDua = "فاتحہ شریف، تلاوتِ کلامِ پاک، نیازِ خواجہ غریب نواز۔"
        ),
        IslamicEvent(
            hijriDay = 11,
            hijriMonthNumber = 7,
            titleUrdu = "ماہانہ گیارہویں شریف حضور غوثِ اعظم ؓ",
            titleEnglish = "Monthly Ghyarween Shareef (Rajab)",
            descriptionUrdu = "ماہِ رجب کی گیارہویں شریف، نیازِ سرکارِ بغداد رضی اللہ عنہ۔",
            isGhyarween = true,
            buzurgZikr = "سلطان الاولیاء سیدنا غوثِ اعظم رضی اللہ عنہ کا فیضِ عام۔",
            fatihaDua = "درودِ غوثیہ و فاتحہ شریف۔"
        ),
        IslamicEvent(
            hijriDay = 22,
            hijriMonthNumber = 7,
            titleUrdu = "کونڈے امام جعفر صادق ؓ (نیازِ حسینی)",
            titleEnglish = "Kunday Imam Jafar Sadiq (RA)",
            descriptionUrdu = "۲۲ رجب المرجب - نیاز و کونڈے مبارک حضرت امام جعفر صادق رضی اللہ عنہ و ایصالِ ثواب۔",
            isMajorHoliday = true,
            buzurgZikr = "حضرت امام جعفر صادق رضی اللہ عنہ ائمہ اہل بیت میں علوم و معارف کے بحرِ زخار ہیں۔",
            fatihaDua = "مٹی کے پاک کونڈوں میں کھیر پوری پر فاتحہ اور امام جعفر صادق رضی اللہ عنہ کے وسیلے سے دعائے حاجات۔"
        ),
        IslamicEvent(
            hijriDay = 27,
            hijriMonthNumber = 7,
            titleUrdu = "شبِ معراج النبی ﷺ (واقعہ معراج شریف)",
            titleEnglish = "Shab-e-Meraj-un-Nabi ﷺ",
            descriptionUrdu = "۲۷ رجب المرجب - سرورِ کائنات ﷺ کا سیرِ افلاک، لامکاں اور بارگاہِ رب العزت میں بالمشافہ حاضری کا معجزہ معراج شریف۔",
            isMajorHoliday = true,
            buzurgZikr = "مسجد حرام سے مسجد اقصی اور وہاں سے سدرۃ المنتہیٰ و قاب قوسین تک کا روح پرور سفر، جس میں امت کو نماز کا تحفہ عطا ہوا۔",
            fatihaDua = "شب بیداری، نوافل، صلوٰۃ التسبیح اور دعائے مغفرت۔"
        ),

        // Shaban
        IslamicEvent(
            hijriDay = 11,
            hijriMonthNumber = 8,
            titleUrdu = "ماہانہ گیارہویں شریف حضور غوثِ پاک ؓ",
            titleEnglish = "Monthly Ghyarween Shareef (Shaban)",
            descriptionUrdu = "ماہِ شعبان المعظم کی گیارہویں شریف، فاتحہ و نیاز غوث الثقلین رضی اللہ عنہ۔",
            isGhyarween = true,
            buzurgZikr = "سرکار غوثِ اعظم رضی اللہ عنہ کا پرنور ذکر۔",
            fatihaDua = "درود شریف و تلاوتِ قرآن۔"
        ),
        IslamicEvent(
            hijriDay = 15,
            hijriMonthNumber = 8,
            titleUrdu = "شبِ برات (لیلۃ البرات - مغفرت کی رات)",
            titleEnglish = "Shab-e-Barat (Night of Forgiveness)",
            descriptionUrdu = "۱۵ شعبان المعظم - رحمت، بخشش اور فیصلوں کی مبارک رات۔ سال بھر کے رزق اور عمر کے فیصلے نازل ہوتے ہیں۔",
            isMajorHoliday = true,
            buzurgZikr = "رسول اللہ ﷺ نے فرمایا کہ اس رات اللہ تعالیٰ بنو کلب کی بکریوں کے بالوں سے بھی زیادہ گنہگاروں کی مغفرت فرماتا ہے۔",
            fatihaDua = "۶ نفل بعد مغرب (درازئ عمر، حفاظت از بلایات، رزق حلال کی نیت سے)، سورۃ یٰسین کی تلاوت، اور دعائے نصف شعبان۔"
        ),

        // Ramadan
        IslamicEvent(
            hijriDay = 1,
            hijriMonthNumber = 9,
            titleUrdu = "آغازِ ماہِ مبارک رمضان و نزولِ قرآن",
            titleEnglish = "First Day of Blessed Ramadan",
            descriptionUrdu = "یکم رمضان المبارک - صیام، قیام، رحمت، مغفرت اور جہنم سے نجات کے مبارک مہینے کا آغاز۔",
            isMajorHoliday = true,
            buzurgZikr = "رمضان المبارک وہ مبارک مہینہ ہے جس میں قرآنِ مجید نازل فرمایا گیا اور نیکیوں کا ثواب ستر گنا بڑھا دیا جاتا ہے۔",
            fatihaDua = "تراویح، تلاوتِ قرآن اور کثرتِ استغفار۔"
        ),
        IslamicEvent(
            hijriDay = 10,
            hijriMonthNumber = 9,
            titleUrdu = "یومِ وصال ام المؤمنین حضرت خدیجۃ الکبریٰ ؓ",
            titleEnglish = "Wisal Umm-ul-Momineen Syeda Khadija (RA)",
            descriptionUrdu = "۱۰ رمضان المبارک - وصالِ باکمال محسنہ اسلام، رفیقۂ رسول ﷺ، ام المؤمنین سیدہ خدیجہ رضی اللہ عنہا۔",
            buzurgZikr = "حضرت خدیجۃ الکبریٰ رضی اللہ عنہا نے اسلام کی سب سے پہلی تصدیق فرمائی اور اپنی تمام دولت اسلام پر نچھاور کر دی۔",
            fatihaDua = "ام المؤمنین کے حضور ایصالِ ثواب اور دعائے بخشش۔"
        ),
        IslamicEvent(
            hijriDay = 11,
            hijriMonthNumber = 9,
            titleUrdu = "ماہانہ گیارہویں شریف در رمضان المبارک",
            titleEnglish = "Monthly Ghyarween Shareef (Ramadan)",
            descriptionUrdu = "رمضان المبارک کے پہلے عشرہ رحمت کی گیارہویں شریف، نیازِ سرکار غوثِ اعظم رضی اللہ عنہ۔",
            isGhyarween = true,
            buzurgZikr = "غوثِ پاک رضی اللہ عنہ کے طفیل رحمتِ الٰہی کی طلب۔",
            fatihaDua = "افطار کے وقت نیازِ غوثیہ و درود شریف۔"
        ),
        IslamicEvent(
            hijriDay = 17,
            hijriMonthNumber = 9,
            titleUrdu = "یومِ فتح و غزوہ بدر (یومِ فرقان)",
            titleEnglish = "Battle of Badr & Youm-ul-Furqan",
            descriptionUrdu = "۱۷ رمضان المبارک - حق و باطل کا پہلا فیصلہ کن معرکہ، ۳۱۳ جانثارانِ اسلام کی فتحِ عظیم۔",
            isMajorHoliday = true,
            buzurgZikr = "شہدائے بدر کے اسماء گرامی اور ان کی قربانیوں کا تذکرہ مصائب و بلایات سے حفاظت کا ذریعہ ہے۔",
            fatihaDua = "شہدائے بدر کے ناموں کی تلاوت و ایصالِ ثواب۔"
        ),
        IslamicEvent(
            hijriDay = 21,
            hijriMonthNumber = 9,
            titleUrdu = "یومِ شہادت مولائے کائنات حضرت علی المرتضیٰ ؓ",
            titleEnglish = "Shahadat Maula Ali Murtaza (KW)",
            descriptionUrdu = "۲۱ رمضان المبارک - شہادتِ اسد اللہ الغالب، باب مدینۃ العلم، امیر المؤمنین حضرت علی بن ابی طالب کرم اللہ وجہہ الکریم۔",
            isMajorHoliday = true,
            buzurgZikr = "سلسلہ عالیہ قادریہ کی بنیاد حضرت علی المرتضیٰ کرم اللہ وجہہ الکریم کی ذاتِ والا صفات پر ہے، جن سے جملہ سلاسلِ طریقت پھوٹتے ہیں۔",
            fatihaDua = "بارگاہِ مولائے کائنات میں نذرانہ درود و سلام اور نیازِ حیدری۔"
        ),
        IslamicEvent(
            hijriDay = 27,
            hijriMonthNumber = 9,
            titleUrdu = "شبِ قدر (لیلۃ القدر - ہزار مہینوں سے افضل رات)",
            titleEnglish = "Shab-e-Qadr (Laylat-ul-Qadr)",
            descriptionUrdu = "۲۷ رمضان المبارک - شبِ قدر جس کی عبادت ہزار مہینوں کی عبادت سے افضل و برتر ہے۔",
            isMajorHoliday = true,
            buzurgZikr = "اس رات ملائکہ اور روح الامین اپنے رب کے حکم سے ہر امرِ خیر لے کر اترتے ہیں۔",
            fatihaDua = "دعائے شبِ قدر: 'اللّٰهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي'، شب بیداری و تلاوتِ قرآن۔"
        ),

        // Shawwal
        IslamicEvent(
            hijriDay = 1,
            hijriMonthNumber = 10,
            titleUrdu = "عید الفطر المبارک (جشنِ انعام)",
            titleEnglish = "Eid-ul-Fitr Mubarak",
            descriptionUrdu = "یکم شوال المکرم - ماہِ صیام کی تکمیل پر اللہ تعالیٰ کی طرف سے روزے داروں کے لیے عید اور خوشی کا دن۔",
            isMajorHoliday = true,
            buzurgZikr = "نمازِ عید الفطر سے قبل صدقۃ الفطر ادا کرنا واجب ہے تاکہ مساکین بھی خوشی میں شریک ہو سکیں۔",
            fatihaDua = "نمازِ عید کی ادائیگی، مبارکباد اور اہل قبور کے لیے ایصالِ ثواب۔"
        ),
        IslamicEvent(
            hijriDay = 11,
            hijriMonthNumber = 10,
            titleUrdu = "ماہانہ گیارہویں شریف حضور غوثِ پاک ؓ",
            titleEnglish = "Monthly Ghyarween Shareef (Shawwal)",
            descriptionUrdu = "شوال المکرم کی گیارہویں شریف، نیازِ غوث الثقلین رضی اللہ عنہ۔",
            isGhyarween = true,
            buzurgZikr = "سرکارِ غوثیت مآب رضی اللہ عنہ کے حضور نیاز۔",
            fatihaDua = "درودِ غوثیہ و فاتحہ۔"
        ),
        IslamicEvent(
            hijriDay = 25,
            hijriMonthNumber = 10,
            titleUrdu = "عرسِ اعلیٰ حضرت امام احمد رضا خان قادری ؒ",
            titleEnglish = "Urs-e-Razvi (Ala Hazrat Imam Ahmad Raza)",
            descriptionUrdu = "۲۵ شوال المکرم - عرسِ مبارک مجددِ اعظم، امامِ اہل سنت، الشاہ امام احمد رضا خان قادری بریلوی قدس سرہ (عرسِ رضوی)۔",
            isMajorHoliday = true,
            buzurgZikr = "اعلیٰ حضرت امام احمد رضا خان قادری علیہ الرحمہ نے عشقِ رسول ﷺ کو ایمان کی روح قرار دیا اور مسلکِ حق اہل سنت کی عظیم ترین علمی پاسبانی فرمائی۔",
            fatihaDua = "حدائقِ بخشش سے کلام اور سلامِ رضا 'مصطفیٰ جانِ رحمت پہ لاکھوں سلام' کی تلاوت و ایصالِ ثواب۔"
        ),

        // Zul-Qadah
        IslamicEvent(
            hijriDay = 11,
            hijriMonthNumber = 11,
            titleUrdu = "ماہانہ گیارہویں شریف حضور غوثِ اعظم ؓ",
            titleEnglish = "Monthly Ghyarween Shareef (Zul-Qadah)",
            descriptionUrdu = "ذوالقعدۃ الحرام کی گیارہویں شریف، ختم و نیازِ سرکارِ بغداد رضی اللہ عنہ۔",
            isGhyarween = true,
            buzurgZikr = "ذکرِ سلطان الاولیاء سیدنا غوثِ اعظم رضی اللہ عنہ۔",
            fatihaDua = "درودِ غوثیہ و فاتحہ شریف۔"
        ),

        // Zul-Hijjah
        IslamicEvent(
            hijriDay = 8,
            hijriMonthNumber = 12,
            titleUrdu = "آغازِ حجِ بیت اللہ (یومِ ترویہ)",
            titleEnglish = "Beginning of Hajj (Youm-ut-Tarwiyah)",
            descriptionUrdu = "۸ ذوالحجۃ الحرام - مناسکِ حج کا آغاز، حجاجِ کرام کا منیٰ کی طرف روانہ ہونا۔",
            isMajorHoliday = true,
            buzurgZikr = "حج اسلام کا پانچواں بنیادی رکن ہے جو صاحبِ استطاعت پر زندگی میں ایک بار فرض ہے۔",
            fatihaDua = "کثرت سے تلبیہ: 'لبیک اللّٰہم لبیک' اور تکبیراتِ تشریق۔"
        ),
        IslamicEvent(
            hijriDay = 9,
            hijriMonthNumber = 12,
            titleUrdu = "یومِ عرفہ (وقوفِ عرفات - حجِ اکبر)",
            titleEnglish = "Day of Arafah (Waqoof-e-Arafat)",
            descriptionUrdu = "۹ ذوالحجہ - وقوفِ میدانِ عرفات، حج کا اعظم ترین رکن۔ غیر حجاج کے لیے یومِ عرفہ کا روزہ سال بھر کے گناہوں کا کفارہ ہے۔",
            isMajorHoliday = true,
            buzurgZikr = "میدانِ عرفات میں رسول اللہ ﷺ نے اپنا تاریخی خطبہ حجتہ الوداع ارشاد فرمایا۔",
            fatihaDua = "استغفار، تسبیحات اور دعائے عرفہ۔"
        ),
        IslamicEvent(
            hijriDay = 10,
            hijriMonthNumber = 12,
            titleUrdu = "عید الاضحیٰ المبارک و قربانیِ خلیل",
            titleEnglish = "Eid-ul-Adha Mubarak",
            descriptionUrdu = "۱۰ ذوالحجہ - سنتِ ابراہیمی، عظیم الشان قربانی اور خوشی کا دن۔",
            isMajorHoliday = true,
            buzurgZikr = "حضرت ابراہیم علیہ السلام اور حضرت اسماعیل علیہ السلام کی تسلیم و رضا کی یادگار۔",
            fatihaDua = "نمازِ عید، تکبیراتِ تشریق، اور گوشتِ قربانی کی تقسیم۔"
        ),
        IslamicEvent(
            hijriDay = 11,
            hijriMonthNumber = 12,
            titleUrdu = "ماہانہ گیارہویں شریف حضور غوثِ پاک ؓ",
            titleEnglish = "Monthly Ghyarween Shareef (Zul-Hijjah)",
            descriptionUrdu = "ایامِ تشریق کے دوران گیارہویں شریف، نیازِ سرکارِ بغداد رضی اللہ عنہ۔",
            isGhyarween = true,
            buzurgZikr = "سلطان الاولیاء غوث الاعظم رضی اللہ عنہ کا فیض۔",
            fatihaDua = "درودِ غوثیہ و ایصالِ ثواب۔"
        ),
        IslamicEvent(
            hijriDay = 17,
            hijriMonthNumber = 12,
            titleUrdu = "عرس تاجدارِ اہل سنت حضرت علامہ شاہ عبدالقادر بدایونی ؒ",
            titleEnglish = "Urs Tajdar-e-Ahle Sunnat Shah Abdul Qadir Badayuni (RA)",
            descriptionUrdu = "۱۷ ذوالحجۃ الحرام - عرسِ مبارک تاجدارِ اہل سنت، رئیس التالیفین حضرت علامہ شاہ عبدالقادر قادری بدایونی قدس سرہ العزیز (خانقاہِ عالیہ قادریہ بدایوں شریف)۔",
            isMajorHoliday = true,
            isUrsBadaun = true,
            buzurgZikr = "تاجدارِ اہل سنت حضرت علامہ شاہ عبدالقادر قادری بدایونی قدس سرہ العزیز خانقاہِ قادریہ بدایوں شریف کے عظیم فقیہ، مفسر اور مناظر تھے۔ آپ کی کتب و فتاویٰ مسلکِ حق کے لیے مینارۂ نور ہیں۔",
            fatihaDua = "فاتحہ شریف برائے تاجدارِ اہل سنت حضرت علامہ شاہ عبدالقادر بدایونی قدس سرہ، تلاوتِ قرآن و درود شریف۔"
        ),
        IslamicEvent(
            hijriDay = 18,
            hijriMonthNumber = 12,
            titleUrdu = "یومِ شہادت سیدنا عثمانِ غنی ذوالنورین ؓ",
            titleEnglish = "Shahadat Sayyiduna Usman Ghani (RA)",
            descriptionUrdu = "۱۸ ذوالحجہ - یومِ شہادت خلیفہ سوم، جامع القرآن، صاحبِ ہجرتین سیدنا عثمان بن عفان رضی اللہ عنہ۔",
            isMajorHoliday = true,
            buzurgZikr = "سیدنا عثمان غنی رضی اللہ عنہ جن پر رسول اللہ ﷺ نے اپنی دو صاحبزادیاں یکے بعد دیگرے بیاہ دیں اور آپ ذوالنورین کہلائے۔",
            fatihaDua = "بارگاہِ عثمان غنی رضی اللہ عنہ میں ایصالِ ثواب۔"
        ),
        IslamicEvent(
            hijriDay = 24,
            hijriMonthNumber = 12,
            titleUrdu = "عرس حضرت شاہ محمد عتیق میاں بدایونی ؒ",
            titleEnglish = "Urs Shah Mohammad Ateeq Miya Badayuni (RA)",
            descriptionUrdu = "۲۴ ذوالحجہ - عرسِ مبارک حضرت شاہ محمد عتیق میاں قادری بدایونی رحمۃ اللہ علیہ (خانقاہِ قادریہ بدایوں شریف)۔",
            isUrsBadaun = true,
            buzurgZikr = "خانقاہِ قادریہ بدایوں شریف کے مقتدر سجادہ نشین جن کے تقویٰ، للہیت اور روحانی فیض سے خلقِ خدا فیضیاب ہوئی۔",
            fatihaDua = "فاتحہ خوانی برائے سجادہ نشینانِ خانقاہِ عالیہ قادریہ بدایوں شریف۔"
        )
    )

    /**
     * Converts Gregorian Calendar to Hijri Date with moon sighting day adjustment.
     */
    fun getHijriDate(calendar: Calendar = Calendar.getInstance(), moonOffset: Int = 0): HijriDate {
        val cal = calendar.clone() as Calendar
        if (moonOffset != 0) {
            cal.add(Calendar.DAY_OF_MONTH, moonOffset)
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
        val dayOfWeekEng = DAYS_ENGLISH[dayOfWeekIndex.coerceIn(0, 6)]
        val gregFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH).format(calendar.time)

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

    /**
     * Generates a complete 30-day Hijri calendar grid for the specified Hijri month.
     * Maps each Hijri day (1..30) to its corresponding Gregorian date and checks for events.
     */
    fun get30DayHijriMonthGrid(
        targetHijriMonth: Int,
        targetHijriYear: Int,
        moonOffset: Int = 0
    ): List<IslamicCalendarDay> {
        val days = mutableListOf<IslamicCalendarDay>()

        // Search backward and forward around current time to anchor day 1 of the target Hijri month
        val anchorCal = Calendar.getInstance()
        var foundDay1Cal: Calendar? = null

        // Sweep +/- 180 days to find exact Gregorian start of targetHijriMonth
        val testCal = Calendar.getInstance()
        testCal.add(Calendar.DAY_OF_MONTH, -180)
        for (i in 0..365) {
            val hijri = getHijriDate(testCal, moonOffset)
            if (hijri.monthNumber == targetHijriMonth && hijri.year == targetHijriYear && hijri.day == 1) {
                foundDay1Cal = testCal.clone() as Calendar
                break
            }
            testCal.add(Calendar.DAY_OF_MONTH, 1)
        }

        // If not found in range, fall back to current calendar
        val startCal = foundDay1Cal ?: Calendar.getInstance().apply {
            val todayHijri = getHijriDate(this, moonOffset)
            add(Calendar.DAY_OF_MONTH, -(todayHijri.day - 1))
        }

        val todayCal = Calendar.getInstance()
        val gregDayFormat = SimpleDateFormat("d MMM", Locale.ENGLISH)

        val runner = startCal.clone() as Calendar
        for (hijriDay in 1..30) {
            val currentGregDay = runner.get(Calendar.DAY_OF_MONTH)
            val currentGregMonth = runner.get(Calendar.MONTH) + 1
            val currentGregYear = runner.get(Calendar.YEAR)
            val dayOfWeek = runner.get(Calendar.DAY_OF_WEEK) // 1=Sun .. 7=Sat
            val dayOfWeekIndex = dayOfWeek - 1

            val isToday = todayCal.get(Calendar.YEAR) == currentGregYear &&
                    todayCal.get(Calendar.MONTH) == runner.get(Calendar.MONTH) &&
                    todayCal.get(Calendar.DAY_OF_MONTH) == currentGregDay

            val event = AHLE_SUNNAT_EVENTS.firstOrNull {
                it.hijriDay == hijriDay && it.hijriMonthNumber == targetHijriMonth
            }

            val isGhyarween = hijriDay == 11
            val isUrsBadaun = event?.isUrsBadaun == true

            days.add(
                IslamicCalendarDay(
                    gregorianDay = currentGregDay,
                    gregorianMonth = currentGregMonth,
                    gregorianYear = currentGregYear,
                    gregorianDateFormatted = gregDayFormat.format(runner.time),
                    hijriDay = hijriDay,
                    hijriMonthNumber = targetHijriMonth,
                    hijriMonthUrdu = HIJRI_MONTHS_URDU[targetHijriMonth - 1],
                    hijriMonthEnglish = HIJRI_MONTHS_ENGLISH[targetHijriMonth - 1],
                    hijriYear = targetHijriYear,
                    dayOfWeek = dayOfWeek,
                    dayOfWeekUrdu = DAYS_URDU[dayOfWeekIndex.coerceIn(0, 6)],
                    dayOfWeekEnglish = DAYS_ENGLISH[dayOfWeekIndex.coerceIn(0, 6)],
                    event = event,
                    isToday = isToday,
                    isGhyarween = isGhyarween,
                    isUrsBadaun = isUrsBadaun
                )
            )

            runner.add(Calendar.DAY_OF_MONTH, 1)
        }

        return days
    }
}
