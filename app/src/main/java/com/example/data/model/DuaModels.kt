package com.example.data.model

data class Dua(
    val id: String,
    val titleUrdu: String,
    val category: DuaCategory,
    val arabicText: String,
    val urduTranslation: String,
    val transliteration: String,
    val reference: String,
    val benefitsUrdu: String = "",
    val recommendedCount: Int = 1,
    var isFavorite: Boolean = false
)

enum class DuaCategory(val urduName: String, val englishName: String) {
    MORNING_EVENING("صبح و شام کے اذکار", "Morning & Evening"),
    AFTER_SALAH("نماز کے بعد کی دعائیں", "After Salah"),
    SLEEP_WAKE("سونے اور جاگنے کی دعائیں", "Sleep & Waking"),
    DAILY_LIFE("کھانے پینے و روزمرہ", "Daily Life"),
    TRAVEL("سفر اور سواری", "Travel"),
    MASJID("مسجد میں آمد و رفت", "Masjid"),
    PROTECTION("حفاظت اور شفا کی دعائیں", "Protection & Healing"),
    RAMADAN("رمضان اور افطار", "Ramadan & Iftar"),
    ISTIKHARA("استخارہ اور استغفار", "Istikhara & Repentance"),
    QADRI_AWRAD("اوراد و وظائفِ قادریہ", "Qadri Awrad")
}
