package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.data.repository.AppPreferencesRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*

@Composable
fun PrayerTimesScreen(
    location: LocationInfo,
    prayerTimes: PrayerTimes,
    nextPrayer: NextPrayerInfo,
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
    onRequestGpsLocation: () -> Unit
) {
    var showCityDialog by remember { mutableStateOf(false) }
    var showMethodDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("prayer_times_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 0. Official Khanqah Branding Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = EmeraldDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            androidx.compose.ui.graphics.Brush.horizontalGradient(
                                listOf(EmeraldDark, EmeraldPrimary, EmeraldDark)
                            )
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        KhanqahOfficialLogo(
                            size = 52.dp,
                            circular = true,
                            elevation = 2.dp
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "خانقاہِ قادریہ بدایوں شریف",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldLight
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (isUrdu) "دائمی اوقاتِ نماز و سحر و افطار • اہل سنت و جماعت" else "Perpetual Prayer & Ramadan Timetable",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }
        }

        // 1. Location and Auto GPS Header Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isUrdu) "موجودہ مقام" else "Current Location",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                        Text(
                            text = "${location.cityName}, ${location.countryName}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Text(
                            text = "عرض بلد: ${String.format("%.2f", location.latitude)}° | طول بلد: ${String.format("%.2f", location.longitude)}°",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }

                    Row {
                        IconButton(
                            onClick = onRequestGpsLocation,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(EmeraldSoftBg)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MyLocation,
                                contentDescription = "GPS",
                                tint = EmeraldPrimary
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = { showCityDialog = true },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(CreamCard)
                        ) {
                            Icon(
                                imageVector = Icons.Default.EditLocationAlt,
                                contentDescription = "Select City",
                                tint = GoldDark
                            )
                        }
                    }
                }
            }
        }

        // 2. Next Prayer Countdown Notice
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = EmeraldPrimary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isUrdu) "اگلی نماز" else "Next Prayer",
                            fontSize = 12.sp,
                            color = GoldLight
                        )
                        Text(
                            text = if (isUrdu) nextPrayer.prayer.urduName else nextPrayer.prayer.englishName,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = nextPrayer.timeFormatted,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                        Text(
                            text = if (isUrdu) "اہل سنت حنفی اوقات" else "Hanafi Timings",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        // 3. Complete Prayer Schedule Table
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(
                        text = if (isUrdu) "آج کے مسنون اوقاتِ نماز" else "Today's Prayer Schedule",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    PrayerRowItem(
                        urduName = "سحری کا اختتام",
                        englishName = "Sehri Ends",
                        time = prayerTimes.sehri,
                        icon = Icons.Default.Nightlight,
                        isNotify = notifications.sehri,
                        onToggleNotify = { onNotificationChange(notifications.copy(sehri = !notifications.sehri)) },
                        isHighlighted = false
                    )
                    HorizontalDivider(color = Color(0xFFF0EBE0))

                    PrayerRowItem(
                        urduName = "فجر",
                        englishName = "Fajr",
                        time = prayerTimes.fajr,
                        icon = Icons.Default.WbTwilight,
                        isNotify = notifications.fajr,
                        onToggleNotify = { onNotificationChange(notifications.copy(fajr = !notifications.fajr)) },
                        isHighlighted = nextPrayer.prayer == PrayerName.FAJR
                    )
                    HorizontalDivider(color = Color(0xFFF0EBE0))

                    PrayerRowItem(
                        urduName = "طلوعِ آفتاب",
                        englishName = "Sunrise",
                        time = prayerTimes.sunrise,
                        icon = Icons.Default.WbSunny,
                        isNotify = notifications.sunrise,
                        onToggleNotify = { onNotificationChange(notifications.copy(sunrise = !notifications.sunrise)) },
                        isHighlighted = nextPrayer.prayer == PrayerName.SUNRISE
                    )
                    HorizontalDivider(color = Color(0xFFF0EBE0))

                    PrayerRowItem(
                        urduName = "ظہر",
                        englishName = "Dhuhr",
                        time = prayerTimes.dhuhr,
                        icon = Icons.Default.Brightness7,
                        isNotify = notifications.dhuhr,
                        onToggleNotify = { onNotificationChange(notifications.copy(dhuhr = !notifications.dhuhr)) },
                        isHighlighted = nextPrayer.prayer == PrayerName.DHUHR
                    )
                    HorizontalDivider(color = Color(0xFFF0EBE0))

                    PrayerRowItem(
                        urduName = "عصر (حنفی - مثلین)",
                        englishName = "Asr (Hanafi)",
                        time = prayerTimes.asr,
                        icon = Icons.Default.Brightness6,
                        isNotify = notifications.asr,
                        onToggleNotify = { onNotificationChange(notifications.copy(asr = !notifications.asr)) },
                        isHighlighted = nextPrayer.prayer == PrayerName.ASR
                    )
                    HorizontalDivider(color = Color(0xFFF0EBE0))

                    PrayerRowItem(
                        urduName = "مغرب و افطار",
                        englishName = "Maghrib & Iftar",
                        time = prayerTimes.maghrib,
                        icon = Icons.Default.Brightness4,
                        isNotify = notifications.maghrib,
                        onToggleNotify = { onNotificationChange(notifications.copy(maghrib = !notifications.maghrib)) },
                        isHighlighted = nextPrayer.prayer == PrayerName.MAGHRIB
                    )
                    HorizontalDivider(color = Color(0xFFF0EBE0))

                    PrayerRowItem(
                        urduName = "عشاء",
                        englishName = "Isha",
                        time = prayerTimes.isha,
                        icon = Icons.Default.Bedtime,
                        isNotify = notifications.isha,
                        onToggleNotify = { onNotificationChange(notifications.copy(isha = !notifications.isha)) },
                        isHighlighted = nextPrayer.prayer == PrayerName.ISHA
                    )
                }
            }
        }

        // 4. Calculation Settings & Fiqh Controls
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(
                        text = if (isUrdu) "شرعی و حسابی ترتیبات" else "Calculation & Fiqh Settings",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Calculation Method
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showMethodDialog = true }
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isUrdu) "طریقۂ حساب (Method)" else "Calculation Method",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextCharcoal
                            )
                            Text(
                                text = if (isUrdu) calculationMethod.urduTitle else calculationMethod.title,
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Select",
                            tint = TextMuted
                        )
                    }

                    HorizontalDivider(color = Color(0xFFF0EBE0))

                    // Hanafi Asr Switch
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isUrdu) "عصر کی حنفی نماز (سایہ دو مثل)" else "Hanafi Asr (Shadow 2x)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextCharcoal
                            )
                            Text(
                                text = if (isUrdu) "اہل سنت حنفی مسلک کے مطابق سایہ دو مثل پر عصر شروع ہوتی ہے۔" else "Standard Hanafi jurisprudence requirement",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                        Switch(
                            checked = isHanafiAsr,
                            onCheckedChange = onHanafiToggle,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = EmeraldPrimary
                            )
                        )
                    }

                    HorizontalDivider(color = Color(0xFFF0EBE0))

                    // 12-Hour vs 24-Hour Format
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isUrdu) "وقت کا فارمیٹ (24 گھنٹے)" else "24-Hour Time Format",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextCharcoal
                            )
                            Text(
                                text = if (use24Hour) "24-Hour" else "12-Hour (AM / PM)",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                        Switch(
                            checked = use24Hour,
                            onCheckedChange = on24HourToggle,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = EmeraldPrimary
                            )
                        )
                    }
                }
            }
        }
    }

    // City Selection Dialog
    if (showCityDialog) {
        AlertDialog(
            onDismissRequest = { showCityDialog = false },
            title = {
                Text(
                    text = if (isUrdu) "شہر یا مقام منتخب کریں" else "Select City / Location",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )
            },
            text = {
                LazyColumn(modifier = Modifier.heightIn(max = 350.dp)) {
                    items(AppPreferencesRepository.PREDEFINED_CITIES.size) { index ->
                        val city = AppPreferencesRepository.PREDEFINED_CITIES[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onLocationChange(city)
                                    showCityDialog = false
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationCity,
                                contentDescription = city.cityName,
                                tint = EmeraldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = city.cityName,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextCharcoal
                                )
                                Text(
                                    text = city.countryName,
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                        }
                        if (index < AppPreferencesRepository.PREDEFINED_CITIES.size - 1) {
                            HorizontalDivider(color = Color(0xFFEEEEEE))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCityDialog = false }) {
                    Text(if (isUrdu) "بند کریں" else "Close", color = EmeraldPrimary)
                }
            }
        )
    }

    // Calculation Method Dialog
    if (showMethodDialog) {
        AlertDialog(
            onDismissRequest = { showMethodDialog = false },
            title = {
                Text(
                    text = if (isUrdu) "حسابی طریقہ منتخب کریں" else "Select Calculation Method",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )
            },
            text = {
                Column {
                    CalculationMethod.values().forEach { method ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onMethodChange(method)
                                    showMethodDialog = false
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = calculationMethod == method,
                                onClick = {
                                    onMethodChange(method)
                                    showMethodDialog = false
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = EmeraldPrimary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = if (isUrdu) method.urduTitle else method.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextCharcoal
                                )
                                Text(
                                    text = "فجر زاویہ: ${method.fajrAngle}° | عشاء زاویہ: ${method.ishaAngle}°",
                                    fontSize = 10.sp,
                                    color = TextMuted
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showMethodDialog = false }) {
                    Text(if (isUrdu) "بند کریں" else "Close", color = EmeraldPrimary)
                }
            }
        )
    }
}

@Composable
fun PrayerRowItem(
    urduName: String,
    englishName: String,
    time: String,
    icon: ImageVector,
    isNotify: Boolean,
    onToggleNotify: () -> Unit,
    isHighlighted: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (isHighlighted) EmeraldSoftBg else Color.Transparent)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(if (isHighlighted) EmeraldPrimary else CreamCard),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = urduName,
                    tint = if (isHighlighted) Color.White else EmeraldDark,
                    modifier = Modifier.size(17.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = urduName,
                    fontSize = 14.sp,
                    fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.SemiBold,
                    color = if (isHighlighted) EmeraldPrimary else TextCharcoal
                )
                Text(
                    text = englishName,
                    fontSize = 10.sp,
                    color = TextMuted
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = time,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (isHighlighted) EmeraldPrimary else TextCharcoal
            )

            Spacer(modifier = Modifier.width(10.dp))

            IconButton(
                onClick = onToggleNotify,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = if (isNotify) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff,
                    contentDescription = "Notification",
                    tint = if (isNotify) GoldDark else TextMuted.copy(alpha = 0.5f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
