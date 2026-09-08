package com.example

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.data.model.AppLanguage
import com.example.data.model.CalculationMethod
import com.example.data.model.LocationInfo
import com.example.data.model.ScholarProfile
import com.example.data.prayer.PrayerCalculationEngine
import com.example.data.repository.AppPreferencesRepository
import com.example.data.repository.IslamicCalendarRepository
import com.example.data.repository.KhanqahRepository
import com.example.data.repository.KnowledgeRepository
import com.example.data.repository.UserProfileRepository
import com.example.ui.components.AuthModal
import com.example.ui.components.IslamicTopAppBar
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.util.AzanAlarmManager
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                KhanqahQadriahApp()
            }
        }
    }
}

enum class NavigationTab(val route: String, val urduTitle: String, val hindiTitle: String, val englishTitle: String, val icon: ImageVector) {
    HOME("home", "ہوم", "होम", "Home", Icons.Default.Home),
    CALENDAR("calendar", "۳۰ روزہ کیلنڈر", "कैलेंडर", "Calendar", Icons.Default.CalendarMonth),
    SHAJRA("shajra", "شجرہ شریف", "शजरा", "Shajra", Icons.Default.AccountTree),
    SALAM("salam", "سلام و درود", "सलाम", "Salam", Icons.Default.Favorite),
    QURAN("quran", "قرآن پاک", "क़ुरआन", "Quran", Icons.Default.MenuBook),
    MORE("more", "مزید", "और", "More", Icons.Default.Widgets)
}

@SuppressLint("MissingPermission")
@Composable
fun KhanqahQadriahApp() {
    val context = LocalContext.current

    // Splash Screen display on launch
    var showSplash by remember { mutableStateOf(true) }

    // State collections from AppPreferencesRepository
    val currentLanguage by AppPreferencesRepository.currentLanguage.collectAsState()
    val location by AppPreferencesRepository.currentLocation.collectAsState()
    val calculationMethod by AppPreferencesRepository.calculationMethod.collectAsState()
    val isHanafiAsr by AppPreferencesRepository.isHanafiAsr.collectAsState()
    val use24Hour by AppPreferencesRepository.use24HourFormat.collectAsState()
    val notifications by AppPreferencesRepository.notifications.collectAsState()
    val lastReadSurah by AppPreferencesRepository.lastReadSurah.collectAsState()
    val lastReadAyah by AppPreferencesRepository.lastReadAyah.collectAsState()
    val adminBanner by AppPreferencesRepository.adminBanner.collectAsState()
    val selectedAuthorFilter by AppPreferencesRepository.selectedAuthorFilter.collectAsState()

    val isUrdu = currentLanguage == AppLanguage.URDU

    // Navigation Route state
    var currentRoute by remember { mutableStateOf("home") }
    var previousRoute by remember { mutableStateOf("home") }
    var selectedScholar by remember { mutableStateOf<ScholarProfile?>(null) }
    var showAuthModal by remember { mutableStateOf(false) }

    // Live Clock ticker for prayer countdown
    var currentCalendar by remember { mutableStateOf(Calendar.getInstance()) }
    LaunchedEffect(Unit) {
        AzanAlarmManager.initNotificationChannel(context)
        while (true) {
            currentCalendar = Calendar.getInstance()
            delay(1000)
        }
    }

    // Dynamic Calculations
    val hijriDate = remember(currentCalendar) {
        IslamicCalendarRepository.getHijriDate(currentCalendar)
    }

    val prayerTimes = remember(location, calculationMethod, isHanafiAsr, use24Hour, currentCalendar) {
        PrayerCalculationEngine.calculatePrayerTimes(
            calendar = currentCalendar,
            latitude = location.latitude,
            longitude = location.longitude,
            method = calculationMethod,
            isHanafiAsr = isHanafiAsr,
            use24HourFormat = use24Hour
        )
    }

    val nextPrayer = remember(prayerTimes, currentCalendar) {
        PrayerCalculationEngine.getNextPrayer(prayerTimes, currentCalendar.timeInMillis)
    }

    val dailyHadith = remember {
        KnowledgeRepository.DAILY_HADITHS[0]
    }

    // GPS Location Request Launcher
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                      permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                    if (loc != null) {
                        AppPreferencesRepository.updateLocation(
                            LocationInfo(
                                cityName = "موجودہ مقام (GPS)",
                                countryName = "خودکار",
                                latitude = loc.latitude,
                                longitude = loc.longitude
                            )
                        )
                        Toast.makeText(context, "مقام کامیابی سے حاصل کر لیا گیا", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "GPS لوکیشن دستیاب نہیں، فہرست سے شہر منتخب کریں", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (_: SecurityException) {}
        } else {
            Toast.makeText(context, "لوکیشن کی اجازت درکار ہے", Toast.LENGTH_SHORT).show()
        }
    }

    fun requestGps() {
        val fineCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        if (fineCheck == PackageManager.PERMISSION_GRANTED) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                    if (loc != null) {
                        AppPreferencesRepository.updateLocation(
                            LocationInfo(
                                cityName = "موجودہ مقام (GPS)",
                                countryName = "خودکار",
                                latitude = loc.latitude,
                                longitude = loc.longitude
                            )
                        )
                        Toast.makeText(context, "مقام کامیابی سے حاصل کر لیا گیا", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "GPS لوکیشن دستیاب نہیں، فہرست سے شہر منتخب کریں", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (_: SecurityException) {}
        } else {
            locationPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }

    if (showSplash) {
        SplashScreen(
            onSplashFinished = {
                showSplash = false
                requestGps()
            }
        )
    } else {
        // Support RTL Layout for Urdu
        val layoutDirection = if (isUrdu) LayoutDirection.Rtl else LayoutDirection.Ltr

        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("app_root_scaffold"),
                topBar = {
                    // Show custom top bar on primary tabs
                    if (currentRoute in listOf("home", "khanqah_section", "quran", "prayer", "books", "more")) {
                        IslamicTopAppBar(
                            title = "خانقاہ قادریہ",
                            subtitle = "خانقاہِ قادریہ مجیدیہ، بدایوں شریف",
                            currentLanguage = currentLanguage,
                            onSearchClick = {
                                previousRoute = currentRoute
                                currentRoute = "search"
                            },
                            onLanguageToggle = {
                                AppPreferencesRepository.toggleLanguage()
                            },
                            onAdminClick = {
                                previousRoute = currentRoute
                                currentRoute = "admin"
                            },
                            onAboutClick = {
                                previousRoute = currentRoute
                                currentRoute = "about"
                            },
                            onProfileClick = {
                                val authState = UserProfileRepository.authState.value
                                if (authState.isLoggedIn) {
                                    previousRoute = currentRoute
                                    currentRoute = "profile"
                                } else {
                                    showAuthModal = true
                                }
                            },
                            onKhanqahBadgeClick = {
                                previousRoute = currentRoute
                                currentRoute = "khanqah_section"
                            }
                        )
                    }
                },
                bottomBar = {
                    // Bottom navigation bar visible on main tabs
                    if (currentRoute in listOf("home", "calendar", "shajra", "salam", "quran", "more", "khanqah_section", "prayer", "books", "videos")) {
                        NavigationBar(
                            containerColor = CreamSurface,
                            contentColor = EmeraldPrimary,
                            tonalElevation = 8.dp
                        ) {
                            NavigationTab.values().forEach { tab ->
                                val isSelected = currentRoute == tab.route
                                NavigationBarItem(
                                    selected = isSelected,
                                    onClick = {
                                        previousRoute = currentRoute
                                        currentRoute = tab.route
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = tab.icon,
                                            contentDescription = tab.urduTitle,
                                            tint = if (isSelected) EmeraldPrimary else TextMuted
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = when (currentLanguage) {
                                                AppLanguage.URDU -> tab.urduTitle
                                                AppLanguage.HINDI -> tab.hindiTitle
                                                else -> tab.englishTitle
                                            },
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) EmeraldPrimary else TextMuted
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        indicatorColor = EmeraldSoftBg
                                    )
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentRoute) {
                        "home" -> HomeScreen(
                            hijriDate = hijriDate,
                            location = location,
                            nextPrayer = nextPrayer,
                            prayerTimes = prayerTimes,
                            dailyHadith = dailyHadith,
                            adminBanner = adminBanner,
                            currentLanguage = currentLanguage,
                            onNavigateToSection = { targetRoute ->
                                previousRoute = currentRoute
                                currentRoute = targetRoute
                            },
                            onOpenLocationPicker = {
                                previousRoute = currentRoute
                                currentRoute = "prayer"
                            },
                            onSelectScholarById = { scholarId ->
                                val scholar = KhanqahRepository.getScholarById(scholarId)
                                if (scholar != null) {
                                    selectedScholar = scholar
                                    previousRoute = currentRoute
                                    currentRoute = "scholar_detail"
                                }
                            }
                        )
                        "khanqah_section" -> KhanqahSectionScreen(
                            currentLanguage = currentLanguage,
                            onBack = { currentRoute = "home" },
                            onSelectScholar = { scholar ->
                                selectedScholar = scholar
                                previousRoute = currentRoute
                                currentRoute = "scholar_detail"
                            },
                            onOpenBooksByAuthor = { authorId ->
                                AppPreferencesRepository.filterBooksByAuthor(authorId)
                                previousRoute = currentRoute
                                currentRoute = "books"
                            },
                            onOpenVideoGallery = {
                                previousRoute = currentRoute
                                currentRoute = "videos"
                            },
                            onOpenPhotoGallery = {
                                previousRoute = currentRoute
                                currentRoute = "photos"
                            },
                            onOpenContact = {
                                previousRoute = currentRoute
                                currentRoute = "contact"
                            }
                        )
                        "scholar_detail" -> {
                            if (selectedScholar != null) {
                                ScholarDetailScreen(
                                    scholar = selectedScholar!!,
                                    currentLanguage = currentLanguage,
                                    onBack = { currentRoute = previousRoute },
                                    onViewBooksByAuthor = { authorId ->
                                        AppPreferencesRepository.filterBooksByAuthor(authorId)
                                        previousRoute = currentRoute
                                        currentRoute = "books"
                                    }
                                )
                            } else {
                                currentRoute = "khanqah_section"
                            }
                        }
                        "videos" -> VideoGalleryScreen(
                            currentLanguage = currentLanguage,
                            onBack = { currentRoute = previousRoute }
                        )
                        "photos" -> PhotoGalleryScreen(
                            currentLanguage = currentLanguage,
                            onBack = { currentRoute = previousRoute }
                        )
                        "contact" -> ContactScreen(
                            currentLanguage = currentLanguage,
                            onBack = { currentRoute = previousRoute }
                        )
                        "quran" -> QuranScreen(
                            isUrdu = isUrdu,
                            lastReadSurah = lastReadSurah,
                            lastReadAyah = lastReadAyah,
                            onSaveLastRead = { surah, ayah ->
                                AppPreferencesRepository.saveLastRead(surah, ayah)
                            }
                        )
                        "prayer" -> PrayerTimesScreen(
                            location = location,
                            prayerTimes = prayerTimes,
                            nextPrayer = nextPrayer,
                            calculationMethod = calculationMethod,
                            isHanafiAsr = isHanafiAsr,
                            use24Hour = use24Hour,
                            notifications = notifications,
                            isUrdu = isUrdu,
                            onLocationChange = { AppPreferencesRepository.updateLocation(it) },
                            onMethodChange = { AppPreferencesRepository.updateCalculationMethod(it) },
                            onHanafiToggle = { AppPreferencesRepository.setHanafiAsr(it) },
                            on24HourToggle = { AppPreferencesRepository.set24HourFormat(it) },
                            onNotificationChange = { AppPreferencesRepository.updateNotifications(it) },
                            onRequestGpsLocation = { requestGps() }
                        )
                        "books" -> BooksScreen(
                            currentLanguage = currentLanguage,
                            authorFilter = selectedAuthorFilter,
                            onClearAuthorFilter = { AppPreferencesRepository.filterBooksByAuthor(null) }
                        )
                        "more" -> MoreScreen(
                            currentLanguage = currentLanguage,
                            onLanguageSelected = { AppPreferencesRepository.updateLanguage(it) },
                            onNavigate = { targetRoute ->
                                previousRoute = currentRoute
                                currentRoute = targetRoute
                            }
                        )
                        // Sub-screens
                        "qibla" -> QiblaScreen(
                            location = location,
                            isUrdu = isUrdu
                        )
                        "calendar" -> CalendarScreen(
                            currentHijriDate = hijriDate,
                            isUrdu = isUrdu
                        )
                        "shajra" -> ShajraScreen(
                            isUrdu = isUrdu
                        )
                        "salam" -> SalamScreen(
                            isUrdu = isUrdu
                        )
                        "media" -> VideoGalleryScreen(
                            currentLanguage = currentLanguage,
                            onBack = { currentRoute = previousRoute }
                        )
                        "tasbeeh" -> TasbeehScreen(
                            isUrdu = isUrdu
                        )
                        "duas" -> DuasScreen(
                            isUrdu = isUrdu
                        )
                        "knowledge" -> KnowledgeScreen(
                            isUrdu = isUrdu
                        )
                        "search" -> GlobalSearchScreen(
                            onBack = { currentRoute = previousRoute },
                            onNavigate = { target ->
                                previousRoute = currentRoute
                                currentRoute = target
                            },
                            isUrdu = isUrdu
                        )
                        "admin" -> AdminScreen(
                            onBack = { currentRoute = previousRoute },
                            isUrdu = isUrdu
                        )
                        "about" -> AboutScreen(
                            currentLanguage = currentLanguage,
                            onBack = { currentRoute = previousRoute }
                        )
                        "website" -> WebsiteScreen(
                            currentLanguage = currentLanguage,
                            onBack = { currentRoute = previousRoute },
                            onNavigateInApp = { targetRoute ->
                                previousRoute = currentRoute
                                currentRoute = targetRoute
                            }
                        )
                        "settings" -> SettingsScreen(
                            location = location,
                            calculationMethod = calculationMethod,
                            isHanafiAsr = isHanafiAsr,
                            use24Hour = use24Hour,
                            notifications = notifications,
                            isUrdu = isUrdu,
                            onLocationChange = { AppPreferencesRepository.updateLocation(it) },
                            onMethodChange = { AppPreferencesRepository.updateCalculationMethod(it) },
                            onHanafiToggle = { AppPreferencesRepository.setHanafiAsr(it) },
                            on24HourToggle = { AppPreferencesRepository.set24HourFormat(it) },
                            onNotificationChange = { AppPreferencesRepository.updateNotifications(it) },
                            onRequestGpsLocation = { requestGps() },
                            onBack = { currentRoute = previousRoute }
                        )
                        "profile" -> UserProfileScreen(
                            isUrdu = isUrdu,
                            onBack = { currentRoute = previousRoute }
                        )
                        "hadith_poster" -> DailyHadithPosterScreen(
                            isUrdu = isUrdu,
                            onBack = { currentRoute = previousRoute }
                        )
                        "khanqah_ai" -> KhanqahAiScreen(
                            isUrdu = isUrdu,
                            onBack = { currentRoute = previousRoute }
                        )
                        "akabir_badaun" -> AkabirBadaunScreen(
                            isUrdu = isUrdu,
                            onBack = { currentRoute = previousRoute },
                            onOpenBook = { bookId ->
                                AppPreferencesRepository.filterBooksByAuthor(null)
                                previousRoute = currentRoute
                                currentRoute = "books"
                            }
                        )
                    }
                }
            }

            if (showAuthModal) {
                AuthModal(
                    onDismiss = { showAuthModal = false },
                    onSuccess = {
                        showAuthModal = false
                        previousRoute = currentRoute
                        currentRoute = "profile"
                    }
                )
            }
        }
    }
}
