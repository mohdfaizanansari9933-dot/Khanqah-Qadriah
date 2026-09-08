package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.repository.KhanqahRepository
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoGalleryScreen(
    currentLanguage: AppLanguage,
    onBack: () -> Unit
) {
    val isUrdu = currentLanguage == AppLanguage.URDU

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("photo_gallery_screen")
    ) {
        TopAppBar(
            title = {
                Text(
                    text = when (currentLanguage) {
                        AppLanguage.URDU -> "تصاویر و نوادرات (آرکائیو)"
                        AppLanguage.HINDI -> "तस्वीरें व ऐतिहासिक संग्रह"
                        AppLanguage.ENGLISH -> "Photo Gallery & Archives"
                        AppLanguage.HINGLISH -> "Tasweer Gallery & Archive"
                    },
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
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = GoldGlow)
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Verified, contentDescription = null, tint = GoldDark)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "خانقاہ قادریہ مجیدیہ بدایوں شریف کے تاریخی و روحانی مناظر",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldDark
                        )
                    }
                }
            }

            items(KhanqahRepository.PHOTO_GALLERY) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Image Visual Placeholder
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(EmeraldSoftBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(EmeraldPrimary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Image, contentDescription = null, tint = GoldLight, modifier = Modifier.size(30.dp))
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = item.categoryUrdu,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (isUrdu) item.titleUrdu else item.titleEnglish,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = if (isUrdu) item.descriptionUrdu else item.descriptionEnglish,
                            fontSize = 12.sp,
                            color = TextCharcoal,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "ماخذ: ${item.sourceCredit}",
                            fontSize = 10.sp,
                            color = TextMuted
                        )
                    }
                }
            }
        }
    }
}
