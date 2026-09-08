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
import com.example.ui.theme.*

data class MoreMenuItem(
    val id: String,
    val titleUrdu: String,
    val titleEnglish: String,
    val subtitleUrdu: String,
    val subtitleEnglish: String,
    val icon: ImageVector,
    val iconColor: Color,
    val testTag: String
)

@Composable
fun MoreScreen(
    currentLanguage: AppLanguage = AppLanguage.URDU,
    onLanguageSelected: (AppLanguage) -> Unit,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    val isUrdu = currentLanguage == AppLanguage.URDU

    val khanqahItems = listOf(
        MoreMenuItem(
            id = "khanqah_section",
            titleUrdu = "خانقاہ قادریہ بدایوں شریف",
            titleEnglish = "Khanqah Qadriah Budaun Shareef",
            subtitleUrdu = "تاریخ، روحانی سلسلہ، سجادہ نشین و خدمات",
            subtitleEnglish = "History, spiritual lineage & services",
            icon = Icons.Default.Mosque,
            iconColor = EmeraldPrimary,
            testTag = "more_item_khanqah"
        ),
        MoreMenuItem(
            id = "videos",
            titleUrdu = "ویڈیو گیلری و یوٹیوب",
            titleEnglish = "Video Gallery & YouTube",
            subtitleUrdu = "عرسِ قادری، خطابات، محافل و بیانات",
            subtitleEnglish = "Urs, lectures & official YouTube channel",
            icon = Icons.Default.PlayCircle,
            iconColor = Color(0xFFCC0000),
            testTag = "more_item_videos"
        ),
        MoreMenuItem(
            id = "photos",
            titleUrdu = "تصاویر و نوادرات",
            titleEnglish = "Photo Gallery & Archives",
            subtitleUrdu = "خانقاہ، مزارات اور نایاب قلمی نسخے",
            subtitleEnglish = "Shrines, architecture & manuscripts",
            icon = Icons.Default.PhotoLibrary,
            iconColor = GoldDark,
            testTag = "more_item_photos"
        ),
        MoreMenuItem(
            id = "contact",
            titleUrdu = "رابطہ و نقشہ",
            titleEnglish = "Contact & Location",
            subtitleUrdu = "آفیشل ویب سائٹ (qadri.in)، پتہ و گوگل میپس",
            subtitleEnglish = "Website (qadri.in), address & Google Maps",
            icon = Icons.Default.ContactPhone,
            iconColor = Color(0xFF1565C0),
            testTag = "more_item_contact"
        )
    )

    val regularMenuItems = listOf(
        MoreMenuItem(
            id = "qibla",
            titleUrdu = "قبلہ رخ (Qibla Direction)",
            titleEnglish = "Qibla Direction",
            subtitleUrdu = "سمتِ کعبہ و فاصلہ تا مکہ مکرمہ",
            subtitleEnglish = "Kaaba compass & distance",
            icon = Icons.Default.Explore,
            iconColor = GoldDark,
            testTag = "more_item_qibla"
        ),
        MoreMenuItem(
            id = "calendar",
            titleUrdu = "اسلامی کیلنڈر و ایام",
            titleEnglish = "Islamic Calendar",
            subtitleUrdu = "ہجری و عیسوی تواریخ اور اہل سنت کے اہم ایام",
            subtitleEnglish = "Hijri dates & Islamic events",
            icon = Icons.Default.CalendarMonth,
            iconColor = Color(0xFF2A5298),
            testTag = "more_item_calendar"
        ),
        MoreMenuItem(
            id = "tasbeeh",
            titleUrdu = "تسبیح و ذکر",
            titleEnglish = "Tasbeeh Counter",
            subtitleUrdu = "ڈیجیٹل کاؤنٹر، اورادِ قادریہ و مسنون اذکار",
            subtitleEnglish = "Digital counter & Qadri litanies",
            icon = Icons.Default.TouchApp,
            iconColor = Color(0xFFE65100),
            testTag = "more_item_tasbeeh"
        ),
        MoreMenuItem(
            id = "duas",
            titleUrdu = "دعائیں و اذکار",
            titleEnglish = "Duas & Azkar",
            subtitleUrdu = "صبح و شام، نماز اور روزمرہ کی مسنون دعائیں",
            subtitleEnglish = "Morning, evening & daily supplications",
            icon = Icons.Default.Favorite,
            iconColor = Color(0xFF00695C),
            testTag = "more_item_duas"
        ),
        MoreMenuItem(
            id = "knowledge",
            titleUrdu = "اسلامی معلومات و فتاویٰ",
            titleEnglish = "Islamic Knowledge & Fatawa",
            subtitleUrdu = "مسائلِ شرعیہ (حنفی)، علمی مضامین و احادیث",
            subtitleEnglish = "Hanafi jurisprudence & articles",
            icon = Icons.Default.AutoStories,
            iconColor = Color(0xFF388E3C),
            testTag = "more_item_knowledge"
        ),
        MoreMenuItem(
            id = "search",
            titleUrdu = "جامع تلاش",
            titleEnglish = "Universal Search",
            subtitleUrdu = "قرآن، کتب، دعاؤں اور مسائل میں تلاش",
            subtitleEnglish = "Search across Quran, Books & Duas",
            icon = Icons.Default.Search,
            iconColor = EmeraldPrimary,
            testTag = "more_item_search"
        ),
        MoreMenuItem(
            id = "admin",
            titleUrdu = "انتظامی پینل (Admin Panel)",
            titleEnglish = "Admin Panel",
            subtitleUrdu = "اعلانات اور دینی مواد کی تصدیق",
            subtitleEnglish = "Manage announcements & content verification",
            icon = Icons.Default.AdminPanelSettings,
            iconColor = Color(0xFF455A64),
            testTag = "more_item_admin"
        ),
        MoreMenuItem(
            id = "settings",
            titleUrdu = "سیٹنگز و ترجیحات",
            titleEnglish = "Settings & Preferences",
            subtitleUrdu = "شہر، حسابِ نماز، فونٹ سائز و نوٹیفکیشن",
            subtitleEnglish = "City, prayer method, fonts & notifications",
            icon = Icons.Default.Settings,
            iconColor = Color(0xFF78909C),
            testTag = "more_item_settings"
        ),
        MoreMenuItem(
            id = "about",
            titleUrdu = "خانقاہ قادریہ ایپ کے بارے میں",
            titleEnglish = "About Khanqah Qadriah App",
            subtitleUrdu = "ورژن، اغراض و مقاصد اور آفیشل پورٹل",
            subtitleEnglish = "Version, purpose & qadri.in official credentials",
            icon = Icons.Default.Info,
            iconColor = EmeraldDark,
            testTag = "more_item_about"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("more_screen_container"),
        contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 90.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Language Selector Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Translate, contentDescription = null, tint = EmeraldPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "زبان منتخب کریں / Select Language",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AppLanguage.values().forEach { lang ->
                            val isSelected = currentLanguage == lang
                            FilterChip(
                                selected = isSelected,
                                onClick = { onLanguageSelected(lang) },
                                label = { Text(lang.nativeName, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = EmeraldPrimary,
                                    selectedLabelColor = Color.White
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // Section: Khanqah Qadriah Official
        item {
            Text(
                text = if (isUrdu) "خانقاہِ قادریہ بدایوں شریف (آفیشل)" else "Khanqah Qadriah Budaun (Official)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldDark,
                modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
            )
        }

        items(khanqahItems) { item ->
            MoreItemRow(item = item, isUrdu = isUrdu, onClick = { onNavigate(item.id) })
        }

        // Section: Islamic Utilities & Settings
        item {
            Text(
                text = if (isUrdu) "اسلامی رہنمائی و سہولیات" else "Islamic Utilities & Settings",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldDark,
                modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
            )
        }

        items(regularMenuItems) { item ->
            MoreItemRow(item = item, isUrdu = isUrdu, onClick = { onNavigate(item.id) })
        }

        // Official Website Shortcut Button
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_WEBSITE_URL))
                        context.startActivity(intent)
                    },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = EmeraldDark)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Language, contentDescription = null, tint = GoldLight)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Official Website: www.qadri.in", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(text = "خانقاہ قادریہ بدایوں شریف کی باضابطہ ویب سائٹ کھولیں", fontSize = 10.sp, color = GoldLight)
                        }
                    }
                    Icon(Icons.Default.OpenInNew, contentDescription = null, tint = GoldLight, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
fun MoreItemRow(
    item: MoreMenuItem,
    isUrdu: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(item.testTag)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(item.iconColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = item.iconColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isUrdu) item.titleUrdu else item.titleEnglish,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextCharcoal
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (isUrdu) item.subtitleUrdu else item.subtitleEnglish,
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = TextMuted.copy(alpha = 0.6f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
