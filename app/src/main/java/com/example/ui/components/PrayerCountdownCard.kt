package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.data.model.LocationInfo
import com.example.data.model.NextPrayerInfo
import com.example.data.model.PrayerTimes
import com.example.ui.theme.*

@Composable
fun PrayerCountdownCard(
    hijriDate: HijriDate,
    location: LocationInfo,
    nextPrayer: NextPrayerInfo,
    prayerTimes: PrayerTimes,
    isUrdu: Boolean = true,
    onLocationClick: () -> Unit,
    onViewPrayerTimes: () -> Unit
) {
    // Format remaining time to hh:mm:ss
    val remainingSeconds = (nextPrayer.remainingMillis / 1000).coerceAtLeast(0)
    val hours = remainingSeconds / 3600
    val minutes = (remainingSeconds % 3600) / 60
    val seconds = remainingSeconds % 60
    val countdownString = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("prayer_countdown_card")
            .clickable { onViewPrayerTimes() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(EmeraldPrimary, EmeraldDark)
                    )
                )
                .padding(18.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Top row: Location & Hijri Date
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Location Badge
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.15f))
                            .clickable { onLocationClick() }
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = GoldLight,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = location.cityName,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )
                    }

                    // Hijri Date
                    Text(
                        text = "${hijriDate.day} ${hijriDate.monthUrdu} ${hijriDate.year} ھ",
                        color = GoldLight,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Center Highlight: Next Prayer & Countdown
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        if (nextPrayer.currentPrayer != null && nextPrayer.currentPrayerEndsIn != null) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = GoldPrimary.copy(alpha = 0.2f),
                                modifier = Modifier.padding(bottom = 4.dp)
                            ) {
                                Text(
                                    text = if (isUrdu) "جاری: ${nextPrayer.currentPrayer.urduName} (باقی: ${nextPrayer.currentPrayerEndsIn})" 
                                           else "Active: ${nextPrayer.currentPrayer.englishName} (${nextPrayer.currentPrayerEndsIn} left)",
                                    color = GoldLight,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = if (isUrdu) "اگلی نماز: ${nextPrayer.prayer.urduName}" else "Next: ${nextPrayer.prayer.englishName}",
                            color = CreamBg.copy(alpha = 0.9f),
                            fontSize = 14.sp
                        )
                        Text(
                            text = nextPrayer.timeFormatted,
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    // Countdown Ring / Box
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = if (isUrdu) "اگلی نماز میں باقی" else "Time Left",
                            color = GoldLight.copy(alpha = 0.85f),
                            fontSize = 11.sp
                        )
                        Text(
                            text = countdownString,
                            color = GoldLight,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = "Active",
                                tint = GoldPrimary,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isUrdu) "اہلِ سنت (حنفی)" else "Ahle Sunnat (Hanafi)",
                                color = CreamBg.copy(alpha = 0.8f),
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Progress Bar
                LinearProgressIndicator(
                    progress = { nextPrayer.progressFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(CircleShape),
                    color = GoldPrimary,
                    trackColor = Color.White.copy(alpha = 0.2f),
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Sehri & Iftar Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.1f))
                        .padding(horizontal = 12.dp, vertical = 7.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isUrdu) "سحری کا اختتام: ${prayerTimes.sehri}" else "Sehri Ends: ${prayerTimes.sehri}",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "|",
                        color = GoldPrimary.copy(alpha = 0.5f),
                        fontSize = 12.sp
                    )
                    Text(
                        text = if (isUrdu) "افطار / مغرب: ${prayerTimes.iftar}" else "Iftar: ${prayerTimes.iftar}",
                        color = GoldLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
