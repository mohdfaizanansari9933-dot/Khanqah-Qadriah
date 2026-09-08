package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.theme.*

data class QuickAccessItem(
    val id: String,
    val titleUrdu: String,
    val titleHindi: String,
    val titleEnglish: String,
    val titleHinglish: String,
    val icon: ImageVector,
    val iconBgColor: Color,
    val testTag: String
)

@Composable
fun QuickAccessGrid(
    currentLanguage: AppLanguage = AppLanguage.URDU,
    onItemClick: (String) -> Unit
) {
    val items = listOf(
        QuickAccessItem(
            id = "calendar",
            titleUrdu = "۳۰ روزہ کیلنڈر",
            titleHindi = "क़ादरी कैलेंडर",
            titleEnglish = "Qadri Calendar",
            titleHinglish = "Qadri Calendar",
            icon = Icons.Default.CalendarMonth,
            iconBgColor = Color(0xFF1565C0),
            testTag = "quick_access_calendar"
        ),
        QuickAccessItem(
            id = "shajra",
            titleUrdu = "شجرہ عالیہ",
            titleHindi = "शजरा शरीफ़",
            titleEnglish = "Shajra Shareef",
            titleHinglish = "Shajra Shareef",
            icon = Icons.Default.AccountTree,
            iconBgColor = GoldDark,
            testTag = "quick_access_shajra"
        ),
        QuickAccessItem(
            id = "salam",
            titleUrdu = "سلام و درود",
            titleHindi = "सलाम व दुरुद",
            titleEnglish = "Salam & Durood",
            titleHinglish = "Salam & Durood",
            icon = Icons.Default.Favorite,
            iconBgColor = Color(0xFF8E24AA),
            testTag = "quick_access_salam"
        ),
        QuickAccessItem(
            id = "khanqah_section",
            titleUrdu = "خانقاہ قادریہ",
            titleHindi = "ख़ानक़ाह क़ादरिया",
            titleEnglish = "Khanqah Qadriah",
            titleHinglish = "Khanqah Qadriah",
            icon = Icons.Default.Mosque,
            iconBgColor = EmeraldPrimary,
            testTag = "quick_access_khanqah"
        ),
        QuickAccessItem(
            id = "quran",
            titleUrdu = "قرآن پاک",
            titleHindi = "क़ुरआन पाक",
            titleEnglish = "Holy Quran",
            titleHinglish = "Holy Quran",
            icon = Icons.Default.MenuBook,
            iconBgColor = EmeraldDark,
            testTag = "quick_access_quran"
        ),
        QuickAccessItem(
            id = "prayer",
            titleUrdu = "نماز کے اوقات",
            titleHindi = "नमाज़ के औक़ात",
            titleEnglish = "Prayer Times",
            titleHinglish = "Namaz ke Auqaat",
            icon = Icons.Default.AccessTime,
            iconBgColor = Color(0xFF1B5E20),
            testTag = "quick_access_prayer"
        ),
        QuickAccessItem(
            id = "qibla",
            titleUrdu = "قبلہ رخ",
            titleHindi = "क़िबला रुख़",
            titleEnglish = "Qibla Finder",
            titleHinglish = "Qibla Finder",
            icon = Icons.Default.Explore,
            iconBgColor = GoldDark,
            testTag = "quick_access_qibla"
        ),
        QuickAccessItem(
            id = "books",
            titleUrdu = "علم کی کتابیں",
            titleHindi = "दीनी किताबें",
            titleEnglish = "Islamic Books",
            titleHinglish = "Deeni Kitaben",
            icon = Icons.Default.LibraryBooks,
            iconBgColor = Color(0xFF795548),
            testTag = "quick_access_books"
        ),
        QuickAccessItem(
            id = "videos",
            titleUrdu = "ویڈیوز و بیانات",
            titleHindi = "वीडियो व बयानात",
            titleEnglish = "Videos & Lectures",
            titleHinglish = "Videos & Speeches",
            icon = Icons.Default.PlayCircle,
            iconBgColor = Color(0xFFCC0000),
            testTag = "quick_access_videos"
        ),
        QuickAccessItem(
            id = "duas",
            titleUrdu = "مسنون دعائیں",
            titleHindi = "मसनून दुआएं",
            titleEnglish = "Masnoon Duas",
            titleHinglish = "Masnoon Duayein",
            icon = Icons.Default.Favorite,
            iconBgColor = Color(0xFF00695C),
            testTag = "quick_access_duas"
        ),
        QuickAccessItem(
            id = "tasbeeh",
            titleUrdu = "تسبیح کاؤنٹر",
            titleHindi = "तस्बीह काउंटर",
            titleEnglish = "Tasbeeh Counter",
            titleHinglish = "Tasbeeh Counter",
            icon = Icons.Default.TouchApp,
            iconBgColor = Color(0xFFE65100),
            testTag = "quick_access_tasbeeh"
        ),
        QuickAccessItem(
            id = "calendar",
            titleUrdu = "اسلامی کیلنڈر",
            titleHindi = "इस्लामी कैलेंडर",
            titleEnglish = "Hijri Calendar",
            titleHinglish = "Islamic Calendar",
            icon = Icons.Default.CalendarMonth,
            iconBgColor = Color(0xFF1565C0),
            testTag = "quick_access_calendar"
        ),
        QuickAccessItem(
            id = "website",
            titleUrdu = "ویب سائٹ (qadri.in)",
            titleHindi = "वेबसाइट (qadri.in)",
            titleEnglish = "Website (qadri.in)",
            titleHinglish = "Website (qadri.in)",
            icon = Icons.Default.Language,
            iconBgColor = Color(0xFF00695C),
            testTag = "quick_access_website"
        ),
        QuickAccessItem(
            id = "akabir_badaun",
            titleUrdu = "اکابرین بدایوں",
            titleHindi = "अकाबिर बदायूं",
            titleEnglish = "Akabir Encyclopedia",
            titleHinglish = "Akabir Encyclopedia",
            icon = Icons.Default.AutoStories,
            iconBgColor = EmeraldPrimary,
            testTag = "quick_access_akabir"
        ),
        QuickAccessItem(
            id = "khanqah_ai",
            titleUrdu = "خانقاہ AI رہبر",
            titleHindi = "ख़ानक़ाह AI गाइड",
            titleEnglish = "Khanqah AI Guide",
            titleHinglish = "Khanqah AI Guide",
            icon = Icons.Default.SmartToy,
            iconBgColor = GoldDark,
            testTag = "quick_access_ai"
        ),
        QuickAccessItem(
            id = "hadith_poster",
            titleUrdu = "حدیث و پوسٹر شیئر",
            titleHindi = "हदीस पोस्टर शेयर",
            titleEnglish = "Daily Hadith Poster",
            titleHinglish = "Hadith Poster Share",
            icon = Icons.Default.CardGiftcard,
            iconBgColor = Color(0xFFC2185B),
            testTag = "quick_access_hadith_poster"
        ),
        QuickAccessItem(
            id = "profile",
            titleUrdu = "پروفائل و محفوظات",
            titleHindi = "प्रोफ़ाइल व बुकमार्क",
            titleEnglish = "Profile & Saved",
            titleHinglish = "Profile & Saved",
            icon = Icons.Default.AccountCircle,
            iconBgColor = Color(0xFF4527A0),
            testTag = "quick_access_profile"
        ),
        QuickAccessItem(
            id = "contact",
            titleUrdu = "رابطہ و پتہ",
            titleHindi = "संपर्क व पता",
            titleEnglish = "Contact & Dargah",
            titleHinglish = "Contact & Dargah",
            icon = Icons.Default.ContactPhone,
            iconBgColor = Color(0xFF37474F),
            testTag = "quick_access_contact"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("quick_access_grid")
    ) {
        Text(
            text = when (currentLanguage) {
                AppLanguage.URDU -> "اہم خدمات و رہنمائی"
                AppLanguage.HINDI -> "महत्वपूर्ण सेवाएं व मार्गदर्शन"
                AppLanguage.ENGLISH -> "Key Features & Guidance"
                AppLanguage.HINGLISH -> "Ahem Khidmaat & Guidance"
            },
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldDark,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        // 5 columns or responsive rows of 5 & 5
        val chunked = items.chunked(5)
        chunked.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { item ->
                    QuickAccessItemView(
                        item = item,
                        currentLanguage = currentLanguage,
                        onClick = { onItemClick(item.id) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun QuickAccessItemView(
    item: QuickAccessItem,
    currentLanguage: AppLanguage,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .testTag(item.testTag)
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(item.iconBgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.titleEnglish,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = when (currentLanguage) {
                AppLanguage.URDU -> item.titleUrdu
                AppLanguage.HINDI -> item.titleHindi
                AppLanguage.ENGLISH -> item.titleEnglish
                AppLanguage.HINGLISH -> item.titleHinglish
            },
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = TextCharcoal,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}
