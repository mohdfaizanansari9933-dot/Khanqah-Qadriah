package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.repository.KhanqahRepository
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    currentLanguage: AppLanguage,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val isUrdu = currentLanguage == AppLanguage.URDU

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("contact_screen")
    ) {
        TopAppBar(
            title = {
                Text(
                    text = when (currentLanguage) {
                        AppLanguage.URDU -> "رابطہ خانقاہ قادریہ بدایوں شریف"
                        AppLanguage.HINDI -> "संपर्क ख़ानक़ाह क़ादरिया बदायूं"
                        AppLanguage.ENGLISH -> "Contact Khanqah Qadriah Budaun"
                        AppLanguage.HINGLISH -> "Rabita Khanqah Qadriah Budaun"
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
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "خانقاہِ قادریہ مجیدیہ بدایوں شریف",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isUrdu) KhanqahRepository.OFFICIAL_ADDRESS_URDU else KhanqahRepository.OFFICIAL_ADDRESS_ENGLISH,
                            fontSize = 13.sp,
                            color = TextCharcoal,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Quick Contact Buttons
            item {
                Text(
                    text = if (isUrdu) "آفیشل ذرائع سے رابطہ فرمائیں:" else "Official Contact Channels:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )
            }

            // Website Button
            item {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_WEBSITE_URL))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Icon(Icons.Default.Language, contentDescription = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "آفیشل ویب سائٹ: qadri.in",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // YouTube Button
            item {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_YOUTUBE_URL))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCC0000))
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "آفیشل یوٹیوب چینل (@haqmultimedia)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Google Maps Button
            item {
                Button(
                    onClick = {
                        val gmmIntentUri = Uri.parse(KhanqahRepository.GOOGLE_MAPS_GEO)
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        context.startActivity(mapIntent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldDark)
                ) {
                    Icon(Icons.Default.Place, contentDescription = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "گوگل میپس پر لوکیشن دیکھیں",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Email Button
            item {
                OutlinedButton(
                    onClick = {
                        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${KhanqahRepository.OFFICIAL_CONTACT_EMAIL}"))
                        context.startActivity(emailIntent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.Email, contentDescription = null, tint = EmeraldPrimary)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "ای میل: ${KhanqahRepository.OFFICIAL_CONTACT_EMAIL}",
                        fontSize = 14.sp,
                        color = EmeraldPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Phone Button
            item {
                OutlinedButton(
                    onClick = {
                        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${KhanqahRepository.OFFICIAL_CONTACT_PHONE.replace(" ", "")}"))
                        context.startActivity(callIntent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.Call, contentDescription = null, tint = EmeraldPrimary)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "فون نمبر: ${KhanqahRepository.OFFICIAL_CONTACT_PHONE}",
                        fontSize = 14.sp,
                        color = EmeraldPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Verification Notice
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = GoldGlow)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = GoldDark)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "مستند معلومات کا ریکارڈ",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldDark
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "تمام تر رابطے کی معلومات خانقاہ قادریہ مجیدیہ بدایوں شریف کی آفیشل ویب سائٹ (qadri.in) سے تصدیق شدہ ہیں۔",
                            fontSize = 11.sp,
                            color = TextCharcoal
                        )
                    }
                }
            }
        }
    }
}
