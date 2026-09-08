package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.CalculationMethod
import com.example.data.model.LocationInfo
import com.example.data.model.PrayerNotificationSettings
import com.example.data.repository.AppPreferencesRepository
import com.example.data.repository.KhanqahRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    location: LocationInfo,
    calculationMethod: CalculationMethod,
    isHanafiAsr: Boolean,
    use24Hour: Boolean,
    notifications: PrayerNotificationSettings,
    isUrdu: Boolean = true,
    onLocationChange: (LocationInfo) -> Unit,
    onMethodChange: (CalculationMethod) -> Unit,
    onHanafiToggle: (Boolean) -> Unit,
    on24HourToggle: (Boolean) -> Unit,
    onNotificationChange: (PrayerNotificationSettings) -> Unit,
    onRequestGpsLocation: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var showCityPicker by remember { mutableStateOf(false) }
    var showMethodPicker by remember { mutableStateOf(false) }
    val currentLanguage by AppPreferencesRepository.currentLanguage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("settings_screen_container")
    ) {
        TopAppBar(
            title = {
                Text(
                    text = if (isUrdu) "سیٹنگز و ترجیحات" else "Settings & Preferences",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = EmeraldDark)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Language Selection
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Translate, contentDescription = null, tint = EmeraldPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isUrdu) "ایپ کی زبان" else "Application Language",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AppLanguage.values().forEach { lang ->
                                FilterChip(
                                    selected = currentLanguage == lang,
                                    onClick = { AppPreferencesRepository.updateLanguage(lang) },
                                    label = { Text(lang.nativeName, fontSize = 12.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = EmeraldPrimary,
                                        selectedLabelColor = Color.White
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            // Location Settings
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = EmeraldPrimary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isUrdu) "شہر و مقام" else "Location & City",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark
                                )
                            }
                            TextButton(onClick = { showCityPicker = true }) {
                                Text(if (isUrdu) "تبدیل کریں" else "Change", color = EmeraldPrimary, fontWeight = FontWeight.Bold)
                            }
                        }

                        Text(
                            text = "${location.cityName}، ${location.countryName}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextCharcoal
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedButton(
                            onClick = onRequestGpsLocation,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.MyLocation, contentDescription = null, tint = EmeraldPrimary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isUrdu) "GPS کے ذریعے خودکار مقام حاصل کریں" else "Auto-detect via GPS", color = EmeraldPrimary)
                        }
                    }
                }
            }

            // Calculation Method & Juristic (Hanafi)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (isUrdu) "نماز کے حساب کا طریقہ" else "Calculation Method",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showMethodPicker = true }
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (isUrdu) calculationMethod.urduTitle else calculationMethod.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextCharcoal
                                )
                                Text(
                                    text = if (isUrdu) "طریقہ تبدیل کرنے کے لیے ٹیپ کریں" else "Tap to change method",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                        }

                        Divider(modifier = Modifier.padding(vertical = 10.dp), color = GoldLight.copy(alpha = 0.4f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (isUrdu) "حنفی عصر (مثلین)" else "Hanafi Asr (Double Shadow)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextCharcoal
                                )
                                Text(
                                    text = if (isUrdu) "اہل سنت و احناف کے نزدیک عصر مثلین کے بعد" else "Standard for Ahle Sunnat Wal Jamaat",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                            Switch(
                                checked = isHanafiAsr,
                                onCheckedChange = onHanafiToggle,
                                colors = SwitchDefaults.colors(checkedThumbColor = EmeraldPrimary, checkedTrackColor = EmeraldSoftBg)
                            )
                        }

                        Divider(modifier = Modifier.padding(vertical = 10.dp), color = GoldLight.copy(alpha = 0.4f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (isUrdu) "24 گھنٹے کا فارمیٹ" else "24-Hour Format",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextCharcoal
                                )
                            }
                            Switch(
                                checked = use24Hour,
                                onCheckedChange = on24HourToggle,
                                colors = SwitchDefaults.colors(checkedThumbColor = EmeraldPrimary, checkedTrackColor = EmeraldSoftBg)
                            )
                        }
                    }
                }
            }

            // Notifications
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            KhanqahOfficialLogo(
                                size = 36.dp,
                                elevation = 2.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (isUrdu) "اذان و نماز کے نوٹیفکیشن" else "Adhan & Prayer Notifications",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark
                                )
                                Text(
                                    text = if (isUrdu) "خانقاہِ قادریہ بدایوں شریف کی طرف سے اوقات و یاد دہانی" else "Khanqah Qadriah Daily Prayer & Adhan Alerts",
                                    fontSize = 11.sp,
                                    color = TextCharcoal
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        val prayerItems = listOf(
                            Pair(if (isUrdu) "فجر" else "Fajr", notifications.fajr),
                            Pair(if (isUrdu) "ظہر" else "Dhuhr", notifications.dhuhr),
                            Pair(if (isUrdu) "عصر" else "Asr", notifications.asr),
                            Pair(if (isUrdu) "مغرب" else "Maghrib", notifications.maghrib),
                            Pair(if (isUrdu) "عشاء" else "Isha", notifications.isha)
                        )

                        prayerItems.forEachIndexed { idx, item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = item.first, fontSize = 13.sp, color = TextCharcoal)
                                Switch(
                                    checked = item.second,
                                    onCheckedChange = { isChecked ->
                                        val updated = when (idx) {
                                            0 -> notifications.copy(fajr = isChecked)
                                            1 -> notifications.copy(dhuhr = isChecked)
                                            2 -> notifications.copy(asr = isChecked)
                                            3 -> notifications.copy(maghrib = isChecked)
                                            else -> notifications.copy(isha = isChecked)
                                        }
                                        onNotificationChange(updated)
                                    },
                                    colors = SwitchDefaults.colors(checkedThumbColor = EmeraldPrimary, checkedTrackColor = EmeraldSoftBg)
                                )
                            }
                        }
                    }
                }
            }

            // Official Website Link
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_WEBSITE_URL))
                            context.startActivity(intent)
                        },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = EmeraldDark)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Official Website: www.qadri.in", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(text = "خانقاہِ قادریہ مجیدیہ بدایوں شریف", fontSize = 10.sp, color = GoldLight)
                        }
                        Icon(Icons.Default.OpenInNew, contentDescription = null, tint = GoldLight)
                    }
                }
            }
        }
    }

    // City Selection Dialog
    if (showCityPicker) {
        AlertDialog(
            onDismissRequest = { showCityPicker = false },
            title = { Text(if (isUrdu) "شہر منتخب کریں" else "Select City", fontWeight = FontWeight.Bold, color = EmeraldDark) },
            text = {
                LazyColumn(modifier = Modifier.heightIn(max = 350.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(AppPreferencesRepository.PREDEFINED_CITIES) { city ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onLocationChange(city)
                                    showCityPicker = false
                                    Toast.makeText(context, "${city.cityName} منتخب کر لیا گیا", Toast.LENGTH_SHORT).show()
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (city.cityName == location.cityName) EmeraldSoftBg else CreamCard
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = city.cityName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextCharcoal)
                                Text(text = city.countryName, fontSize = 12.sp, color = TextMuted)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCityPicker = false }) {
                    Text(if (isUrdu) "بند کریں" else "Close", color = EmeraldPrimary)
                }
            }
        )
    }

    // Calculation Method Dialog
    if (showMethodPicker) {
        AlertDialog(
            onDismissRequest = { showMethodPicker = false },
            title = { Text(if (isUrdu) "حساب کا طریقہ" else "Calculation Method", fontWeight = FontWeight.Bold, color = EmeraldDark) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    CalculationMethod.values().forEach { method ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onMethodChange(method)
                                    showMethodPicker = false
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (method == calculationMethod) EmeraldSoftBg else CreamCard
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(text = if (isUrdu) method.urduTitle else method.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextCharcoal)
                                Text(text = "Fajr: ${method.fajrAngle}°, Isha: ${method.ishaAngle}°", fontSize = 11.sp, color = TextMuted)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showMethodPicker = false }) {
                    Text(if (isUrdu) "بند کریں" else "Close", color = EmeraldPrimary)
                }
            }
        )
    }
}
