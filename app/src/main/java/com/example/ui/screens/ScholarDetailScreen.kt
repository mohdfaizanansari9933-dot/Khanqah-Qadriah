package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.ScholarProfile
import com.example.data.repository.KhanqahRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScholarDetailScreen(
    scholar: ScholarProfile,
    currentLanguage: AppLanguage,
    onBack: () -> Unit,
    onViewBooksByAuthor: (String) -> Unit
) {
    val context = LocalContext.current
    val isUrdu = currentLanguage == AppLanguage.URDU

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("scholar_detail_screen")
    ) {
        TopAppBar(
            title = {
                Text(
                    text = when (currentLanguage) {
                        AppLanguage.URDU -> scholar.nameUrdu
                        AppLanguage.HINDI -> scholar.nameHindi
                        AppLanguage.ENGLISH -> scholar.nameEnglish
                        AppLanguage.HINGLISH -> scholar.nameHinglish
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
            // Header Profile Card
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
                            size = 76.dp,
                            circular = true,
                            elevation = 4.dp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.URDU -> scholar.nameUrdu
                                AppLanguage.HINDI -> scholar.nameHindi
                                AppLanguage.ENGLISH -> scholar.nameEnglish
                                AppLanguage.HINGLISH -> scholar.nameHinglish
                            },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )

                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.URDU -> scholar.titleUrdu
                                AppLanguage.HINDI -> scholar.titleHindi
                                else -> scholar.titleEnglish
                            },
                            fontSize = 12.sp,
                            color = GoldDark,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 2.dp)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = EmeraldSoftBg)
                        ) {
                            Text(
                                text = scholar.eraOrDates,
                                fontSize = 11.sp,
                                color = EmeraldPrimary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // Button: Books by this Author
            if (scholar.booksAuthored.isNotEmpty()) {
                item {
                    Button(
                        onClick = { onViewBooksByAuthor(scholar.id) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldDark)
                    ) {
                        Icon(imageVector = Icons.Default.LibraryBooks, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.URDU -> "اس مصنف کی کتب دیکھیں (${scholar.booksAuthored.size})"
                                AppLanguage.HINDI -> "इस लेखक की पुस्तकें देखें"
                                AppLanguage.ENGLISH -> "View Books by this Author (${scholar.booksAuthored.size})"
                                AppLanguage.HINGLISH -> "Is Author ki Kitaben Dekhein"
                            },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            // Detailed Biography
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (isUrdu) "سوانحِ حیات و کوائف" else "Biography & Life",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.URDU -> scholar.biographyUrdu
                                AppLanguage.HINDI -> scholar.biographyHindi
                                AppLanguage.ENGLISH -> scholar.biographyEnglish
                                AppLanguage.HINGLISH -> scholar.biographyHinglish
                            },
                            fontSize = 13.sp,
                            color = TextCharcoal,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            // Spiritual Lineage & Khanqah Role
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (isUrdu) "سلسلۂ ارادت و کردار" else "Spiritual Lineage & Role",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "سلسلہ: ${scholar.spiritualLineage}",
                            fontSize = 12.sp,
                            color = TextCharcoal
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "منصب در خانقاہ: ${scholar.roleUrdu}",
                            fontSize = 12.sp,
                            color = GoldDark,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Scholarly Services
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (isUrdu) "دینی و علمی خدمات" else "Scholarly Services",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (isUrdu) scholar.scholarlyServicesUrdu else scholar.scholarlyServicesEnglish,
                            fontSize = 12.sp,
                            color = TextCharcoal,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Books Authored List
            if (scholar.booksAuthored.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamSurface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (isUrdu) "معروف تصانیف و قلمی یادگاریں" else "Authored Works",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            scholar.booksAuthored.forEachIndexed { idx, bookTitle ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MenuBook,
                                        contentDescription = null,
                                        tint = EmeraldPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "${idx + 1}. $bookTitle",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = TextCharcoal
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Video Lectures
            if (scholar.relatedVideoTitles.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamSurface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.VideoLibrary, contentDescription = null, tint = Color(0xFFCC0000))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isUrdu) "متعلقہ بیانات و ویڈیوز" else "Related Lectures",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            scholar.relatedVideoTitles.forEach { vid ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = CardDefaults.cardColors(containerColor = EmeraldSoftBg)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = vid, fontSize = 12.sp, color = EmeraldDark, modifier = Modifier.weight(1f))
                                        IconButton(onClick = {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_YOUTUBE_URL))
                                            context.startActivity(intent)
                                        }) {
                                            Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color(0xFFCC0000))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // References & Verification
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = GoldGlow)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = GoldDark, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "مستند حوالہ جات و اسناد (Verified)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldDark
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = scholar.references.joinToString(separator = " • "),
                            fontSize = 11.sp,
                            color = TextCharcoal
                        )
                    }
                }
            }
        }
    }
}
