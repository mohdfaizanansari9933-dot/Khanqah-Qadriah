package com.example.data.model

data class ScholarProfile(
    val id: String,
    val nameUrdu: String,
    val nameHindi: String,
    val nameEnglish: String,
    val nameHinglish: String,
    val titleUrdu: String,
    val titleHindi: String,
    val titleEnglish: String,
    val biographyUrdu: String,
    val biographyHindi: String,
    val biographyEnglish: String,
    val biographyHinglish: String,
    val eraOrDates: String,
    val roleUrdu: String,
    val roleEnglish: String,
    val spiritualLineage: String,
    val scholarlyServicesUrdu: String,
    val scholarlyServicesEnglish: String,
    val booksAuthored: List<String>,
    val relatedVideoTitles: List<String> = emptyList(),
    val references: List<String> = listOf("تذکرۂ خانوادۂ قادریہ بدایوں شریف", "سجلِ مشائخِ قادریہ بدایوں"),
    val isVerified: Boolean = true
)

typealias Scholar = ScholarProfile

val ScholarProfile.birthDeathUrdu: String get() = eraOrDates
val ScholarProfile.booksWritten: List<String> get() = booksAuthored

data class HuzoorAteefMiyaSpeech(
    val id: String,
    val titleUrdu: String,
    val titleHindi: String,
    val titleEnglish: String,
    val occasionUrdu: String,
    val occasionEnglish: String,
    val duration: String,
    val youtubeId: String,
    val youtubeUrl: String,
    val summaryUrdu: String,
    val speakerUrdu: String = "جانشین حضور تاجدارِ اہلِ سنت حضرت صوفی محمد عاطف میاں قادری مدظلہ العالی (سجادہ نشین خانقاہِ عالیہ قادریہ)",
    val speakerHindi: String = "हज़रत सूफ़ी मोहम्मद आतिफ़ मियां क़ादरी (सज्जादा नशीन)",
    val speakerEnglish: String = "Hazrat Sufi Mohammad Ateef Miya Qadri (Sajjadah Nashin)",
    val isFeatured: Boolean = false,
    val viewsCount: String = "Haq Multimedia Official"
)

data class KhanqahVideoItem(
    val id: String,
    val titleUrdu: String,
    val titleHindi: String,
    val titleEnglish: String,
    val speakerUrdu: String,
    val speakerEnglish: String,
    val duration: String,
    val category: VideoCategory,
    val youtubeId: String,
    val youtubeUrl: String,
    val isVerified: Boolean = true
)

enum class VideoCategory(val urduName: String, val hindiName: String, val englishName: String) {
    HUZOOR_ATEEF_MIYA("خطابات حضور عاطف میاں", "ख़िताबत हुज़ूर आतिफ़ मियां", "Huzoor Ateef Miya Speeches"),
    LATEST("تازہ ترین ویڈیوز", "ताज़ा तरीन वीडियो", "Latest Videos"),
    BAYAANS("روحانی بیانات", "आध्यात्मिक बयान", "Spiritual Bayaans"),
    ISLAMIC_EVENTS("اسلامی تقاریب", "इस्लामी कार्यक्रम", "Islamic Events"),
    BUDAUN_SHAREEF("بدایوں شریف دستاویزی", "बदायूं शरीफ़ विरासत", "Budaun Shareef"),
    SPECIAL_PROGRAMS("خصوصی نشریات", "विशेष प्रसारण", "Special Programs"),
    URS_QADRI("عرسِ قادری مبارک", "उर्स-ए-क़ादरी मुबारक", "Urs-e-Qadri Mubarak"),
    LECTURES("دینی و فکری خطابات", "दीनी व फ़िक्री ख़िताब", "Islamic Lectures"),
    ULAMA_SPEECHES("بیاناتِ علمائے بدایوں", "उलेमा के बयानात", "Ulama Speeches"),
    KHANQAH_PROGRAMS("پروگرامز خانقاہ قادریہ", "ख़ानक़ाह प्रोग्राम्स", "Khanqah Programs"),
    CONFERENCES("سیرت و تاریخی کانفرنسز", "कॉन्फ्रेंस व सेमीनार", "Conferences"),
    MEHFIL_SAMA("محفلِ میلاد و مناقب", "महफ़िल-ए-मीलाद व मनाक़िब", "Mehfil-e-Milad & Manaqib"),
    SEERAT("سیرت النبی ﷺ", "सीरत-उन-नबी ﷺ", "Seerat-un-Nabi ﷺ"),
    HISTORICAL("تاریخی ویڈیوز بدایوں", "ऐतिहासिक वीडियो", "Historical Videos")
}

data class PhotoGalleryItem(
    val id: String,
    val titleUrdu: String,
    val titleHindi: String,
    val titleEnglish: String,
    val categoryUrdu: String,
    val categoryEnglish: String,
    val descriptionUrdu: String,
    val descriptionEnglish: String,
    val sourceCredit: String = "آفیشل آرکائیو خانقاہ قادریہ بدایوں شریف",
    val isVerified: Boolean = true
)

data class TazkiraMilestone(
    val year: String,
    val titleUrdu: String,
    val titleHindi: String,
    val titleEnglish: String,
    val descriptionUrdu: String,
    val descriptionEnglish: String,
    val sourceReference: String = "تذکرۂ خانوادۂ قادریہ"
)

data class DailySpiritualPacket(
    val hadithTitleUrdu: String,
    val hadithArabic: String,
    val hadithTranslationUrdu: String,
    val hadithHindi: String,
    val hadithEnglish: String,
    val hadithReference: String,
    
    val duaTitleUrdu: String,
    val duaArabic: String,
    val duaTranslationUrdu: String,
    val duaHindi: String,
    val duaEnglish: String,
    val duaReference: String,
    
    val eventTitleUrdu: String,
    val eventDescriptionUrdu: String,
    val eventDateOrPeriod: String,
    
    val personalityNameUrdu: String,
    val personalityTitleUrdu: String,
    val personalityBriefUrdu: String,
    
    val bookTitleUrdu: String,
    val bookAuthorUrdu: String,
    val bookBriefUrdu: String,
    val bookCategoryUrdu: String
)
