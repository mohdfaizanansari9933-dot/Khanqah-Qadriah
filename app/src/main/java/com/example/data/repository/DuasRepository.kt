package com.example.data.repository

import com.example.data.model.Dua
import com.example.data.model.DuaCategory

object DuasRepository {

    val DUAS_LIST: List<Dua> = listOf(
        // Morning & Evening
        Dua(
            id = "dua_morning_1",
            titleUrdu = "صبح کے وقت کی دعا",
            category = DuaCategory.MORNING_EVENING,
            arabicText = "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ، وَالْحَمْدُ لِلَّهِ، لَا إِلٰهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ",
            urduTranslation = "ہم نے صبح کی اور اللہ کے سارے ملک نے صبح کی، اور تمام تعریفیں اللہ کے لیے ہیں، اللہ کے سوا کوئی معبود نہیں وہ اکیلا ہے اس کا کوئی شریک نہیں، اسی کی بادشاہی ہے اور اسی کی تعریف ہے اور وہ ہر چیز پر قادر ہے۔",
            transliteration = "Asbahna wa asbahal mulku lillah, walhamdu lillah, la ilaha illallahu wahdahu la shareeka lah...",
            reference = "صحیح مسلم شریف، حدیث ۲۷۲۳",
            recommendedCount = 1
        ),
        Dua(
            id = "dua_evening_1",
            titleUrdu = "شام کے وقت کی دعا",
            category = DuaCategory.MORNING_EVENING,
            arabicText = "أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ، وَالْحَمْدُ لِلَّهِ، لَا إِلٰهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ",
            urduTranslation = "ہم نے شام کی اور اللہ کے سارے ملک نے شام کی، اور تمام تعریفیں اللہ کے لیے ہیں، اللہ کے سوا کوئی معبود نہیں وہ اکیلا ہے اس کا کوئی شریک نہیں، اسی کی بادشاہی اور اسی کی تعریف ہے اور وہ ہر چیز پر قادر ہے۔",
            transliteration = "Amsayna wa amsal mulku lillah, walhamdu lillah...",
            reference = "صحیح مسلم شریف",
            recommendedCount = 1
        ),
        Dua(
            id = "sayyidul_istighfar",
            titleUrdu = "سید الاستغفار (مغفرت کی سب سے بڑی دعا)",
            category = DuaCategory.MORNING_EVENING,
            arabicText = "اللَّهُمَّ أَنْتَ رَبِّي لَا إِلٰهَ إِلَّا أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَىٰ عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ، وَأَبُوءُ بِذَنْبِي فَاغْفِرْ لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ",
            urduTranslation = "اے اللہ! تو میرا رب ہے، تیرے سوا کوئی معبود نہیں، تو نے مجھے پیدا کیا اور میں تیرا بندہ ہوں، اور میں اپنی طاقت کے مطابق تیرے عہد اور وعدے پر قائم ہوں، میں نے جو برائیاں کیں ان کے شر سے تیری پناہ مانگتا ہوں، میں اپنے اوپر تیری نعمتوں کا اعتراف کرتا ہوں اور اپنے گناہوں کا اعتراف کرتا ہوں، پس مجھے بخش دے کیونکہ تیرے سوا کوئی گناہوں کو بخشنے والا نہیں۔",
            transliteration = "Allahumma Anta Rabbee la ilaha illa Anta, khalaqtanee wa ana 'abduka...",
            reference = "صحیح بخاری شریف، حدیث ۶۳۰۶",
            benefitsUrdu = "حضور ﷺ نے فرمایا: جس نے یقین کے ساتھ اسے دن میں پڑھا اور اسی دن شام سے پہلے انتقال کر گیا وہ اہل جنت میں سے ہے۔",
            recommendedCount = 1
        ),

        // After Salah
        Dua(
            id = "dua_after_salah_1",
            titleUrdu = "فرض نماز کے بعد کی دعا",
            category = DuaCategory.AFTER_SALAH,
            arabicText = "اللَّهُمَّ أَنْتَ السَّلَامُ وَمِنْكَ السَّلَامُ، تَبَارَكْتَ يَا ذَا الْجَلَالِ وَالْإِكْرَامِ",
            urduTranslation = "اے اللہ! تو ہی سراپا سلامتی ہے اور تیری ہی طرف سے سلامتی ہے، تو بڑی برکت والا ہے اے عظمت اور بزرگی والے۔",
            transliteration = "Allahumma Antas Salamu wa minkas salamu, tabarakta ya Zal Jalali wal Ikram",
            reference = "صحیح مسلم، حدیث ۵۹۱",
            recommendedCount = 1
        ),
        Dua(
            id = "dua_tasbeeh_fatima",
            titleUrdu = "تسبیحِ فاطمہ بعد نماز",
            category = DuaCategory.AFTER_SALAH,
            arabicText = "سُبْحَانَ اللَّهِ (۳۳ بار) ، الْحَمْدُ لِلَّهِ (۳۳ بار) ، اللَّهُ أَكْبَرُ (۳۴ بار)",
            urduTranslation = "اللہ پاک ہے (۳۳ مرتبہ)، تمام تعریفیں اللہ ہی کے لیے ہیں (۳۳ مرتبہ)، اللہ سب سے بڑا ہے (۳۴ مرتبہ)۔",
            transliteration = "SubhanAllah (33x), Alhamdulillah (33x), Allahu Akbar (34x)",
            reference = "صحیح بخاری و صحیح مسلم",
            benefitsUrdu = "گناہ سمندر کے جھاگ کے برابر بھی ہوں تو معاف کر دیے جاتے ہیں اور جسمانی تھکن دور ہوتی ہے۔",
            recommendedCount = 100
        ),

        // Sleep & Waking
        Dua(
            id = "dua_sleep",
            titleUrdu = "سوتے وقت کی مسنون دعا",
            category = DuaCategory.SLEEP_WAKE,
            arabicText = "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
            urduTranslation = "اے اللہ! میں تیرے نام کے ساتھ ہی مرتا (سوتا) ہوں اور جیتا (جاگتا) ہوں۔",
            transliteration = "BismikAllahumma amootu wa ahya",
            reference = "صحیح بخاری، حدیث ۶۳۲۴",
            recommendedCount = 1
        ),
        Dua(
            id = "dua_wake_up",
            titleUrdu = "نیند سے بیدار ہونے کی دعا",
            category = DuaCategory.SLEEP_WAKE,
            arabicText = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ",
            urduTranslation = "تمام تعریفیں اس اللہ کے لیے ہیں جس نے ہمیں مارنے کے بعد زندہ کیا اور اسی کی طرف اٹھ کر جانا ہے۔",
            transliteration = "Alhamdu lillahil lazee ahyana ba'da ma amatana wa ilayhin nushoor",
            reference = "صحیح بخاری، حدیث ۶۳۱۲",
            recommendedCount = 1
        ),

        // Eating & Daily Life
        Dua(
            id = "dua_eating_start",
            titleUrdu = "کھانا شروع کرنے کی دعا",
            category = DuaCategory.DAILY_LIFE,
            arabicText = "بِسْمِ اللَّهِ وَعَلَىٰ بَرَكَةِ اللَّهِ",
            urduTranslation = "اللہ کے نام سے اور اللہ کی برکت کے ساتھ (ہم نے کھانا شروع کیا)۔",
            transliteration = "Bismillahi wa 'ala barakatillah",
            reference = "المستدرک للحاکم، مشکوٰۃ شریف",
            recommendedCount = 1
        ),
        Dua(
            id = "dua_eating_finish",
            titleUrdu = "کھانے کے بعد کی دعا",
            category = DuaCategory.DAILY_LIFE,
            arabicText = "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنَا وَسَقَانَا وَجَعَلَنَا مِنَ الْمُسْلِمِينَ",
            urduTranslation = "تمام تعریفیں اس اللہ کے لیے ہیں جس نے ہمیں کھلایا، پلایا اور مسلمانوں میں سے بنایا۔",
            transliteration = "Alhamdu lillahil lazee at'amana wa saqana wa ja'alana minal muslimeen",
            reference = "جامع ترمذی، حدیث ۳۴۵۷",
            recommendedCount = 1
        ),
        Dua(
            id = "dua_leaving_home",
            titleUrdu = "گھر سے نکلتے وقت کی دعا",
            category = DuaCategory.DAILY_LIFE,
            arabicText = "بِسْمِ اللَّهِ، تَوَكَّلْتُ عَلَى اللَّهِ، وَلَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ",
            urduTranslation = "اللہ کے نام سے، میں نے اللہ پر بھروسہ کیا، اور گناہ سے بچنے اور نیکی کرنے کی کوئی طاقت نہیں مگر اللہ کی توفیق سے۔",
            transliteration = "Bismillahi tawakkaltu 'alallahi, wa la hawla wa la quwwata illa billah",
            reference = "جامع ترمذی، حدیث ۳۴۲۶",
            benefitsUrdu = "فرشتہ پکارتا ہے: تو ہدایت پا گیا، تیری کفایت کی گئی اور تجھے بچا لیا گیا۔",
            recommendedCount = 1
        ),

        // Travel
        Dua(
            id = "dua_travel",
            titleUrdu = "سواری اور سفر کی دعا",
            category = DuaCategory.TRAVEL,
            arabicText = "سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَٰذَا وَمَا كُنَّا لَهُ مُقْرِنِينَ وَإِنَّا إِلَىٰ رَبِّنَا لَمُنقَلِبُونَ",
            urduTranslation = "پاکی ہے اسے جس نے اس (سواری) کو ہمارے قابو میں کر دیا حالانکہ ہم اسے قابو کرنے والے نہ تھے، اور بیشک ہم اپنے رب ہی کی طرف لوٹنے والے ہیں۔",
            transliteration = "Subhanal lazee sakh-khara lana haza wa ma kunna lahoo muqrineen, wa inna ila Rabbina lamunqaliboon",
            reference = "قرآن مجید (سورۃ الزخرف، آیت ۱۳-۱۴) / سنن ابی داؤد",
            recommendedCount = 1
        ),

        // Masjid
        Dua(
            id = "dua_enter_masjid",
            titleUrdu = "مسجد میں داخل ہونے کی دعا",
            category = DuaCategory.MASJID,
            arabicText = "اللَّهُمَّ افْتَحْ لِي أَبْوَابَ رَحْمَتِكَ",
            urduTranslation = "اے اللہ! میرے لیے اپنی رحمت کے دروازے کھول دے۔ (دایاں پاؤں پہلے رکھیں)",
            transliteration = "Allahummaf tah lee abwaba rahmatik",
            reference = "صحیح مسلم، حدیث ۷۱۳",
            recommendedCount = 1
        ),
        Dua(
            id = "dua_leave_masjid",
            titleUrdu = "مسجد سے نکلنے کی دعا",
            category = DuaCategory.MASJID,
            arabicText = "اللَّهُمَّ إِنِّي أَسْأَلُكَ مِنْ فَضْلِكَ",
            urduTranslation = "اے اللہ! میں تجھ سے تیرے فضل کا سوال کرتا ہوں۔ (بایاں پاؤں پہلے نکالیں)",
            transliteration = "Allahumma innee as'aluka min fadlik",
            reference = "صحیح مسلم، حدیث ۷۱۳",
            recommendedCount = 1
        ),

        // Protection
        Dua(
            id = "dua_protection_evil",
            titleUrdu = "ہر بلا اور شر سے حفاظت کی دعا",
            category = DuaCategory.PROTECTION,
            arabicText = "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الْأَرْضِ وَلَا فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ",
            urduTranslation = "اللہ کے نام کے ساتھ جس کے نام کی برکت سے زمین اور آسمان کی کوئی چیز نقصان نہیں پہنچا سکتی، اور وہی سب کچھ سننے والا خوب جاننے والا ہے۔",
            transliteration = "Bismillahil lazee la yadurru ma'as mihee shay'un fil ardi wa la fis samaaa'i wa Huwas Samee'ul 'Aleem",
            reference = "سنن ابی داؤد، حدیث ۵۰۸۸",
            benefitsUrdu = "صبح و شام تین تین بار پڑھنے والے کو کوئی ناگہانی مصیبت یا زہر نقصان نہیں پہنچا سکتا۔",
            recommendedCount = 3
        ),

        // Ramadan & Iftar
        Dua(
            id = "dua_iftar",
            titleUrdu = "افطار کے وقت کی مسنون دعا",
            category = DuaCategory.RAMADAN,
            arabicText = "اللَّهُمَّ إِنِّي لَكَ صُمْتُ وَبِكَ آمَنْتُ وَعَلَىٰ رِزْقِكَ أَفْطَرْتُ",
            urduTranslation = "اے اللہ! میں نے تیرے ہی لیے روزہ رکھا، اور تجھ پر ایمان لایا، اور تیرے ہی دیے ہوئے رزق سے افطار کیا۔",
            transliteration = "Allahumma innee laka sumtu wa bika aamantu wa 'ala rizqika aftartu",
            reference = "سنن ابی داؤد، حدیث ۲۳۵۸",
            recommendedCount = 1
        ),

        // Qadri Awrad
        Dua(
            id = "durood_ghousia",
            titleUrdu = "درودِ غوثیہ شریف (سلسلہ قادریہ)",
            category = DuaCategory.QADRI_AWRAD,
            arabicText = "اللَّهُمَّ صَلِّ عَلَىٰ سَيِّدِنَا وَمَوْلَانَا مُحَمَّدٍ مَّعْدِنِ الْجُودِ وَالْكَرَمِ، وَآلِهِ وَبَارِكْ وَسَلِّمْ",
            urduTranslation = "اے اللہ! درود و سلام اور برکت نازل فرما ہمارے سردار اور مولیٰ حضرت محمد مصطفیٰ ﷺ پر جو جود و سخا اور کرم کی کان ہیں، اور آپ کی پاک آل پر۔",
            transliteration = "Allahumma salli 'ala Sayyidina wa Mawlana Muhammadin ma'dinil joodi wal karam, wa aalihee wa barik wa sallim",
            reference = "اورادِ مشائخِ قادریہ رضویہ",
            recommendedCount = 11
        ),
        Dua(
            id = "dua_shajra_qadria",
            titleUrdu = "یا شیخ عبدالقادر جیلانی شیئاً للہ",
            category = DuaCategory.QADRI_AWRAD,
            arabicText = "يَا سَيِّدِي عَبْدَ الْقَادِرِ جِيلَانِي شَيْئاً لِّلَّهِ، أَلْمَدَدْ يَا غَوْثَ الثَّقَلَيْنِ",
            urduTranslation = "اے میرے سردار شیخ عبدالقادر جیلانی! اللہ کے واسطے ہماری دستگیری فرمائیے، مدد فرمائیے اے جن و انس کے فریاد رس۔ (توسل و استغاثہ صالحین)",
            transliteration = "Ya Sayyidee Abdul Qadir Jilani shay'an lillah...",
            reference = "سلسلہ عالیہ قادریہ برکاتیہ رضویہ",
            recommendedCount = 11
        )
    )

    fun getDuasByCategory(category: DuaCategory): List<Dua> {
        return DUAS_LIST.filter { it.category == category }
    }

    fun searchDuas(query: String): List<Dua> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return DUAS_LIST
        return DUAS_LIST.filter {
            it.titleUrdu.contains(q) ||
            it.arabicText.contains(q) ||
            it.urduTranslation.contains(q) ||
            it.reference.contains(q) ||
            it.category.urduName.contains(q)
        }
    }
}
