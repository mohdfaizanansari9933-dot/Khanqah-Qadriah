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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.ScholarProfile
import com.example.data.repository.AppPreferencesRepository
import com.example.data.repository.KhanqahRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KhanqahSectionScreen(
    currentLanguage: AppLanguage,
    onBack: () -> Unit,
    onSelectScholar: (ScholarProfile) -> Unit,
    onOpenBooksByAuthor: (String) -> Unit,
    onOpenVideoGallery: () -> Unit,
    onOpenPhotoGallery: () -> Unit,
    onOpenContact: () -> Unit
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val isUrdu = currentLanguage == AppLanguage.URDU

    val tabs = when (currentLanguage) {
        AppLanguage.URDU -> listOf("تعارف و تاریخ", "اکابر و مشائخ", "تذکرۂ تاریخی", "عرس و خدمات")
        AppLanguage.HINDI -> listOf("परिचय व इतिहास", "अकाबिर व मशाईख", "ऐतिहासिक तज़किरा", "उर्स व सेवाएं")
        AppLanguage.ENGLISH -> listOf("Overview", "Scholars", "Chronology", "Urs & Services")
        AppLanguage.HINGLISH -> listOf("Ta'aruf", "Akabir-e-Khanqah", "Tazkira Timeline", "Urs-e-Qadri")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("khanqah_official_section")
    ) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = when (currentLanguage) {
                            AppLanguage.URDU -> "خانقاہ قادریہ بدایوں شریف"
                            AppLanguage.HINDI -> "ख़ानक़ाह क़ादरिया बदायूं शरीफ़"
                            AppLanguage.ENGLISH -> "Khanqah Qadriah Budaun Shareef"
                            AppLanguage.HINGLISH -> "Khanqah Qadriah Budaun Shareef"
                        },
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Official Heritage • qadri.in",
                        fontSize = 11.sp,
                        color = GoldLight
                    )
                }
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
                    Icon(imageVector = Icons.Default.Language, contentDescription = "Official Website", tint = GoldLight)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = EmeraldDark)
        )

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = EmeraldPrimary,
            contentColor = Color.White,
            edgePadding = 12.dp
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == index) GoldLight else Color.White.copy(alpha = 0.8f)
                        )
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            when (selectedTab) {
                0 -> {
                    // TAB 0: OVERVIEW & SPIRITUAL TRADITION
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = CreamSurface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                KhanqahOfficialLogo(
                                    size = 76.dp,
                                    elevation = 6.dp
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "خانقاہِ قادریہ مجیدیہ بدایوں شریف",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark
                                )
                                Text(
                                    text = "Khanqah Qadriah Majeediah, Budaun Shareef, UP",
                                    fontSize = 12.sp,
                                    color = GoldDark,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "خانقاہ قادریہ مجیدیہ بدایوں شریف برصغیر کے تاریخی شہر بدایوں (اتر پردیش، بھارت) میں واقع سلسلہ عالیہ قادریہ کا ایک عظیم اور معتبر روحانی و تعلیمی مرکز ہے۔ یہ مرکز صدیوں سے شریعت و طریقت کی ترویج، دینی کتب کی اشاعت اور تشنگانِ معرفت کی تربیت کا منبع رہا ہے۔\n\nحضور شاہ عبد المجید قادری بدایونی ؒ اور شاہ فضلِ رسول قادری بدایونی ؒ جیسے اکابر نے اس درگاہ و خانقاہ کو علمِ حدیث، فقہ اور تصوف کا گہوارہ بنایا۔",
                                    fontSize = 13.sp,
                                    color = TextCharcoal,
                                    lineHeight = 22.sp,
                                    textAlign = if (isUrdu) TextAlign.Right else TextAlign.Left
                                )
                            }
                        }
                    }

                    // Direct Action Cards: Website, YouTube, Gallery, Contact
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_WEBSITE_URL))
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                            ) {
                                Icon(Icons.Default.Language, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "qadri.in پورٹل", fontSize = 12.sp)
                            }
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_YOUTUBE_URL))
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCC0000))
                            ) {
                                Icon(Icons.Default.PlayCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "یوٹیوب چینل", fontSize = 12.sp)
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = onOpenPhotoGallery,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(16.dp), tint = EmeraldPrimary)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "تصاویرِ خانقاہ", fontSize = 12.sp, color = EmeraldPrimary)
                            }
                            OutlinedButton(
                                onClick = onOpenContact,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.ContactPhone, contentDescription = null, modifier = Modifier.size(16.dp), tint = EmeraldPrimary)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "رابطہ و نقشہ", fontSize = 12.sp, color = EmeraldPrimary)
                            }
                        }
                    }

                    // Spiritual Lineage Card
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = CreamSurface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.AccountTree, contentDescription = null, tint = GoldDark)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "سلسلہ عالیہ اور نسبتِ روحانی",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldDark
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "خانقاہ قادریہ مجیدیہ کا روحانی سلسلہ سیدنا غوثِ اعظم شیخ عبدالقادر جیلانی رضی اللہ عنہ سے جڑتا ہے۔ یہاں قادریت کے ساتھ ساتھ سلسلہ مجددیہ اور برکاتیہ کی برکات بھی جمع ہیں، جس کی بدولت یہ خانقاہ شریعت کی سختی اور تصوف کے لطیف آداب کی جامع ہے۔",
                                    fontSize = 12.sp,
                                    color = TextCharcoal,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }

                1 -> {
                    // TAB 1: ULAMA & SCHOLARS LIST
                    item {
                        Text(
                            text = "اکابرین و مشائخِ خانقاہ قادریہ بدایوں شریف",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    items(KhanqahRepository.SCHOLARS_LIST) { scholar ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectScholar(scholar) },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = CreamSurface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(44.dp)
                                                .clip(CircleShape)
                                                .background(EmeraldSoftBg),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = null,
                                                tint = EmeraldPrimary,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                text = when (currentLanguage) {
                                                    AppLanguage.URDU -> scholar.nameUrdu
                                                    AppLanguage.HINDI -> scholar.nameHindi
                                                    AppLanguage.ENGLISH -> scholar.nameEnglish
                                                    AppLanguage.HINGLISH -> scholar.nameHinglish
                                                },
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = EmeraldDark
                                            )
                                            Text(
                                                text = when (currentLanguage) {
                                                    AppLanguage.URDU -> scholar.titleUrdu
                                                    AppLanguage.HINDI -> scholar.titleHindi
                                                    else -> scholar.titleEnglish
                                                },
                                                fontSize = 11.sp,
                                                color = GoldDark,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = when (currentLanguage) {
                                        AppLanguage.URDU -> scholar.biographyUrdu
                                        AppLanguage.HINDI -> scholar.biographyHindi
                                        AppLanguage.ENGLISH -> scholar.biographyEnglish
                                        AppLanguage.HINGLISH -> scholar.biographyHinglish
                                    },
                                    fontSize = 12.sp,
                                    color = TextCharcoal,
                                    maxLines = 3,
                                    lineHeight = 18.sp
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = scholar.eraOrDates,
                                        fontSize = 11.sp,
                                        color = TextMuted
                                    )
                                    TextButton(
                                        onClick = { onSelectScholar(scholar) },
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Text(text = "مکمل سوانح و کتب ←", fontSize = 11.sp, color = EmeraldPrimary, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // TAB 2: TAZKIRA TIMELINE
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = GoldGlow)
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.MenuBook, contentDescription = null, tint = GoldDark)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "تذکرۂ خانوادۂ قادریہ بدایوں شریف کے تاریخی سنگ میل",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldDark
                                )
                            }
                        }
                    }

                    items(KhanqahRepository.TAZKIRA_TIMELINE) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = CreamSurface)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.year,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldDark
                                    )
                                    Text(
                                        text = item.sourceReference,
                                        fontSize = 10.sp,
                                        color = TextMuted
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = if (isUrdu) item.titleUrdu else item.titleEnglish,
                                    fontSize = 14.sp,
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
                            }
                        }
                    }
                }

                3 -> {
                    // TAB 3: URS & SERVICES
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = CreamSurface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = GoldDark)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "سالانہ عرسِ قادری مبارک بدایوں شریف",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldDark
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "بدایوں شریف میں سالانہ عرسِ قادری نہایت عقیدت و احترام اور شریعت کے مطابق تزک و احتشام سے منایا جاتا ہے۔ اس روحانی محفل میں ملک و بیرونِ ملک سے جید علمائے کرام، مشائخِ عظام، نعت خوان اور ہزاروں زائرین شرکت فرماتے ہیں۔ عرس کے دوران لنگرِ عام، قرات و نعت کے مقابلے اور اصلاحی نشستیں منعقد ہوتی ہیں۔",
                                    fontSize = 12.sp,
                                    color = TextCharcoal,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = CreamSurface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "خانقاہ کے اہم شعبہ جات",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "• مکتبہ قادریہ (اشاعتِ کتب و رسائل)\n• دار الافتاء (شرعی مسائل و رہنمائی)\n• شعبہ حفظ و ناظرہ قرآن مجید\n• لنگر خانہ حضرت شاہ عبد المجید قادری ؒ (روزانہ طعامِ مساکین)\n• شعبہ ڈیجیٹل میڈیا و آرکائیو (qadri.in)",
                                    fontSize = 12.sp,
                                    color = TextCharcoal,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
