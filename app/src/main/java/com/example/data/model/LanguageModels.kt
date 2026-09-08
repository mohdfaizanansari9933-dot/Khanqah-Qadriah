package com.example.data.model

enum class AppLanguage(
    val code: String,
    val nativeName: String,
    val englishName: String,
    val isRtl: Boolean
) {
    URDU("ur", "اردو", "Urdu", true),
    HINDI("hi", "हिन्दी", "Hindi", false),
    ENGLISH("en", "English", "English", false),
    HINGLISH("hinglish", "Hinglish", "Hinglish (Roman)", false)
}

object AppStrings {
    fun getWelcome(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "خانقاہ قادریہ میں خوش آمدید"
        AppLanguage.HINDI -> "खानकाह क़ादरिया में आपका स्वागत है"
        AppLanguage.ENGLISH -> "Welcome to Khanqah Qadriah"
        AppLanguage.HINGLISH -> "Khanqah Qadriah mein aapka khush aamdeed hai"
    }

    fun getAppName(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "خانقاہ قادریہ بدایوں شریف"
        AppLanguage.HINDI -> "खानकाह क़ादरिया, बदायूं शरीफ़"
        AppLanguage.ENGLISH -> "Khanqah Qadriah Majeediah, Budaun Shareef"
        AppLanguage.HINGLISH -> "Khanqah Qadriah Budaun Shareef"
    }

    fun getSubTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "خانقاہ قادریہ مجیدیہ • بدایوں شریف، یوپی"
        AppLanguage.HINDI -> "खानकाह क़ादरिया मजीदिया • बदायूं शरीफ़, यूपी"
        AppLanguage.ENGLISH -> "Khanqah Qadriah Majeediah • Budaun Shareef, UP"
        AppLanguage.HINGLISH -> "Khanqah Qadriah Majeediah • Budaun Shareef, UP"
    }

    fun getTabHome(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "ہوم"
        AppLanguage.HINDI -> "होम"
        AppLanguage.ENGLISH -> "Home"
        AppLanguage.HINGLISH -> "Home"
    }

    fun getTabQuran(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "قرآن پاک"
        AppLanguage.HINDI -> "क़ुरआन"
        AppLanguage.ENGLISH -> "Quran"
        AppLanguage.HINGLISH -> "Quran"
    }

    fun getTabNamaz(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "نماز"
        AppLanguage.HINDI -> "नमाज़"
        AppLanguage.ENGLISH -> "Prayer"
        AppLanguage.HINGLISH -> "Namaz"
    }

    fun getTabBooks(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "کتب"
        AppLanguage.HINDI -> "किताबें"
        AppLanguage.ENGLISH -> "Books"
        AppLanguage.HINGLISH -> "Kutub"
    }

    fun getTabMore(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "مزید"
        AppLanguage.HINDI -> "और"
        AppLanguage.ENGLISH -> "More"
        AppLanguage.HINGLISH -> "Mazeed"
    }

    fun getPrayerTimesTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "نماز کے اوقات"
        AppLanguage.HINDI -> "नमाज़ का समय"
        AppLanguage.ENGLISH -> "Prayer Times"
        AppLanguage.HINGLISH -> "Namaz ke Auqat"
    }

    fun getQiblaTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "قبلہ رخ"
        AppLanguage.HINDI -> "क़िबला दिशा"
        AppLanguage.ENGLISH -> "Qibla Direction"
        AppLanguage.HINGLISH -> "Qibla Rukh"
    }

    fun getCalendarTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "اسلامی کیلنڈر"
        AppLanguage.HINDI -> "इस्लामी कैलेंडर"
        AppLanguage.ENGLISH -> "Islamic Calendar"
        AppLanguage.HINGLISH -> "Islamic Calendar"
    }

    fun getBooksTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "علم کی کتابیں"
        AppLanguage.HINDI -> "ज्ञान की किताबें"
        AppLanguage.ENGLISH -> "Islamic Books"
        AppLanguage.HINGLISH -> "Ilm ki Kitaben"
    }

    fun getDuasTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "دعائیں و اذکار"
        AppLanguage.HINDI -> "दुआएं और अज़कार"
        AppLanguage.ENGLISH -> "Duas & Azkar"
        AppLanguage.HINGLISH -> "Duayein aur Azkar"
    }

    fun getUlamaTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "علماء و مشائخ"
        AppLanguage.HINDI -> "उलेमा और मशाईख"
        AppLanguage.ENGLISH -> "Ulama & Mashaikh"
        AppLanguage.HINGLISH -> "Ulama aur Mashaikh"
    }

    fun getKhanqahSectionTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "خانقاہ قادریہ بدایوں شریف"
        AppLanguage.HINDI -> "खानकाह क़ादरिया बदायूं शरीफ़"
        AppLanguage.ENGLISH -> "Khanqah Qadriah Budaun"
        AppLanguage.HINGLISH -> "Khanqah Qadriah Budaun"
    }

    fun getYouTubeTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "یوٹیوب چینل و ویڈیوز"
        AppLanguage.HINDI -> "यूट्यूब वीडियो गैलरी"
        AppLanguage.ENGLISH -> "YouTube & Videos"
        AppLanguage.HINGLISH -> "YouTube aur Videos"
    }

    fun getSearchPlaceholder(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "کتاب یا موضوع تلاش کریں..."
        AppLanguage.HINDI -> "किताब या विषय खोजें..."
        AppLanguage.ENGLISH -> "Search books, authors or topic..."
        AppLanguage.HINGLISH -> "Kitab ya mauzu search karein..."
    }

    fun getReadOnline(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "آن لائن پڑھیں"
        AppLanguage.HINDI -> "ऑनलाइन पढ़ें"
        AppLanguage.ENGLISH -> "Read Online"
        AppLanguage.HINGLISH -> "Online Padhein"
    }

    fun getOfficialWebsiteButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.URDU -> "آفیشل ویب سائٹ پر پڑھیں (qadri.in)"
        AppLanguage.HINDI -> "ऑफ़िशियल वेबसाइट पर पढ़ें (qadri.in)"
        AppLanguage.ENGLISH -> "Read on Official Website (qadri.in)"
        AppLanguage.HINGLISH -> "Official Website par Padhein (qadri.in)"
    }
}
