package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.repository.KhanqahRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*

data class WebsiteSectionItem(
    val id: String,
    val titleUrdu: String,
    val titleHindi: String,
    val titleEnglish: String,
    val descriptionUrdu: String,
    val descriptionEnglish: String,
    val targetUrl: String,
    val inAppRoute: String?,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebsiteScreen(
    currentLanguage: AppLanguage,
    onBack: () -> Unit,
    onNavigateInApp: (String) -> Unit
) {
    val context = LocalContext.current
    val isUrdu = currentLanguage == AppLanguage.URDU

    val websiteSections = listOf(
        WebsiteSectionItem(
            id = "books",
            titleUrdu = "کتب خانہ و تصانیف (Books & Publications)",
            titleHindi = "किताबें और प्रकाशन",
            titleEnglish = "Books & Publications",
            descriptionUrdu = "شاہ فضلِ رسول بدایونیؒ اور اکابرینِ بدایوں کی نادر تصانیف کا مستند ذخیرہ۔",
            descriptionEnglish = "Authentic manuscripts, commentaries and treatises of Budaun elders.",
            targetUrl = "https://www.qadri.in/books",
            inAppRoute = "books",
            icon = Icons.Default.MenuBook
        ),
        WebsiteSectionItem(
            id = "scholars",
            titleUrdu = "مشائخ و علماء (Akabir & Scholars)",
            titleHindi = "मशाईख व उलेमा की जीवनी",
            titleEnglish = "Elders & Spiritual Personalities",
            descriptionUrdu = "خانقاہِ قادریہ کے بانیان، سجادہ نشینان اور اصحابِ علم کے سوانحی خاکے۔",
            descriptionEnglish = "Biographies and scholarly achievements of the saints of Budaun.",
            targetUrl = "https://www.qadri.in/",
            inAppRoute = "khanqah_section",
            icon = Icons.Default.People
        ),
        WebsiteSectionItem(
            id = "history",
            titleUrdu = "تاریخِ خانقاہ قادریہ (History & Heritage)",
            titleHindi = "ख़ानक़ाह का इतिहास व विरासत",
            titleEnglish = "History & Heritage",
            descriptionUrdu = "بدایوں شریف میں خانقاہِ قادریہ مجیدیہ کے قیام اور صدیوں پر محیط خدمات کی تاریخ۔",
            descriptionEnglish = "The establishment and historical role of Khanqah Qadriah in India.",
            targetUrl = "https://www.qadri.in/",
            inAppRoute = "about",
            icon = Icons.Default.AccountBalance
        ),
        WebsiteSectionItem(
            id = "multimedia",
            titleUrdu = "ویڈیوز و خطابات (Lectures & Videos)",
            titleHindi = "वीडियो व बयानात",
            titleEnglish = "Lectures & Official Videos",
            descriptionUrdu = "عرسِ قادری، محافلِ نعت اور کانفرنسز کے آفیشل ویڈیوز کا لنک۔",
            descriptionEnglish = "Official lectures, Urs-e-Qadri coverage and multimedia.",
            targetUrl = "https://www.youtube.com/@haqmultimedia",
            inAppRoute = "videos",
            icon = Icons.Default.PlayCircle
        ),
        WebsiteSectionItem(
            id = "announcements",
            titleUrdu = "اعلانات و عرسِ مبارک (Announcements & Events)",
            titleHindi = "महत्वपूर्ण सूचनाएं व उर्स",
            titleEnglish = "Events & Urs Schedule",
            descriptionUrdu = "سجادہ نشین صاحب کی جانب سے سالانہ عرس اور دینی پروگراموں کے باضابطہ اعلانات۔",
            descriptionEnglish = "Official schedules of Urs Mubarak and Khanqah programs.",
            targetUrl = "https://www.qadri.in/",
            inAppRoute = "calendar",
            icon = Icons.Default.Campaign
        ),
        WebsiteSectionItem(
            id = "contact_location",
            titleUrdu = "رابطہ و پتہ (Contact & Location)",
            titleHindi = "संपर्क व ख़ानक़ाह का पता",
            titleEnglish = "Contact & Dargah Location",
            descriptionUrdu = "محلہ سوتھا، بدایوں شریف، یوپی — فون، ای میل اور گوگل میپس نیویگیشن۔",
            descriptionEnglish = "Mohalla Sotha, Budaun, UP - official contact information.",
            targetUrl = "https://www.qadri.in/",
            inAppRoute = "contact",
            icon = Icons.Default.Place
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("website_screen_container")
    ) {
        TopAppBar(
            title = {
                Text(
                    text = when (currentLanguage) {
                        AppLanguage.URDU -> "خانقاہ قادریہ ویب سائٹ (qadri.in)"
                        AppLanguage.HINDI -> "ख़ानक़ाह क़ादरिया वेबसाइट"
                        AppLanguage.ENGLISH -> "Khanqah Qadriah Website"
                        AppLanguage.HINGLISH -> "Khanqah Qadriah Website"
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
            actions = {
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_WEBSITE_URL))
                    context.startActivity(intent)
                }) {
                    Icon(imageVector = Icons.Default.OpenInNew, contentDescription = "Open in browser", tint = GoldLight)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = EmeraldDark)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Hero Card with Khanqah Logo
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_WEBSITE_URL))
                            context.startActivity(intent)
                        }
                        .testTag("website_hero_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = EmeraldDark),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        KhanqahOfficialLogo(
                            size = 80.dp,
                            showUrduName = false,
                            elevation = 6.dp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Khanqah Qadriah Official Portal",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                        Text(
                            text = "www.qadri.in",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "خانقاہِ قادریہ مجیدیہ بدایوں شریف کا باضابطہ ڈیجیٹل پورٹل",
                            fontSize = 12.sp,
                            color = GoldLight.copy(alpha = 0.9f)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_WEBSITE_URL))
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Icon(Icons.Default.Language, contentDescription = null, tint = EmeraldDark, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isUrdu) "ویب سائٹ کھولیں (qadri.in)" else "Open Website (qadri.in)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        }
                    }
                }
            }

            // Section Header
            item {
                Text(
                    text = if (isUrdu) "ویب سائٹ کے اہم شعبہ جات:" else "Official Website Sections:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )
            }

            // Section Cards
            items(websiteSections) { section ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (section.inAppRoute != null) {
                                onNavigateInApp(section.inAppRoute)
                            } else {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(section.targetUrl))
                                context.startActivity(intent)
                            }
                        }
                        .testTag("website_section_${section.id}"),
                    shape = RoundedCornerShape(16.dp),
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
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(EmeraldSoftBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = section.icon,
                                contentDescription = section.titleEnglish,
                                tint = EmeraldPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = when (currentLanguage) {
                                    AppLanguage.URDU -> section.titleUrdu
                                    AppLanguage.HINDI -> section.titleHindi
                                    else -> section.titleEnglish
                                },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = if (isUrdu) section.descriptionUrdu else section.descriptionEnglish,
                                fontSize = 11.sp,
                                color = TextCharcoal,
                                lineHeight = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(section.targetUrl))
                                context.startActivity(intent)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.OpenInNew,
                                contentDescription = "Open URL",
                                tint = GoldDark,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
