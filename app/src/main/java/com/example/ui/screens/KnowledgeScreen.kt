package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DailyHadith
import com.example.data.model.IslamicArticle
import com.example.data.model.MasalahQA
import com.example.data.repository.KnowledgeRepository
import com.example.ui.theme.*

@Composable
fun KnowledgeScreen(
    isUrdu: Boolean = true
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Masail, 1: Articles, 2: Hadiths
    var readingArticle by remember { mutableStateOf<IslamicArticle?>(null) }

    if (readingArticle != null) {
        ArticleReaderView(
            article = readingArticle!!,
            isUrdu = isUrdu,
            onBack = { readingArticle = null }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CreamBg)
                .testTag("knowledge_screen_container")
        ) {
            // Tab Selector
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = CreamSurface,
                contentColor = EmeraldPrimary
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Text(
                            text = if (isUrdu) "مسائلِ شرعیہ (حنفی)" else "Fiqh Masail",
                            fontSize = 13.sp,
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Text(
                            text = if (isUrdu) "مضامین و سوانح" else "Articles & Seerat",
                            fontSize = 13.sp,
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = {
                        Text(
                            text = if (isUrdu) "احادیثِ مبارکہ" else "Hadith Collection",
                            fontSize = 13.sp,
                            fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }

            // Tab Content
            when (selectedTab) {
                0 -> {
                    // Masail Q&A List
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(KnowledgeRepository.MASAIL_QA, key = { it.id }) { item ->
                            MasalahCard(item = item, isUrdu = isUrdu)
                        }
                    }
                }
                1 -> {
                    // Articles List
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(KnowledgeRepository.ARTICLES_LIST, key = { it.id }) { article ->
                            ArticleSummaryCard(
                                article = article,
                                isUrdu = isUrdu,
                                onClick = { readingArticle = article }
                            )
                        }
                    }
                }
                2 -> {
                    // Hadiths List
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(KnowledgeRepository.DAILY_HADITHS, key = { it.id }) { hadith ->
                            FullHadithCard(hadith = hadith, isUrdu = isUrdu)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MasalahCard(item: MasalahQA, isUrdu: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(EmeraldSoftBg)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(text = item.topicUrdu, fontSize = 11.sp, color = EmeraldPrimary, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "سوال: ${item.questionUrdu}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "الجواب: ${item.answerUrdu}",
                fontSize = 13.sp,
                color = EmeraldDark,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "حوالہ: ${item.referenceUrdu}",
                fontSize = 11.sp,
                color = TextMuted
            )
        }
    }
}

@Composable
fun ArticleSummaryCard(article: IslamicArticle, isUrdu: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(GoldGlow)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(text = article.categoryUrdu, fontSize = 11.sp, color = GoldDark, fontWeight = FontWeight.Bold)
                }

                Text(text = article.dateHijri, fontSize = 11.sp, color = TextMuted)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = article.titleUrdu,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = article.summaryUrdu,
                fontSize = 12.sp,
                color = TextMuted,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "قلم: ${article.authorUrdu}", fontSize = 11.sp, color = EmeraldDark)
                Text(text = "مزید پڑھیں ←", fontSize = 12.sp, color = EmeraldPrimary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun FullHadithCard(hadith: DailyHadith, isUrdu: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = hadith.sourceReference,
                fontSize = 12.sp,
                color = GoldDark,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = hadith.textArabic,
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                color = EmeraldDark,
                lineHeight = 30.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = hadith.translationUrdu,
                fontSize = 14.sp,
                color = TextCharcoal,
                lineHeight = 22.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "راوی: ${hadith.narratorUrdu}",
                fontSize = 11.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(EmeraldSoftBg)
                    .padding(8.dp)
            ) {
                Text(
                    text = "سبق: ${hadith.lessonUrdu}",
                    fontSize = 11.sp,
                    color = EmeraldDark,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleReaderView(article: IslamicArticle, isUrdu: Boolean, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = article.titleUrdu,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = EmeraldDark)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(18.dp)) {
                        Text(
                            text = article.titleUrdu,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark,
                            lineHeight = 30.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "قلم: ${article.authorUrdu}", fontSize = 12.sp, color = GoldDark)
                            Text(text = article.dateHijri, fontSize = 12.sp, color = TextMuted)
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE0DACB))

                        Text(
                            text = article.contentUrdu,
                            fontSize = 15.sp,
                            color = TextCharcoal,
                            lineHeight = 26.sp
                        )
                    }
                }
            }
        }
    }
}
