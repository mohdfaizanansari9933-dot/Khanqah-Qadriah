package com.example.data.repository

import com.example.data.model.SalamItem
import com.example.data.model.SalamVerse

object SalamRepository {

    val ITEMS = listOf(
        SalamItem(
            id = "salam_mustafa",
            titleUrdu = "مصطفیٰ جانِ رحمت پہ لاکھوں سلام",
            titleEnglish = "Mustafa Jane Rehmat Pe Lakhon Salam",
            poetUrdu = "اعلیٰ حضرت امام احمد رضا خان قادری بریلوی قدس سرہ",
            category = "سلام",
            descriptionUrdu = "اہلِ سنت و جماعت اور خانقاہِ عالیہ قادریہ بدایوں شریف کی ہر مجلس و محفل کا دائمی و روحانی سلام۔",
            verses = listOf(
                SalamVerse(
                    verseNumber = 1,
                    urduText = "مصطفیٰ جانِ رحمت پہ لاکھوں سلام\nشمعِ بزمِ ہدایت پہ لاکھوں سلام",
                    transliteration = "Mustafa Jane Rehmat Pe Lakhon Salam\nShamma-e-Bazme Hidayat Pe Lakhon Salam",
                    englishMeaning = "Millions of salutations upon Mustafa, the embodiment of mercy!\nMillions of salutations upon the luminous lantern of the gathering of guidance!"
                ),
                SalamVerse(
                    verseNumber = 2,
                    urduText = "مہرِ چرخِ نبوت پہ روشن درود\nگلِ باغِ رسالت پہ لاکھوں سلام",
                    transliteration = "Mehre Charkhe Nubuwwat Pe Roshan Durood\nGule Baghe Risalat Pe Lakhon Salam",
                    englishMeaning = "Radiant benedictions upon the sun of the celestial sphere of prophethood!\nMillions of salutations upon the fragrant rose of the garden of messengership!"
                ),
                SalamVerse(
                    verseNumber = 3,
                    urduText = "جس کے پرتو سے پیدا ہوئے دو جہاں\nاس ضیائے حقیقت پہ لاکھوں سلام",
                    transliteration = "Jiske Partaw Se Paida Hue Do Jahan\nUs Ziyaye Haqeeqat Pe Lakhon Salam",
                    englishMeaning = "From whose reflection both universes were manifested!\nMillions of salutations upon that brilliant light of divine truth!"
                ),
                SalamVerse(
                    verseNumber = 4,
                    urduText = "جس کے آگے سبھی سر نگوں ہو گئے\nاس وقارِ امامت پہ لاکھوں سلام",
                    transliteration = "Jiske Aage Sabhi Sar Nigoan Ho Gaye\nUs Waqar-e-Imamat Pe Lakhon Salam",
                    englishMeaning = "Before whose grandeur all heads bow down!\nMillions of salutations upon that supreme dignity of cosmic leadership!"
                ),
                SalamVerse(
                    verseNumber = 5,
                    urduText = "غوثِ اعظم امام التقی والنقی\nجلوۂ شانِ قدرت پہ لاکھوں سلام",
                    transliteration = "Ghaus-e-Azam Imam-ut-Tuqa Wan-Naqi\nJalwa-e-Shaane Qudrat Pe Lakhon Salam",
                    englishMeaning = "Ghaus-e-Azam, the leader of the pious and pure!\nMillions of salutations upon that manifestation of Divine glory!"
                ),
                SalamVerse(
                    verseNumber = 6,
                    urduText = "کاش محشر میں جب ان کی آمد ہو اور\nبھیجیں سب ان کی شوکت پہ لاکھوں سلام",
                    transliteration = "Kaash Mehshar Mein Jab Unki Aamad Ho Aur\nBhejein Sab Unki Shaukat Pe Lakhon Salam",
                    englishMeaning = "May that blessed moment arrive in the Gathering of Judgment when He arrives,\nAnd everyone sends millions of salutations upon His sovereign majesty!"
                )
            )
        ),
        SalamItem(
            id = "qasidah_ghausia",
            titleUrdu = "قصیدہ غوثیہ شریف (کلامِ غوثِ اعظم ؓ)",
            titleEnglish = "Qasidah Ghausia Shareef",
            poetUrdu = "سلطان الاولیاء حضور غوثِ پاک شیخ عبدالقادر جیلانی رضی اللہ عنہ",
            category = "قصیدہ",
            descriptionUrdu = "سرکار غوثِ اعظم رضی اللہ عنہ کا وہ پرتاثیر اور روحانی کلام جس کا ورد دافعِ بلیات اور موجبِ برکات ہے۔",
            verses = listOf(
                SalamVerse(
                    verseNumber = 1,
                    urduText = "سَقَانِي الْحُبُّ كَاسَاتِ الْوِصَالِ\nفَقُلْتُ لِخَمْرَتِي نَحْوِي تَعَالِي",
                    transliteration = "Saqanil Hubbu Kaasat-il-Wisaali\nFaqultu Li-Khamrati Nahwi Ta'aali",
                    englishMeaning = "Love quenched my thirst with cups of divine union;\nSo I said to my mystical beverage, 'Come towards me!'"
                ),
                SalamVerse(
                    verseNumber = 2,
                    urduText = "سَعَتْ وَمَشَتْ لِنَحْوِي فِي كُئُوسٍ\nفَهِمْتُ بِسَكْرَتِي بَيْنَ الْمَوَالِي",
                    transliteration = "Sa'at Wa Mashat Li-Nahwi Fee Ku'oosin\nFahimtu Bi-Sakrati Bainal Mawaali",
                    englishMeaning = "It hurried and flowed towards me in goblets;\nAnd I was immersed in ecstatic devotion among the masters."
                ),
                SalamVerse(
                    verseNumber = 3,
                    urduText = "وَقُلْتُ لِسَائِرِ الْأَقْطَابِ لُمُّوا\nبِحَالِي وَادْخُلُوا أَنْتُمْ رِجَالِي",
                    transliteration = "Wa Qultu Li-Sa'ir-il-Aqtaabi Lummu\nBi-Haali Wad-Khulu Antum Rijaali",
                    englishMeaning = "And I said to all the spiritual poles (Aqtaab), 'Gather around my state,\nAnd enter, for you are my companions!'"
                ),
                SalamVerse(
                    verseNumber = 4,
                    urduText = "فَهُمُّوا وَاشْرَبُوا أَنْتُمْ جُنُودِي\nفَسَاقِي الْقَوْمِ بِالْوَافِي مَلَا لِي",
                    transliteration = "Fahummu Wash-Rabu Antum Junoodi\nFa-Saaqi-al-Qawmi Bil-Waafi Malaa Li",
                    englishMeaning = "Rejoice and drink, for you are my army;\nThe Cupbearer of the Saints has filled my vessel to overflowing!"
                ),
                SalamVerse(
                    verseNumber = 5,
                    urduText = "أَنَا الْبَازِيُّ أَشْهَبُ كُلِّ شَيْخٍ\nوَمَنْ ذَا فِي الرِّجَالِ أُعْطِي مِثَالِي",
                    transliteration = "Anal Baaziyyu Ash-habu Kulli Shaikhin\nWa Man Zaa Fir-Rijaali U'tiya Misaali",
                    englishMeaning = "I am the Royal White Falcon of every Shaikh;\nAnd who among the spiritual masters has been granted my likeness?"
                )
            )
        ),
        SalamItem(
            id = "manqabat_ghausia",
            titleUrdu = "منقبتِ غوثِ اعظم (یا غوثِ اعظم دستگیر)",
            titleEnglish = "Manqabat Ghaus-e-Azam (Imdad Kun)",
            poetUrdu = "منقبتِ عالیہ سلسلہ قادریہ بدایوں شریف",
            category = "منقبت",
            descriptionUrdu = "سلسلہ قادریہ کے مریدین و محبین کا بارگاہِ غوثیت مآب میں نذرانہ عقیدت و استغاثہ۔",
            verses = listOf(
                SalamVerse(
                    verseNumber = 1,
                    urduText = "امداد کن امداد کن، از بندِ غم آزاد کن\nدر دین و دنیا شاد کن، یا غوثِ اعظم دستگیر",
                    transliteration = "Imdad Kun Imdad Kun, Az Bande Gham Azad Kun\nDar Deen o Dunya Shaad Kun, Ya Ghaus-e-Azam Dastagheer",
                    englishMeaning = "Come to my aid, liberate me from the bonds of sorrow!\nMake me joyous in faith and this world, O Ghaus-e-Azam the Helper!"
                ),
                SalamVerse(
                    verseNumber = 2,
                    urduText = "تو شاہِ جیلاں پیرِ من، دریا دل و روشن ضمیر\nما را نگاہِ لطف کن، یا غوثِ اعظم دستگیر",
                    transliteration = "Tu Shahe Jeelan Peere Man, Darya Dil o Roshan Zameer\nMa Ra Nigahe Lutf Kun, Ya Ghaus-e-Azam Dastagheer",
                    englishMeaning = "You are the King of Jilan, my spiritual guide, open-hearted and radiant of conscience;\nBestow a glance of grace upon us, O Ghaus-e-Azam the Helper!"
                ),
                SalamVerse(
                    verseNumber = 3,
                    urduText = "ہر دم بہ لب نامِ تو شد، وردِ زبان کامِ تو شد\nجانم فدائے نامِ تو، یا غوثِ اعظم دستگیر",
                    transliteration = "Har Dam Ba Lab Naame Tu Shud, Wirde Zaban Kaame Tu Shud\nJaanam Fidaye Naame Tu, Ya Ghaus-e-Azam Dastagheer",
                    englishMeaning = "At every breath your name is upon my lips; reciting your praise is my sweetness;\nMy life is sacrificed for your blessed name, O Ghaus-e-Azam!"
                )
            )
        ),
        SalamItem(
            id = "durood_taj",
            titleUrdu = "درودِ تاج شریف",
            titleEnglish = "Durood-e-Taj Shareef",
            poetUrdu = "حضرت امام ابو بکر بن سالم قدس سرہ",
            category = "درود",
            descriptionUrdu = "رسول اللہ ﷺ کی بارگاہ میں صلوٰۃ و سلام کا نہایت جامع، مبارک اور مسنون درود شریف۔",
            verses = listOf(
                SalamVerse(
                    verseNumber = 1,
                    urduText = "اللّٰهُمَّ صَلِّ عَلٰى سَيِّدِنَا وَمَوْلَانَا مُحَمَّدٍ، صَاحِبِ التَّاجِ وَالْمِعْرَاجِ وَالْبُرَاقِ وَالْعَلَمِ",
                    transliteration = "Allahumma Salli Ala Sayyidina Wa Maulana Muhammadin Sahib-it-Taaji Wal-Mi'raaji Wal-Buraaqi Wal-Alam",
                    englishMeaning = "O Allah! Send blessings upon our Master and Sovereign Muhammad, owner of the Crown, the Ascent, the Buraq, and the Flag!"
                ),
                SalamVerse(
                    verseNumber = 2,
                    urduText = "دَافِعِ الْبَلَاءِ وَالْوَبَاءِ وَالْقَحْطِ وَالْمَرَضِ وَالْأَلَمِ، اِسْمُهٗ مَكْتُوبٌ مَّرْفُوعٌ مَّشْفُوعٌ مَّنْقُوشٌ فِي اللَّوْحِ وَالْقَلَمِ",
                    transliteration = "Dafi'il Balaa'i Wal-Wabaa'i Wal-Qahti Wal-Maradi Wal-Alam, Ismuhu Maktoobun Marfoo'un Mashfoo'un Manqooshun Fil-Lawhi Wal-Qalam",
                    englishMeaning = "The repeller of affliction, pestilence, famine, disease, and pain; whose name is inscribed, elevated, interceding, engraved upon the Tablet and the Pen!"
                ),
                SalamVerse(
                    verseNumber = 3,
                    urduText = "سَيِّدِ الْعَرَبِ وَالْعَجَمِ، جِسْمُهٗ مُقَدَّسٌ مُّعَطَّرٌ مُّطَهَّرٌ مُّنَوَّرٌ فِي الْبَيْتِ وَالْحَرَمِ",
                    transliteration = "Sayyid-il-Arabi Wal-Ajam, Jismuhu Muqaddasun Mu'attarun Mutahharun Munawwarun Fil-Baiti Wal-Haram",
                    englishMeaning = "The leader of the Arabs and non-Arabs; whose sacred body is holy, fragrant, cleansed, and illuminated in the Sanctuary and Holy Precincts!"
                )
            )
        )
    )
}
