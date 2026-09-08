package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.HuzoorAteefMiyaSpeech
import com.example.data.repository.KhanqahRepository
import com.example.ui.theme.*

/**
 * Prominent Front-Screen Section for "Haq Multimedia" featuring
 * Janasheen Huzoor Tajdar-e-Ahle Sunnat Hazrat Sufi Mohammad Ateef Miya Qadri's speeches.
 */
@Composable
fun HaqMultimediaSpeechesSection(
    currentLanguage: AppLanguage = AppLanguage.URDU,
    onViewAllVideos: () -> Unit
) {
    val context = LocalContext.current
    val isUrdu = currentLanguage == AppLanguage.URDU
    val speeches = KhanqahRepository.HUZOOR_ATEEF_MIYA_SPEECHES
    val featuredSpeech = speeches.firstOrNull { it.isFeatured } ?: speeches.firstOrNull()
    val otherSpeeches = speeches.filter { it.id != featuredSpeech?.id }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("haq_multimedia_speeches_section")
    ) {
        // Section Header with YouTube Badge & Haq Multimedia Branding
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Red YouTube Icon Badge
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFCC0000)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Haq Multimedia",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isUrdu) "حق ملٹی میڈیا" else "Haq Multimedia",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFCC0000).copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = "@haqmultimedia",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFCC0000),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = if (isUrdu) "خطابات: حضور عاطف میاں قادری مدظلہ العالی" else "Speeches: Huzoor Ateef Miya Qadri",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GoldDark
                    )
                }
            }

            TextButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_YOUTUBE_URL))
                    context.startActivity(intent)
                }
            ) {
                Text(
                    text = if (isUrdu) "یوٹیوب" else "YouTube",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFCC0000)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    imageVector = Icons.Default.OpenInNew,
                    contentDescription = null,
                    tint = Color(0xFFCC0000),
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        // Subtitle with Sajjadah Nashin title
        Text(
            text = if (isUrdu)
                "جانشین حضور تاجدارِ اہلِ سنت حضرت صوفی محمد عاطف میاں قادری (سجادہ نشین خانقاہِ عالیہ قادریہ بدایوں شریف)"
            else
                "Discourses by Sajjadah Nashin Hazrat Sufi Mohammad Ateef Miya Qadri",
            fontSize = 11.sp,
            color = TextMuted,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Main Featured Speech Hero Card
        if (featuredSpeech != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .testTag("featured_ateef_speech_card")
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(featuredSpeech.youtubeUrl))
                        context.startActivity(intent)
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = EmeraldDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Top tag row: Occasion & Duration
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFCC0000)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayCircle,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isUrdu) "خطابِ خاص" else "Featured Speech",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.Black.copy(alpha = 0.35f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccessTime,
                                    contentDescription = null,
                                    tint = GoldLight,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = featuredSpeech.duration,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Speech Title
                    Text(
                        text = when (currentLanguage) {
                            AppLanguage.URDU -> featuredSpeech.titleUrdu
                            AppLanguage.HINDI -> featuredSpeech.titleHindi
                            AppLanguage.ENGLISH -> featuredSpeech.titleEnglish
                            AppLanguage.HINGLISH -> featuredSpeech.titleEnglish
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Speaker Label
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.RecordVoiceOver,
                            contentDescription = null,
                            tint = GoldLight,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isUrdu) featuredSpeech.speakerUrdu else featuredSpeech.speakerEnglish,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = GoldLight
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Occasion
                    Text(
                        text = if (isUrdu) "مناسبت: ${featuredSpeech.occasionUrdu}" else "Occasion: ${featuredSpeech.occasionEnglish}",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Summary
                    Text(
                        text = featuredSpeech.summaryUrdu,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Action Buttons Row: Watch on YouTube & Share
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(featuredSpeech.youtubeUrl))
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("play_ateef_speech_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFCC0000),
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isUrdu) "خطاب سنیں / دیکھیں" else "Watch Speech",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "✨ حق ملٹی میڈیا آفیشل: ${featuredSpeech.titleUrdu}\n" +
                                                "خطاب: ${featuredSpeech.speakerUrdu}\n" +
                                                "مناسبت: ${featuredSpeech.occasionUrdu}\n" +
                                                "لنک: ${featuredSpeech.youtubeUrl}\n" +
                                                "خانقاہِ عالیہ قادریہ بدایوں شریف"
                                    )
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "شیئر خطاب"))
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldLight.copy(alpha = 0.6f)),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                modifier = Modifier.size(16.dp),
                                tint = GoldLight
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Horizontal Carousel of Other Speeches by Huzoor Ateef Miya
        Text(
            text = if (isUrdu) "مزید ارشادات و خطاباتِ حضور عاطف میاں" else "More Speeches by Huzoor Ateef Miya",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldDark,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(otherSpeeches) { speech ->
                AteefSpeechMiniCard(
                    speech = speech,
                    currentLanguage = currentLanguage,
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(speech.youtubeUrl))
                        context.startActivity(intent)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Bottom Strip: Subscribe to Haq Multimedia + View All Videos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_YOUTUBE_URL))
                        context.startActivity(intent)
                    },
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFCC0000).copy(alpha = 0.08f),
                border = androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFFCC0000).copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Subscriptions,
                        contentDescription = null,
                        tint = Color(0xFFCC0000),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isUrdu) "سبسکرائب @haqmultimedia" else "Subscribe Channel",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFCC0000)
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onViewAllVideos() },
                shape = RoundedCornerShape(12.dp),
                color = EmeraldSoftBg,
                border = androidx.compose.foundation.BorderStroke(0.8.dp, EmeraldPrimary.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.VideoLibrary,
                        contentDescription = null,
                        tint = EmeraldDark,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isUrdu) "تمام بیانات و ویڈیوز" else "All Speeches & Videos",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                }
            }
        }
    }
}

@Composable
fun AteefSpeechMiniCard(
    speech: HuzoorAteefMiyaSpeech,
    currentLanguage: AppLanguage,
    onClick: () -> Unit
) {
    val isUrdu = currentLanguage == AppLanguage.URDU

    Card(
        modifier = Modifier
            .width(220.dp)
            .clickable { onClick() }
            .testTag("ateef_speech_mini_card_${speech.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Top row: Play badge + duration
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFCC0000)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = EmeraldSoftBg
                ) {
                    Text(
                        text = speech.duration,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = EmeraldDark,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = when (currentLanguage) {
                    AppLanguage.URDU -> speech.titleUrdu
                    AppLanguage.HINDI -> speech.titleHindi
                    AppLanguage.ENGLISH -> speech.titleEnglish
                    AppLanguage.HINGLISH -> speech.titleEnglish
                },
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldDark,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (isUrdu) speech.occasionUrdu else speech.occasionEnglish,
                fontSize = 10.sp,
                color = TextMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "حق ملٹی میڈیا",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFCC0000)
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Play",
                    tint = GoldDark,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}
