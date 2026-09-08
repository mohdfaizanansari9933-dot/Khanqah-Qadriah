package com.example.data.repository

import com.example.data.model.AppLanguage
import com.example.data.model.CalculationMethod
import com.example.data.model.DhikrItem
import com.example.data.model.LocationInfo
import com.example.data.model.PrayerNotificationSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AppPreferencesRepository {

    val PREDEFINED_CITIES = listOf(
        LocationInfo("بدایوں شریف (Budaun Shareef)", "بھارت", 28.0315, 79.1176),
        LocationInfo("بریلی شریف (Bareilly)", "بھارت", 28.3670, 79.4304),
        LocationInfo("دہلی (Delhi)", "بھارت", 28.6139, 77.2090),
        LocationInfo("لکھنؤ (Lucknow)", "بھارت", 26.8467, 80.9462),
        LocationInfo("ممبئی (Mumbai)", "بھارت", 19.0760, 72.8777),
        LocationInfo("حیدرآباد (Hyderabad)", "بھارت", 17.3850, 78.4867),
        LocationInfo("لاہور (Lahore)", "پاکستان", 31.5204, 74.3587),
        LocationInfo("کراچی (Karachi)", "پاکستان", 24.8607, 67.0011),
        LocationInfo("اسلام آباد / راولپنڈی", "پاکستان", 33.6844, 73.0479),
        LocationInfo("بغداد شریف (Baghdad)", "عراق", 33.3152, 44.3661),
        LocationInfo("مکہ مکرمہ (Makkah)", "سعودی عرب", 21.4225, 39.8262),
        LocationInfo("مدینہ منورہ (Madinah)", "سعودی عرب", 24.4672, 39.6111),
        LocationInfo("لندن (London)", "برطانیہ", 51.5074, -0.1278)
    )

    val PRESET_DHIKRS = listOf(
        DhikrItem("subhanallah", "سُبْحَانَ اللَّهِ", "سبحان اللہ", "اللہ ہر عیب اور نقص سے پاک ہے", 33, "جنت میں درخت لگتا ہے اور گناہ معاف ہوتے ہیں"),
        DhikrItem("alhamdulillah", "الْحَمْدُ لِلَّهِ", "الحمد للہ", "تمام تعریفیں اور شکر اللہ کے لیے ہے", 33, "میزانِ عمل کو نیکیوں سے بھر دیتا ہے"),
        DhikrItem("allahuakbar", "اللَّهُ أَكْبَرُ", "اللہ اکبر", "اللہ سب سے بڑا اور برتر ہے", 34, "زمین و آسمان کے درمیان کی وسعتوں کو بھر دیتا ہے"),
        DhikrItem("durood_pak", "صَلَّى اللَّهُ عَلَيْهِ وَآلِهِ وَسَلَّمَ", "درودِ پاک", "اللہ کی رحمتیں اور سلامتی ہو نبی اکرم ﷺ اور آپ کی آل پر", 100, "ایک بار پڑھنے پر ۱۰ رحمتیں، ۱۰ گناہ معاف اور ۱۰ درجات بلند"),
        DhikrItem("astaghfar", "أَسْتَغْفِرُ اللَّهَ رَبِّي وَأَتُوبُ إِلَيْهِ", "استغفار", "میں اپنے رب اللہ سے معافی مانگتا ہوں اور اس کی طرف رجوع کرتا ہوں", 100, "رزق میں برکت اور پریشانیوں سے نجات کا سبب"),
        DhikrItem("kalima_tayyiba", "لَا إِلٰهَ إِلَّا اللَّهُ مُحَمَّدٌ رَّسُولُ اللَّهِ", "کلمہ طیبہ", "اللہ کے سوا کوئی معبود نہیں، محمد ﷺ اللہ کے رسول ہیں", 100, "افضل ترین ذکر اور ایمان کی بنیاد"),
        DhikrItem("qadri_wazifa", "يَا قَادِرُ يَا قَيُّومُ", "یا قادر یا قیوم", "اے قدرت والے اور قائم رکھنے والے پروردگار", 111, "سلسلہ عالیہ قادریہ کا مجرب ذکر برائے کشائشِ قلب و حاجات")
    )

    // Current State Flows
    private val _currentLanguage = MutableStateFlow(AppLanguage.URDU)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    private val _currentLocation = MutableStateFlow(PREDEFINED_CITIES[0])
    val currentLocation: StateFlow<LocationInfo> = _currentLocation.asStateFlow()

    private val _calculationMethod = MutableStateFlow(CalculationMethod.KARACHI)
    val calculationMethod: StateFlow<CalculationMethod> = _calculationMethod.asStateFlow()

    private val _isHanafiAsr = MutableStateFlow(true)
    val isHanafiAsr: StateFlow<Boolean> = _isHanafiAsr.asStateFlow()

    private val _use24HourFormat = MutableStateFlow(false)
    val use24HourFormat: StateFlow<Boolean> = _use24HourFormat.asStateFlow()

    private val _isUrduPrimary = MutableStateFlow(true)
    val isUrduPrimary: StateFlow<Boolean> = _isUrduPrimary.asStateFlow()

    private val _notifications = MutableStateFlow(PrayerNotificationSettings())
    val notifications: StateFlow<PrayerNotificationSettings> = _notifications.asStateFlow()

    private val _arabicFontSize = MutableStateFlow(28f)
    val arabicFontSize: StateFlow<Float> = _arabicFontSize.asStateFlow()

    private val _urduFontSize = MutableStateFlow(18f)
    val urduFontSize: StateFlow<Float> = _urduFontSize.asStateFlow()

    private val _lastReadSurah = MutableStateFlow(1)
    val lastReadSurah: StateFlow<Int> = _lastReadSurah.asStateFlow()

    private val _lastReadAyah = MutableStateFlow(1)
    val lastReadAyah: StateFlow<Int> = _lastReadAyah.asStateFlow()

    private val _todayTasbeehCount = MutableStateFlow(132)
    val todayTasbeehCount: StateFlow<Int> = _todayTasbeehCount.asStateFlow()

    private val _adminBanner = MutableStateFlow("خوش آمدید! خانقاہ قادریہ مجیدیہ بدایوں شریف کی جانب سے ماہانہ گیارہویں شریف و روحانی مجلس ہر ماہ 11 تاریخ کو منعقد ہوگی۔")
    val adminBanner: StateFlow<String> = _adminBanner.asStateFlow()

    private val _selectedAuthorFilter = MutableStateFlow<String?>(null)
    val selectedAuthorFilter: StateFlow<String?> = _selectedAuthorFilter.asStateFlow()

    fun updateLanguage(language: AppLanguage) {
        _currentLanguage.value = language
        _isUrduPrimary.value = (language == AppLanguage.URDU)
    }

    fun toggleLanguage() {
        val next = when (_currentLanguage.value) {
            AppLanguage.URDU -> AppLanguage.HINDI
            AppLanguage.HINDI -> AppLanguage.ENGLISH
            AppLanguage.ENGLISH -> AppLanguage.HINGLISH
            AppLanguage.HINGLISH -> AppLanguage.URDU
        }
        updateLanguage(next)
    }

    fun updateLocation(location: LocationInfo) {
        _currentLocation.value = location
    }

    fun updateCalculationMethod(method: CalculationMethod) {
        _calculationMethod.value = method
    }

    fun setHanafiAsr(enabled: Boolean) {
        _isHanafiAsr.value = enabled
    }

    fun set24HourFormat(enabled: Boolean) {
        _use24HourFormat.value = enabled
    }

    fun updateNotifications(settings: PrayerNotificationSettings) {
        _notifications.value = settings
    }

    fun updateFontSizes(arabic: Float, urdu: Float) {
        _arabicFontSize.value = arabic
        _urduFontSize.value = urdu
    }

    fun saveLastRead(surahNumber: Int, ayahNumber: Int) {
        _lastReadSurah.value = surahNumber
        _lastReadAyah.value = ayahNumber
    }

    fun incrementTodayTasbeeh() {
        _todayTasbeehCount.value += 1
    }

    fun updateAdminBanner(newBanner: String) {
        _adminBanner.value = newBanner
    }

    fun filterBooksByAuthor(authorId: String?) {
        _selectedAuthorFilter.value = authorId
    }
}
