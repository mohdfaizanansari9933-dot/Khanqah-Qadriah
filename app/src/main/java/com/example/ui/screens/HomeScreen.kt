package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.data.model.AppLanguage
import com.example.data.model.DailyHadith
import com.example.data.model.HijriDate
import com.example.data.model.LocationInfo
import com.example.data.model.NextPrayerInfo
import com.example.data.model.PrayerTimes
import com.example.data.repository.KhanqahRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.components.PrayerCountdownCard
import com.example.ui.components.QuickAccessGrid
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    hijriDate: HijriDate,
    location: LocationInfo,
    nextPrayer: NextPrayerInfo,
    prayerTimes: PrayerTimes,
    dailyHadith: DailyHadith,
    adminBanner: String,
    currentLanguage: AppLanguage = AppLanguage.URDU,
    onNavigateToSection: (String) -> Unit,
    onOpenLocationPicker: () -> Unit,
    onSelectScholarById: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val isUrdu = currentLanguage == AppLanguage.URDU
    val dailyPacket = KhanqahRepository.TODAY_SPIRITUAL_PACKET

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("home_screen_content"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // 1. Khanqah Announcement Banner
        if (adminBanner.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = GoldGlow)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = "Announcement",
                            tint = GoldDark,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = adminBanner,
                            color = TextCharcoal,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // 2. Date Header Bar (Gregorian + Hijri + Urdu Day)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (currentLanguage) {
                        AppLanguage.URDU -> "آج: ${hijriDate.dayOfWeekUrdu}"
                        AppLanguage.HINDI -> "आज: ${hijriDate.dayOfWeekEnglish}"
                        else -> "Today: ${hijriDate.dayOfWeekEnglish}"
                    },
                    color = EmeraldDark,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${hijriDate.gregorianFormatted} | ${hijriDate.formattedUrdu}",
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }
        }

        // 3. Hero Prayer Countdown Card
        item {
            PrayerCountdownCard(
                hijriDate = hijriDate,
                location = location,
                nextPrayer = nextPrayer,
                prayerTimes = prayerTimes,
                isUrdu = isUrdu,
                onLocationClick = onOpenLocationPicker,
                onViewPrayerTimes = { onNavigateToSection("prayer") }
            )
        }

        // 4. Quick Prayer Schedule Strip
        item {
            PrayerTimesHorizontalStrip(
                prayerTimes = prayerTimes,
                nextPrayerName = nextPrayer.prayer.name,
                isUrdu = isUrdu,
                onClick = { onNavigateToSection("prayer") }
            )
        }

        // 5. Quick Access Grid (10 tiles)
        item {
            QuickAccessGrid(
                currentLanguage = currentLanguage,
                onItemClick = onNavigateToSection
            )
        }

        // 6. Khanqah Qadriah Official Portal Spotlight
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { onNavigateToSection("khanqah_section") },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KhanqahOfficialLogo(
                        size = 52.dp,
                        elevation = 4.dp
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "خانقاہِ قادریہ مجیدیہ بدایوں شریف",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Text(
                            text = "تاریخ، تذکرہ، اکابرین، کتب خانہ و آفیشل ویب سائٹ (qadri.in)",
                            fontSize = 11.sp,
                            color = TextCharcoal
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Open",
                        tint = GoldDark
                    )
                }
            }
        }

        // 7. Daily Spiritual Packet: Hadith of the Day
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .testTag("daily_hadith_card")
                    .clickable { onNavigateToSection("knowledge") },
                shape = RoundedCornerShape(18.dp),
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(EmeraldSoftBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = "Hadith",
                                    tint = EmeraldPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isUrdu) "حدیثِ مبارکہ (آج کا سبق)" else "Hadith of the Day",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        }
                        Text(
                            text = dailyPacket.hadithReference,
                            fontSize = 11.sp,
                            color = GoldDark,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = dailyPacket.hadithArabic,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = EmeraldDark,
                        lineHeight = 26.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = when (currentLanguage) {
                            AppLanguage.URDU -> dailyPacket.hadithTranslationUrdu
                            AppLanguage.HINDI -> dailyPacket.hadithHindi
                            else -> dailyPacket.hadithEnglish
                        },
                        fontSize = 12.sp,
                        color = TextCharcoal,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // 8. Daily Spiritual Packet: Masnoon Dua of the Day
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { onNavigateToSection("duas") },
                shape = RoundedCornerShape(18.dp),
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(EmeraldSoftBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Dua",
                                    tint = EmeraldPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isUrdu) "آج کی مسنون دعا" else "Dua of the Day",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        }
                        Text(
                            text = dailyPacket.duaReference,
                            fontSize = 11.sp,
                            color = GoldDark,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = dailyPacket.duaArabic,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = when (currentLanguage) {
                            AppLanguage.URDU -> dailyPacket.duaTranslationUrdu
                            AppLanguage.HINDI -> dailyPacket.duaHindi
                            else -> dailyPacket.duaEnglish
                        },
                        fontSize = 12.sp,
                        color = TextCharcoal
                    )
                }
            }
        }

        // 9. Scholar / Personality of the Day (Shah Fazl-e-Rasool ؒ)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { onSelectScholarById("shah_fazle_rasool") },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "شخصیتِ امروز • اکابرِ بدایوں شریف",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldDark
                        )
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = dailyPacket.personalityNameUrdu,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                    Text(
                        text = dailyPacket.personalityTitleUrdu,
                        fontSize = 11.sp,
                        color = GoldDark,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = dailyPacket.personalityBriefUrdu,
                        fontSize = 12.sp,
                        color = TextCharcoal,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // 10. Book of the Day (احقاق الحق)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { onNavigateToSection("books") },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF795548)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.MenuBook, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "کتابِ امروز: ${dailyPacket.bookTitleUrdu}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
                        Text(text = "مصنف: ${dailyPacket.bookAuthorUrdu}", fontSize = 11.sp, color = GoldDark)
                        Text(text = dailyPacket.bookBriefUrdu, fontSize = 11.sp, color = TextMuted, maxLines = 1)
                    }
                    Button(
                        onClick = { onNavigateToSection("books") },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(text = "مطالعہ", fontSize = 11.sp)
                    }
                }
            }
        }

        // 11. Official YouTube Channel (@haqmultimedia)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { onNavigateToSection("videos") },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFCC0000))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        KhanqahOfficialLogo(size = 38.dp, elevation = 2.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Official YouTube: @haqmultimedia",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "سرکاری یوٹیوب چینل • خطابات و بیانات",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_YOUTUBE_URL))
                            context.startActivity(intent)
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(text = "دیکھیں", color = Color(0xFFCC0000), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 12. Official Website Link (qadri.in)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { onNavigateToSection("website") },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = EmeraldDark)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        KhanqahOfficialLogo(size = 38.dp, elevation = 2.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "خانقاہ قادریہ ویب سائٹ: www.qadri.in",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "کتب خانہ، مشائخ و اکابرین اور تعارف",
                                fontSize = 10.sp,
                                color = GoldLight
                            )
                        }
                    }
                    Button(
                        onClick = { onNavigateToSection("website") },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(text = "پورٹل", color = EmeraldDark, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun PrayerTimesHorizontalStrip(
    prayerTimes: PrayerTimes,
    nextPrayerName: String,
    isUrdu: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() }
            .testTag("prayer_horizontal_strip"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val prayers = listOf(
                Pair(if (isUrdu) "فجر" else "Fajr", prayerTimes.fajr),
                Pair(if (isUrdu) "طلوع" else "Sunrise", prayerTimes.sunrise),
                Pair(if (isUrdu) "ظہر" else "Dhuhr", prayerTimes.dhuhr),
                Pair(if (isUrdu) "عصر" else "Asr", prayerTimes.asr),
                Pair(if (isUrdu) "مغرب" else "Maghrib", prayerTimes.maghrib),
                Pair(if (isUrdu) "عشاء" else "Isha", prayerTimes.isha)
            )

            prayers.forEach { (name, time) ->
                val isNext = nextPrayerName.equals(name, ignoreCase = true)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = if (isNext) {
                        Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(EmeraldSoftBg)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    } else Modifier
                ) {
                    Text(
                        text = name,
                        fontSize = 11.sp,
                        fontWeight = if (isNext) FontWeight.Bold else FontWeight.Medium,
                        color = if (isNext) EmeraldPrimary else TextCharcoal
                    )
                    Text(
                        text = time,
                        fontSize = 10.sp,
                        color = if (isNext) EmeraldDark else TextMuted,
                        fontWeight = if (isNext) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
