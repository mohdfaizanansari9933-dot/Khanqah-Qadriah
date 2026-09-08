package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.BooksRepository
import com.example.data.repository.DuasRepository
import com.example.data.repository.IslamicCalendarRepository
import com.example.data.repository.KnowledgeRepository
import com.example.data.repository.QuranRepository
import com.example.ui.theme.*

data class SearchResultItem(
    val title: String,
    val subtitle: String,
    val category: String,
    val categoryColor: Color,
    val destination: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalSearchScreen(
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    isUrdu: Boolean = true
) {
    var query by remember { mutableStateOf("") }

    val results = remember(query) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) {
            emptyList()
        } else {
            val list = mutableListOf<SearchResultItem>()

            // 1. Search Quran Surahs
            QuranRepository.SURAHS_LIST.filter {
                it.urduName.contains(q) || it.arabicName.contains(q) || it.meaningUrdu.contains(q) || it.englishName.lowercase().contains(q)
            }.take(5).forEach {
                list.add(
                    SearchResultItem(
                        title = "${it.urduName} (${it.arabicName})",
                        subtitle = "پارہ ${it.startParaNumber} • ${it.totalAyahs} آیات • ${it.meaningUrdu}",
                        category = "قرآن پاک",
                        categoryColor = EmeraldPrimary,
                        destination = "quran"
                    )
                )
            }

            // 2. Search Books
            BooksRepository.BOOKS_LIST.filter {
                it.titleUrdu.contains(q) || it.authorUrdu.contains(q) || it.descriptionUrdu.contains(q)
            }.take(5).forEach {
                list.add(
                    SearchResultItem(
                        title = it.titleUrdu,
                        subtitle = "مصنف: ${it.authorUrdu} • ${it.category.urduName}",
                        category = "کتاب",
                        categoryColor = Color(0xFF7A4B1A),
                        destination = "books"
                    )
                )
            }

            // 3. Search Duas
            DuasRepository.DUAS_LIST.filter {
                it.titleUrdu.contains(q) || it.urduTranslation.contains(q) || it.arabicText.contains(q)
            }.take(5).forEach {
                list.add(
                    SearchResultItem(
                        title = it.titleUrdu,
                        subtitle = "${it.urduTranslation.take(60)}... (${it.reference})",
                        category = "دعا",
                        categoryColor = Color(0xFF5E35B1),
                        destination = "duas"
                    )
                )
            }

            // 4. Search Masail
            KnowledgeRepository.MASAIL_QA.filter {
                it.questionUrdu.contains(q) || it.answerUrdu.contains(q)
            }.take(4).forEach {
                list.add(
                    SearchResultItem(
                        title = it.questionUrdu,
                        subtitle = "${it.answerUrdu.take(60)}... (${it.referenceUrdu})",
                        category = "مسئلہ",
                        categoryColor = EmeraldDark,
                        destination = "knowledge"
                    )
                )
            }

            // 5. Search Calendar Events
            IslamicCalendarRepository.AHLE_SUNNAT_EVENTS.filter {
                it.titleUrdu.contains(q) || it.descriptionUrdu.contains(q)
            }.take(3).forEach {
                list.add(
                    SearchResultItem(
                        title = it.titleUrdu,
                        subtitle = "${it.hijriDay} ${it.hijriMonthUrdu} • ${it.descriptionUrdu.take(50)}...",
                        category = "کیلنڈر",
                        categoryColor = GoldDark,
                        destination = "calendar"
                    )
                )
            }

            list
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("global_search_screen")
    ) {
        TopAppBar(
            title = {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text(if (isUrdu) "پورے ایپ میں تلاش کریں..." else "Search across entire app...", fontSize = 14.sp) },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { query = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = EmeraldPrimary
                    ),
                    singleLine = true
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = EmeraldDark)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = CreamSurface)
        )

        if (query.isBlank()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = TextMuted.copy(alpha = 0.5f),
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = if (isUrdu) "قرآن، احادیث، کتابیں، دعائیں اور مسائل تلاش کریں" else "Search Quran, Books, Duas, Hadith & Masail",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                }
            }
        } else if (results.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isUrdu) "کوئی نتیجہ نہیں ملا" else "No results found",
                    fontSize = 14.sp,
                    color = TextMuted
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(results) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigate(item.destination) },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(item.categoryColor.copy(alpha = 0.15f))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = item.category,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = item.categoryColor
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextCharcoal
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.subtitle,
                                    fontSize = 11.sp,
                                    color = TextMuted,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
