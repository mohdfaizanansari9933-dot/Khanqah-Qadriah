package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HijriDate
import com.example.data.model.IslamicEvent
import com.example.data.repository.IslamicCalendarRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*
import java.util.Calendar

@Composable
fun CalendarScreen(
    currentHijriDate: HijriDate,
    isUrdu: Boolean = true
) {
    var monthOffset by remember { mutableIntStateOf(0) }
    val displayedHijriDate = remember(monthOffset) {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, monthOffset)
        IslamicCalendarRepository.getHijriDate(cal)
    }

    val events = remember { IslamicCalendarRepository.AHLE_SUNNAT_EVENTS }

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
                            text = "خانقاہِ قادریہ بدایوں شریف",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (isUrdu) "اسلامی ہجری کیلنڈر اور اعراس و ایامِ متبرکہ" else "Islamic Hijri Calendar & Urs Sharif Dates",
                            fontSize = 12.sp,
                            color = TextCharcoal
                        )
                    }
                }
            }
        }

        // 1. Month Navigation Header Card
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
                                    text = "${displayedHijriDate.monthUrdu} ${displayedHijriDate.year} ھ",
                                    color = GoldLight,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = displayedHijriDate.gregorianFormatted,
                                    color = CreamBg.copy(alpha = 0.85f),
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

                        if (monthOffset != 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                            TextButton(
                                onClick = { monthOffset = 0 },
                                colors = ButtonDefaults.textButtonColors(contentColor = GoldLight)
                            ) {
                                Text(
                                    text = if (isUrdu) "آج کے مہینے پر واپس جائیں" else "Back to Current Month",
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. Today Banner Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(GoldGlow),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Event,
                                contentDescription = "Event",
                                tint = GoldDark,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (isUrdu) "آج کی ہجری تاریخ" else "Today's Hijri Date",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                            Text(
                                text = "${currentHijriDate.day} ${currentHijriDate.monthUrdu} ${currentHijriDate.year} ھ",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        }
                    }

                    Text(
                        text = currentHijriDate.dayOfWeekUrdu,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldDark
                    )
                }
            }
        }

        // 3. Section Title: Ahle Sunnat Important Islamic Dates
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isUrdu) "اہم اسلامی و مسنون ایام (اہلِ سنت و جماعت)" else "Important Islamic Dates",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )
                Text(
                    text = "${events.size} ایام",
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }
        }

        // 4. Events List
        items(events) { event ->
            IslamicEventCard(event = event, isUrdu = isUrdu)
        }
    }
}

@Composable
fun IslamicEventCard(
    event: IslamicEvent,
    isUrdu: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                // Event Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (event.isMajor) EmeraldSoftBg else CreamCard)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "${event.hijriDay} ${event.hijriMonthUrdu}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (event.isMajor) EmeraldPrimary else GoldDark
                    )
                }

                if (event.isMajor) {
                    Text(
                        text = "عظیم الشان مناسبت ✦",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = event.titleUrdu,
                fontSize = 16.sp,
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
