package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.QuranRepository
import com.example.ui.theme.*
import com.example.util.QuranAudioPlayerManager
import com.example.util.QuranReciter
import java.util.Locale

@Composable
fun QuranPlayerBar(
    modifier: Modifier = Modifier
) {
    val playerState by QuranAudioPlayerManager.playerState.collectAsState()
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }

    if (playerState.currentSurahNumber == null) return

    val currentSurah = QuranRepository.SURAHS_LIST.find { it.number == playerState.currentSurahNumber }
        ?: return

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag("quran_player_bar"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = EmeraldDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        listOf(EmeraldDark, Color(0xFF071F16))
                    )
                )
                .padding(14.dp)
        ) {
            // Top Row: Title + Reciter + Expand / Close
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(GoldGlow)
                            .border(1.5.dp, GoldLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${currentSurah.number}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = currentSurah.nameArabic,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "(${currentSurah.nameUrdu})",
                                fontSize = 12.sp,
                                color = GoldLight
                            )
                        }

                        Text(
                            text = "قاری: ${playerState.reciter.nameUrdu}",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { isExpanded = !isExpanded },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.Tune,
                            contentDescription = "Controls",
                            tint = GoldLight,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(
                        onClick = { QuranAudioPlayerManager.stop() },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Stop",
                            tint = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Expanded Settings (Reciter choice, Speed, Offline download)
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Divider(color = Color.White.copy(alpha = 0.15f))

                    // Reciter Choice
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "قاری کا انتخاب:",
                            fontSize = 12.sp,
                            color = GoldLight,
                            fontWeight = FontWeight.SemiBold
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            QuranReciter.values().forEach { reciter ->
                                val isSelected = playerState.reciter == reciter
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) GoldPrimary else Color.White.copy(alpha = 0.1f),
                                    modifier = Modifier.clickable {
                                        QuranAudioPlayerManager.playSurah(context, currentSurah.number, reciter)
                                    }
                                ) {
                                    Text(
                                        text = if (reciter == QuranReciter.MISHARY_ALAFASY) "العفاسی" else "عبد الباسط",
                                        fontSize = 11.sp,
                                        color = if (isSelected) EmeraldDark else Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Playback Speed & Offline Download
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Speeds: 0.75x, 1.0x, 1.25x
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = "رفتار:", fontSize = 11.sp, color = TextMuted)
                            listOf(0.75f, 1.0f, 1.25f, 1.5f).forEach { speed ->
                                val isSelected = playerState.playbackSpeed == speed
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (isSelected) EmeraldLight else Color.Transparent,
                                    modifier = Modifier.clickable {
                                        QuranAudioPlayerManager.setSpeed(speed)
                                    }
                                ) {
                                    Text(
                                        text = "${speed}x",
                                        fontSize = 10.sp,
                                        color = if (isSelected) EmeraldDark else Color.White.copy(alpha = 0.8f),
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        // Download Offline
                        if (playerState.isDownloading) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(
                                    progress = { playerState.downloadProgress },
                                    modifier = Modifier.size(16.dp),
                                    color = GoldLight,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${(playerState.downloadProgress * 100).toInt()}%",
                                    fontSize = 10.sp,
                                    color = GoldLight
                                )
                            }
                        } else {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (playerState.isCachedOffline) Color(0xFF2E7D32) else GoldDark.copy(alpha = 0.3f),
                                modifier = Modifier.clickable {
                                    QuranAudioPlayerManager.downloadSurahForOffline(context, currentSurah.number)
                                }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = if (playerState.isCachedOffline) Icons.Default.CheckCircle else Icons.Default.Download,
                                        contentDescription = null,
                                        tint = if (playerState.isCachedOffline) Color.White else GoldLight,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (playerState.isCachedOffline) "آف لائن دستیاب" else "ڈاؤنلوڈ آڈیو",
                                        fontSize = 10.sp,
                                        color = if (playerState.isCachedOffline) Color.White else GoldLight,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Progress Slider
            Slider(
                value = if (playerState.durationMs > 0) playerState.currentPositionMs.toFloat() / playerState.durationMs else 0f,
                onValueChange = { fraction ->
                    if (playerState.durationMs > 0) {
                        val newPos = (fraction * playerState.durationMs).toInt()
                        QuranAudioPlayerManager.seekTo(newPos)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                colors = SliderDefaults.colors(
                    thumbColor = GoldLight,
                    activeTrackColor = GoldLight,
                    inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                )
            )

            // Time and Play / Pause Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatMs(playerState.currentPositionMs),
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )

                // Central Control Buttons: -10s, Play/Pause, +10s
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    IconButton(
                        onClick = {
                            val newPos = (playerState.currentPositionMs - 10000).coerceAtLeast(0)
                            QuranAudioPlayerManager.seekTo(newPos)
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Replay10,
                            contentDescription = "Rewind 10s",
                            tint = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(GoldPrimary)
                            .clickable {
                                QuranAudioPlayerManager.togglePlayPause(context, currentSurah.number)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (playerState.isBuffering) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = EmeraldDark,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = if (playerState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (playerState.isPlaying) "Pause" else "Play",
                                tint = EmeraldDark,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            val newPos = (playerState.currentPositionMs + 10000).coerceAtMost(playerState.durationMs)
                            QuranAudioPlayerManager.seekTo(newPos)
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Forward10,
                            contentDescription = "Forward 10s",
                            tint = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Text(
                    text = formatMs(playerState.durationMs),
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}

private fun formatMs(ms: Int): String {
    val totalSecs = ms / 1000
    val minutes = totalSecs / 60
    val seconds = totalSecs % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}
