package com.example.ui.screens

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.HijriDate
import com.example.data.model.IslamicCalendarDay
import com.example.data.model.IslamicEvent
import com.example.data.repository.IslamicCalendarRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*

@Composable
fun CalendarScreen(
    currentHijriDate: HijriDate,
    isUrdu: Boolean = true
) {
    val context = LocalContext.current

    // Moon sighting offset (-1, 0, +1 day)
    var moonOffset by remember { mutableIntStateOf(0) }

    // Month offset (0 = current Hijri month, -1 = previous, +1 = next)
    var monthOffset by remember { mutableIntStateOf(0) }

    // Selected day for details modal
    var selectedDayForModal by remember { mutableStateOf<IslamicCalendarDay?>(null) }

    // Calculate current dynamic Hijri date accounting for moon sighting
    val baseHijri = remember(moonOffset) {
        IslamicCalendarRepository.getHijriDate(java.util.Calendar.getInstance(), moonOffset)
    }

    // Determine target month and year based on monthOffset
    val targetMonthNumber = remember(baseHijri, monthOffset) {
        var m = baseHijri.monthNumber + monthOffset
        while (m <= 0) m += 12
        while (m > 12) m -= 12
        m
    }

    val targetYear = remember(baseHijri, monthOffset) {
        val delta = (baseHijri.monthNumber + monthOffset - 1) / 12
        baseHijri.year + if (baseHijri.monthNumber + monthOffset <= 0) -1 else delta
    }

    val targetMonthUrdu = remember(targetMonthNumber) {
        IslamicCalendarRepository.HIJRI_MONTHS_URDU[targetMonthNumber - 1]
    }

    val targetMonthEnglish = remember(targetMonthNumber) {
        IslamicCalendarRepository.HIJRI_MONTHS_ENGLISH[targetMonthNumber - 1]
    }

    // 30-Day Grid data for the month
    val monthDays = remember(targetMonthNumber, targetYear, moonOffset) {
        IslamicCalendarRepository.get30DayHijriMonthGrid(targetMonthNumber, targetYear, moonOffset)
    }

    // Month events list for quick overview below grid
    val monthEvents = remember(monthDays) {
        monthDays.mapNotNull { it.event }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("calendar_screen_container"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 0. Official Khanqah Calendar Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    KhanqahOfficialLogo(
                        size = 48.dp,
                        circular = true,
                        elevation = 2.dp
                    )

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "خانقاہِ عالیہ قادریہ بدایوں شریف",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (isUrdu) "۳۰ روزہ مکمل قادری ہجری کیلنڈر و اعراسِ اکابرین" else "30-Day Qadri Islamic Calendar & Akabir Urs",
                            fontSize = 12.sp,
                            color = TextCharcoal
                        )
                    }
                }
            }
        }

        // 1. Month Navigation Header Card with Moon Sighting Toggle (+1 / -1 Day)
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = EmeraldPrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(EmeraldDark, EmeraldPrimary, EmeraldDark)
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Month Selector Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { monthOffset-- },
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.15f))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ChevronLeft,
                                    contentDescription = "Previous Month",
                                    tint = GoldLight
                                )
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$targetMonthUrdu $targetYear ھ",
                                    color = GoldLight,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "$targetMonthEnglish $targetYear AH",
                                    color = CreamBg.copy(alpha = 0.9f),
                                    fontSize = 12.sp
                                )
                            }

                            IconButton(
                                onClick = { monthOffset++ },
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.15f))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = "Next Month",
                                    tint = GoldLight
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Moon Sighting Adjustment Bar (+1 / -1 Day Toggle)
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.Black.copy(alpha = 0.25f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.NightsStay,
                                        contentDescription = "Ruyat-e-Hilal",
                                        tint = GoldLight,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isUrdu) "رویتِ ہلال (چاند) ایڈجسٹمنٹ:" else "Moon Sighting:",
                                        fontSize = 11.sp,
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    FilledTonalButton(
                                        onClick = { moonOffset = -1 },
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        colors = ButtonDefaults.filledTonalButtonColors(
                                            containerColor = if (moonOffset == -1) GoldPrimary else Color.White.copy(alpha = 0.15f),
                                            contentColor = if (moonOffset == -1) EmeraldDark else Color.White
                                        ),
                                        modifier = Modifier.height(28.dp)
                                    ) {
                                        Text("-1 دن", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }

                                    FilledTonalButton(
                                        onClick = { moonOffset = 0 },
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        colors = ButtonDefaults.filledTonalButtonColors(
                                            containerColor = if (moonOffset == 0) GoldPrimary else Color.White.copy(alpha = 0.15f),
                                            contentColor = if (moonOffset == 0) EmeraldDark else Color.White
                                        ),
                                        modifier = Modifier.height(28.dp)
                                    ) {
                                        Text("معیاری", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }

                                    FilledTonalButton(
                                        onClick = { moonOffset = 1 },
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        colors = ButtonDefaults.filledTonalButtonColors(
                                            containerColor = if (moonOffset == 1) GoldPrimary else Color.White.copy(alpha = 0.15f),
                                            contentColor = if (moonOffset == 1) EmeraldDark else Color.White
                                        ),
                                        modifier = Modifier.height(28.dp)
                                    ) {
                                        Text("+1 دن", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        if (monthOffset != 0) {
                            Spacer(modifier = Modifier.height(6.dp))
                            TextButton(
                                onClick = { monthOffset = 0 },
                                colors = ButtonDefaults.textButtonColors(contentColor = GoldLight)
                            ) {
                                Text(
                                    text = if (isUrdu) "موجودہ مہینے پر واپس جائیں" else "Back to Current Month",
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. Legend / Badges Guide
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(GoldPrimary)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = if (isUrdu) "گیارہویں شریف" else "Ghyarween",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = GoldDark
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(EmeraldPrimary)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = if (isUrdu) "عرسِ اکابر بدایوں" else "Urs Badaun",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = EmeraldPrimary
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF8E24AA))
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = if (isUrdu) "اسلامی مناسبت" else "Islamic Event",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF8E24AA)
                        )
                    }
                }
            }
        }

        // 3. 30-Day Grid Layout (Pure 30-Day Complete Qadri Islamic Calendar)
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = if (isUrdu) "۳۰ روزہ ہجری و انگریزی تقویم (کلک کر کے تفصیل ملاحظہ فرمائیں)" else "30-Day Hijri & Gregorian Grid (Tap for details)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextMuted,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // 5 Columns / 6 Rows for 30 days grid
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val chunkedDays = monthDays.chunked(5)
                        chunkedDays.forEach { rowDays ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                rowDays.forEach { day ->
                                    Box(modifier = Modifier.weight(1f)) {
                                        CalendarDayGridCell(
                                            day = day,
                                            isUrdu = isUrdu,
                                            onClick = { selectedDayForModal = day }
                                        )
                                    }
                                }
                                // If row has fewer than 5 items, fill empty space
                                for (i in 0 until (5 - rowDays.size)) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
        }

        // 4. Section: Important Urs & Events of the Selected Month
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isUrdu) "اس مبارک مہینے کے اعراس و ایامِ متبرکہ" else "Blessed Urs & Events of this Month",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )
                Text(
                    text = "${monthEvents.size} ایام",
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }
        }

        if (monthEvents.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isUrdu) "اس مہینے میں معمول کی عبادات اور دعاؤں کا اہتمام فرمائیں۔" else "Observe regular prayers and supplications this month.",
                            fontSize = 13.sp,
                            color = TextMuted,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(monthEvents) { event ->
                IslamicEventCard(
                    event = event,
                    isUrdu = isUrdu,
                    onTap = {
                        val matchedDay = monthDays.firstOrNull { it.event == event }
                        selectedDayForModal = matchedDay
                    }
                )
            }
        }
    }

    // Event & Spiritual Details Modal / Dialog
    selectedDayForModal?.let { day ->
        CalendarDayDetailDialog(
            day = day,
            isUrdu = isUrdu,
            onDismiss = { selectedDayForModal = null },
            onShare = {
                val shareText = buildString {
                    appendLine("✦ خانقاہِ عالیہ قادریہ بدایوں شریف ✦")
                    appendLine("تاریخ: ${day.hijriDay} ${day.hijriMonthUrdu} ${day.hijriYear} ھ (${day.gregorianDateFormatted})")
                    if (day.event != null) {
                        appendLine("مناسبت: ${day.event.titleUrdu}")
                        appendLine("تفصیل: ${day.event.descriptionUrdu}")
                        if (day.event.buzurgZikr.isNotEmpty()) {
                            appendLine("ذکرِ جمیل: ${day.event.buzurgZikr}")
                        }
                        if (day.event.fatihaDua.isNotEmpty()) {
                            appendLine("فاتحہ و دعا: ${day.event.fatihaDua}")
                        }
                    }
                }
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(sendIntent, "Share Islamic Date"))
            }
        )
    }
}

/**
 * Clean, legible cell in the 30-day calendar grid.
 * Displays Hijri Date prominently in Urdu and Gregorian Date clearly in English.
 */
@Composable
fun CalendarDayGridCell(
    day: IslamicCalendarDay,
    isUrdu: Boolean,
    onClick: () -> Unit
) {
    val borderColor = when {
        day.isToday -> EmeraldPrimary
        day.isGhyarween -> GoldPrimary
        day.isUrsBadaun -> EmeraldDark
        day.event != null -> Color(0xFF8E24AA)
        else -> Color.Transparent
    }

    val backgroundColor = when {
        day.isToday -> EmeraldSoftBg
        day.isGhyarween -> GoldGlow.copy(alpha = 0.35f)
        day.isUrsBadaun -> EmeraldSoftBg.copy(alpha = 0.5f)
        day.event != null -> Color(0xFFF3E5F5)
        else -> CreamBg
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(
                width = if (day.isToday || day.isGhyarween || day.isUrsBadaun || day.event != null) 1.5.dp else 0.5.dp,
                color = if (borderColor != Color.Transparent) borderColor else Color.Black.copy(alpha = 0.08f),
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (day.isToday || day.isGhyarween) 2.dp else 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Hijri Day (Prominent Urdu numeral)
            Text(
                text = "${day.hijriDay}",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = when {
                    day.isToday -> EmeraldPrimary
                    day.isGhyarween -> GoldDark
                    day.isUrsBadaun -> EmeraldDark
                    else -> TextCharcoal
                }
            )

            // English (Gregorian) Date
            Text(
                text = day.gregorianDateFormatted,
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
                color = TextMuted,
                maxLines = 1
            )

            // Badges / Indicator dot for Events
            Spacer(modifier = Modifier.height(4.dp))
            if (day.isGhyarween) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = GoldPrimary,
                    modifier = Modifier.padding(top = 1.dp)
                ) {
                    Text(
                        text = "۱۱ویں",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }
            } else if (day.isUrsBadaun) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = EmeraldDark,
                    modifier = Modifier.padding(top = 1.dp)
                ) {
                    Text(
                        text = "عرس",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }
            } else if (day.event != null) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF8E24AA))
                )
            } else {
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

/**
 * Card for an individual Islamic event / Urs.
 */
@Composable
fun IslamicEventCard(
    event: IslamicEvent,
    isUrdu: Boolean,
    onTap: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTap() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Event Date Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            when {
                                event.isGhyarween -> GoldGlow
                                event.isUrsBadaun -> EmeraldSoftBg
                                else -> CreamCard
                            }
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "${event.hijriDay} ${event.hijriMonthUrdu}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            event.isGhyarween -> GoldDark
                            event.isUrsBadaun -> EmeraldPrimary
                            else -> TextCharcoal
                        }
                    )
                }

                if (event.isUrsBadaun) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = EmeraldDark
                    ) {
                        Text(
                            text = "اکابرِ بدایوں شریف ✦",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                } else if (event.isGhyarween) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = GoldPrimary
                    ) {
                        Text(
                            text = "سرکار غوثِ اعظم ؓ",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = event.titleUrdu,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = event.descriptionUrdu,
                fontSize = 12.sp,
                color = TextMuted,
                lineHeight = 18.sp
            )
        }
    }
}

/**
 * Details modal when tapping any date on the 30-day calendar.
 * Shows Hijri & Gregorian date, significant event, Buzurg ka Zikr, and recommended Fatiha & Dua!
 */
@Composable
fun CalendarDayDetailDialog(
    day: IslamicCalendarDay,
    isUrdu: Boolean,
    onDismiss: () -> Unit,
    onShare: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CreamSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Icon & Title
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                day.isGhyarween -> GoldGlow
                                day.isUrsBadaun -> EmeraldSoftBg
                                else -> CreamCard
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when {
                            day.isGhyarween -> Icons.Default.AutoAwesome
                            day.isUrsBadaun -> Icons.Default.Mosque
                            else -> Icons.Default.Event
                        },
                        contentDescription = null,
                        tint = when {
                            day.isGhyarween -> GoldDark
                            day.isUrsBadaun -> EmeraldPrimary
                            else -> EmeraldDark
                        },
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "${day.hijriDay} ${day.hijriMonthUrdu} ${day.hijriYear} ھ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )

                Text(
                    text = "${day.dayOfWeekUrdu} • ${day.gregorianDateFormatted} (${day.dayOfWeekEnglish})",
                    fontSize = 12.sp,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color.Black.copy(alpha = 0.08f))
                Spacer(modifier = Modifier.height(14.dp))

                // Event info if present
                if (day.event != null) {
                    Text(
                        text = day.event.titleUrdu,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldDark,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = day.event.descriptionUrdu,
                        fontSize = 12.sp,
                        color = TextCharcoal,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )

                    // Buzurg ka Zikr
                    if (day.event.buzurgZikr.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = CreamBg,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "ذکرِ جمیل و احوالِ بزرگ:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldPrimary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = day.event.buzurgZikr,
                                    fontSize = 11.sp,
                                    color = TextCharcoal,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }

                    // Fatiha / Dua Procedure
                    if (day.event.fatihaDua.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = GoldGlow.copy(alpha = 0.3f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "طریقۂ فاتحہ و ایصالِ ثواب:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldDark
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = day.event.fatihaDua,
                                    fontSize = 11.sp,
                                    color = TextCharcoal,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                } else {
                    // Regular day message
                    Text(
                        text = "مسنون اذکار و دعاؤں کا اہتمام فرمائیں۔",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextCharcoal
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "ہر دن اللہ تعالیٰ کی عظیم نعمت ہے۔ پنج وقتہ نمازوں کی پابندی فرمائیں اور درودِ پاک کا ورد جاری رکھیں۔",
                        fontSize = 12.sp,
                        color = TextMuted,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Action buttons: Share & Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "بند کریں", color = TextCharcoal)
                    }

                    Button(
                        onClick = onShare,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            modifier = Modifier.size(16.dp),
                            tint = GoldLight
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "شیئر کریں", color = Color.White)
                    }
                }
            }
        }
    }
}
