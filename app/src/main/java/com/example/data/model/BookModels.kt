package com.example.data.model

data class BookChapter(
    val id: String,
    val chapterNumber: Int,
    val titleUrdu: String,
    val titleHindi: String = "",
    val titleEnglish: String = "",
    val contentUrdu: String
)

data class Book(
    val id: String,
    val titleUrdu: String,
    val titleHindi: String = "",
    val titleEnglish: String,
    val titleHinglish: String = "",
    val authorUrdu: String,
    val authorHindi: String = "",
    val authorEnglish: String,
    val authorHinglish: String = "",
    val authorId: String = "",
    val category: BookCategory,
    val descriptionUrdu: String,
    val descriptionHindi: String = "",
    val descriptionEnglish: String = "",
    val publicationInfo: String,
    val chapters: List<BookChapter>,
    val totalPagesEstimate: Int = 120,
    val officialWebsiteUrl: String = "https://www.qadri.in/books",
    var isBookmarked: Boolean = false
)

enum class BookCategory(
    val urduName: String,
    val hindiName: String,
    val englishName: String,
    val hinglishName: String
) {
    ALL("تمام کتب", "सभी किताबें", "All Books", "Tamam Kitaben"),
    KHANQAH_QADRIAH("خانقاہ قادریہ کی کتب", "ख़ानक़ाह क़ादरिया की किताबें", "Khanqah Qadriah Books", "Khanqah Qadriah ki Kitaben"),
    AKABIR_BUDAUN("اکابرِ بدایوں کی کتب", "अकाबिर-ए-बदायूं की किताबें", "Elders of Budaun Books", "Akabir-e-Budaun ki Kitaben"),
    AQEEDAH("عقائد و کلام", "अक़ीदा व कलाम", "Aqeedah & Theology", "Aqeedah"),
    FIQH("فقہ و مسائل", "फ़िक़्ह व मसाइल", "Fiqh & Jurisprudence", "Fiqh"),
    HADITH("حدیث و سنت", "हदीस व सुन्नत", "Hadith", "Hadith"),
    TAFSEER("تفسیر و قرآن", "तफ़्सीर व क़ुरआन", "Tafseer", "Tafseer"),
    SEERAT("سیرت النبی ﷺ", "सीरत-उन-नबी ﷺ", "Seerat-un-Nabi ﷺ", "Seerat"),
    TASAWWUF("تصوف و طریقت", "तसव्वुफ़ व तरीक़त", "Tasawwuf & Spirituality", "Tasawwuf"),
    FAZAIL("فضائلِ اعمال", "फ़ज़ाइल-ए-आमाल", "Virtues of Deeds", "Fazail"),
    ISLAMIC_HISTORY("اسلامی تاریخ و سوانح", "इस्लामी इतिहास", "Islamic History", "Tareekh"),
    NAAT_MANAQIB("نعت و مناقب", "नात व मनाक़िब", "Naat & Manaqib", "Naat & Manaqib"),
    MILAD("میلاد النبی ﷺ", "मीलाद-उन-नबी ﷺ", "Milad-un-Nabi ﷺ", "Milad"),
    ISLAHI("اصلاحی کتب", "इस्लाही किताबें", "Spiritual Reform", "Islahi Kitaben"),
    ARTICLES("علمی مضامین", "इल्मी मक़ालात", "Scholarly Articles", "Ilmi Mazameen")
}
