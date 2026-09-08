package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.SavedContentItem
import com.example.data.repository.UserProfileRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*
import com.example.util.DailyHadithPosterEngine
import com.example.util.DailySpiritualMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DailyHadithPosterScreen(
    isUrdu: Boolean = true,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val messages = remember { DailyHadithPosterEngine.MESSAGES }
    val currentMessage = messages[selectedIndex]

    var previewBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isGeneratingPoster by remember { mutableStateOf(false) }

    // Generate preview bitmap when selection changes
    LaunchedEffect(currentMessage.id) {
        isGeneratingPoster = true
        withContext(Dispatchers.IO) {
            try {
                previewBitmap = DailyHadithPosterEngine.generatePosterBitmap(context, currentMessage)
            } catch (_: Exception) {}
        }
        isGeneratingPoster = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("daily_hadith_poster_screen")
    ) {
        // Top App Bar
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
            colors = CardDefaults.cardColors(containerColor = EmeraldDark),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(EmeraldDark, EmeraldPrimary)
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    KhanqahOfficialLogo(size = 38.dp, circular = true)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "روزانہ حدیث و اچھی بات (ہندی پوسٹر)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Daily Hindi Hadith Card & Poster Share",
                            fontSize = 10.sp,
                            color = GoldLight
                        )
                    }
                }

                IconButton(
                    onClick = {
                        DailyHadithPosterEngine.sharePosterImage(context, currentMessage)
                    }
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = GoldLight)
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 90.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Day selector tabs
            item {
                Text(
                    text = "منتخب دن کا پوسٹر منتخب فرمائیں:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(messages) { index, msg ->
                        val isSelected = index == selectedIndex
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) EmeraldPrimary else CreamSurface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) GoldLight else Color(0xFFE0DACB)
                            ),
                            modifier = Modifier.clickable { selectedIndex = index }
                        ) {
                            Text(
                                text = "دن ${index + 1}: ${msg.hindiHadithTitle}",
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else TextCharcoal,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // High Resolution Poster Preview Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        if (previewBitmap != null) {
                            Image(
                                bitmap = previewBitmap!!.asImageBitmap(),
                                contentDescription = "Daily Hadith Poster",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1080f / 1350f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, GoldLight, RoundedCornerShape(12.dp))
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(280.dp)
                                    .background(EmeraldSoftBg, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = EmeraldPrimary)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Sharing & Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    DailyHadithPosterEngine.sharePosterImage(context, currentMessage)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("share_hadith_poster_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("شیئر کریں", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = {
                                    val success = DailyHadithPosterEngine.savePosterToGallery(context, currentMessage)
                                    if (success) {
                                        Toast.makeText(context, "پوسٹر گیلری میں محفوظ کر لیا گیا", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("save_gallery_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp), tint = EmeraldDark)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("گیلری میں سیو", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = {
                                    val textToCopy = buildString {
                                        append("✨ ${currentMessage.hindiHadithTitle} ✨\n")
                                        append("खानक़ाह-ए-आलिया क़ादरिया, बदायूं शरीफ़\n\n")
                                        append("حدیث شریف:\n${currentMessage.arabicHadith}\n\n")
                                        append("तर्जुमा (हिंदी):\n${currentMessage.hindiHadithText}\n")
                                        append("हवाला: ${currentMessage.hadithReference}\n")
                                        append("${currentMessage.khanqahBookName}\n\n")
                                        append("आज की एक अच्छी बात:\n${currentMessage.ekAchhiBaatHindi}\n\n")
                                        append("رابطہ: +91 94125 61786 | www.qadri.in")
                                    }
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    clipboard.setPrimaryClip(ClipData.newPlainText("Hadith Text", textToCopy))
                                    Toast.makeText(context, "متن کلپ بورڈ پر کاپی ہو گیا", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("متن کاپی کریں", fontSize = 11.sp, color = EmeraldDark)
                            }

                            TextButton(
                                onClick = {
                                    UserProfileRepository.saveContentItem(
                                        SavedContentItem(
                                            id = "hadith_${currentMessage.id}",
                                            title = currentMessage.hindiHadithTitle,
                                            subtitle = currentMessage.hadithReference,
                                            type = "HADITH_POSTER",
                                            content = currentMessage.hindiHadithText
                                        )
                                    )
                                    Toast.makeText(context, "محفوظ لسٹ میں شامل کر لیا گیا", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(Icons.Default.BookmarkAdd, contentDescription = null, modifier = Modifier.size(16.dp), tint = GoldDark)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("بک مارک کریں", fontSize = 11.sp, color = GoldDark)
                            }
                        }
                    }
                }
            }

            // Textual Breakdown Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "متن و تفصیلات (تفصیلی مطالعہ):",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = currentMessage.arabicHadith,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = currentMessage.hindiHadithText,
                            fontSize = 13.sp,
                            color = TextCharcoal,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "${currentMessage.hadithReference} • ${currentMessage.khanqahBookName}",
                            fontSize = 11.sp,
                            color = GoldDark,
                            fontWeight = FontWeight.Medium
                        )

                        Divider(modifier = Modifier.padding(vertical = 10.dp), color = CreamBg)

                        Text(
                            text = "💡 आज की एक अच्छी बात:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentMessage.ekAchhiBaatHindi,
                            fontSize = 12.sp,
                            color = TextCharcoal,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}
