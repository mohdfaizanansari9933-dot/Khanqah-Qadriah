package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.repository.KhanqahRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    currentLanguage: AppLanguage = AppLanguage.URDU,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val isUrdu = currentLanguage == AppLanguage.URDU

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("about_screen_container")
    ) {
        TopAppBar(
            title = {
                Text(
                    text = when (currentLanguage) {
                        AppLanguage.URDU -> "تعارف و شرائط (خانقاہ قادریہ)"
                        AppLanguage.HINDI -> "परिचय व गोपनीयता (ख़ानक़ाह क़ादरिया)"
                        AppLanguage.ENGLISH -> "About & Official Platform"
                        AppLanguage.HINGLISH -> "About Khanqah Qadriah Platform"
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
            // Emblem & App Identity
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        KhanqahOfficialLogo(
                            size = 84.dp,
                            elevation = 6.dp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "خانقاہ قادریہ مجیدیہ",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )

                        Text(
                            text = "بدایوں شریف، اتر پردیش، بھارت",
                            fontSize = 13.sp,
                            color = GoldDark,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "Official Digital Portal • www.qadri.in",
                            fontSize = 12.sp,
                            color = EmeraldPrimary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Text(
                            text = "سلسلہ عالیہ قادریہ • مسلکِ حق اہل سنت و جماعت",
                            fontSize = 11.sp,
                            color = TextMuted,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // Introduction & Vision
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Text(
                            text = if (isUrdu) "تعارف و اغراض و مقاصد" else "Purpose & Overview",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "خانقاہ قادریہ مجیدیہ بدایوں شریف کی یہ موبائل ایپلیکیشن مسلمانوں تک شریعت و طریقت کی مستند روشنی، نماز کے درست حسابی اوقات، قبلہ رخ، قرآن مجید، اورادِ قادریہ، اکابرِ بدایوں کی کتب، ویڈیوز اور تاریخی معلومات بہم پہنچانے کے لیے وقف کی گئی ہے۔\n\nاس میں شامل تمام تر تاریخی اور علمی معلومات آفیشل ویب سائٹ (qadri.in) اور خانوادۂ قادریہ کے معتبر اکابر کی تصانیف سے ماخوذ ہیں۔",
                            fontSize = 13.sp,
                            color = TextCharcoal,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            // Open Official Website Button
            item {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_WEBSITE_URL))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Icon(Icons.Default.Language, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "آفیشل ویب سائٹ وزٹ کریں (qadri.in)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Authentic Sources
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Verified, contentDescription = "Sources", tint = EmeraldPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isUrdu) "مستند حوالہ جات و مراجع" else "Verified Scholarly Sources",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "• احقاق الحق المبين (حضرت شاہ فضلِ رسول قادری بدایونی ؒ)\n• ارشاد المجيد في سلوك المريد (حضرت شاہ عبد المجید قادری بدایونی ؒ)\n• تذکرۂ خانوادۂ قادریہ بدایوں شریف (مولانا اسید الحق قادری ؒ)\n• قرآن مجید (مع کنز الایمان فی ترجمۃ القرآن - امام احمد رضا خان ؒ)\n• بہارِ شریعت (صدر الشریعہ مفتی محمد امجد علی اعظمی ؒ)\n• صحیح بخاری، صحیح مسلم، سنن ابن ماجہ، جامع ترمذی\n• آفیشل آرکائیو خانقاہ قادریہ مجیدیہ بدایوں شریف (qadri.in)",
                            fontSize = 12.sp,
                            color = TextCharcoal,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            // Privacy Policy
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Security, contentDescription = "Privacy", tint = EmeraldPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isUrdu) "پرائیویسی پالیسی (رازداری کا تحفظ)" else "Privacy Policy",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "یہ ایپلیکیشن صارف کی ذاتی رازداری کا مکمل احترام کرتی ہے۔ کوئی غیر متعلقہ ڈیٹا یا نجی معلومات محفوظ نہیں کی جاتیں۔ لوکیشن صرف نماز کے درست اوقات اور قبلہ رخ کے لیے ڈیوائس کی حد تک محدود ہے۔ تمام دینی کتب و اوراد بغیر اشتہارات کے خالصتاً فی سبیل اللہ فراہم کیے جا رہے ہیں۔",
                            fontSize = 12.sp,
                            color = TextCharcoal,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Official Portals and Links
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Language, contentDescription = "Official Portals", tint = EmeraldPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isUrdu) "آفیشل روابط و ڈیجیٹل پورٹلز" else "Official Portals & Contacts",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        // Website Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "Official Website", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = EmeraldDark)
                                Text(text = KhanqahRepository.OFFICIAL_WEBSITE_URL, fontSize = 12.sp, color = GoldDark)
                            }
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_WEBSITE_URL))
                                    context.startActivity(intent)
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text("qadri.in", fontSize = 11.sp)
                            }
                        }

                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = GoldGlow)

                        // YouTube Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "Official YouTube Channel", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = EmeraldDark)
                                Text(text = "Haq Multimedia (@haqmultimedia)", fontSize = 12.sp, color = Color(0xFFCC0000))
                            }
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_YOUTUBE_URL))
                                    context.startActivity(intent)
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCC0000)),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text("YouTube", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }

            // Religious Respect
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = GoldGlow)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Text(
                            text = "شرعی احتیاط و گزارش",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "چونکہ اس ایپ میں قرآنی آیات، احادیثِ کریمہ اور اولیاء کے کلام موجود ہیں، لہٰذا فون کو باادب رکھیں اور طہارت کا خصوصی اہتمام فرمائیں۔",
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
