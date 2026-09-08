package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.data.model.Book
import com.example.data.model.BookCategory
import com.example.data.repository.BooksRepository
import com.example.data.repository.KhanqahRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*

@Composable
fun BooksScreen(
    currentLanguage: AppLanguage = AppLanguage.URDU,
    authorFilter: String? = null,
    onClearAuthorFilter: () -> Unit = {}
) {
    val isUrdu = currentLanguage == AppLanguage.URDU
    var selectedCategory by remember { mutableStateOf(BookCategory.ALL) }
    var searchQuery by remember { mutableStateOf("") }
    var activeReadingBook by remember { mutableStateOf<Book?>(null) }
    val context = LocalContext.current

    val filteredBooks = remember(selectedCategory, searchQuery, authorFilter) {
        var list = if (authorFilter != null) {
            BooksRepository.getBooksByAuthorId(authorFilter)
        } else {
            BooksRepository.getBooksByCategory(selectedCategory)
        }

        if (searchQuery.isNotBlank()) {
            val q = searchQuery.trim().lowercase()
            list = list.filter {
                it.titleUrdu.contains(q) ||
                it.titleHindi.contains(q) ||
                it.titleEnglish.lowercase().contains(q) ||
                it.titleHinglish.lowercase().contains(q) ||
                it.authorUrdu.contains(q) ||
                it.authorEnglish.lowercase().contains(q) ||
                it.descriptionUrdu.contains(q)
            }
        }
        list
    }

    if (activeReadingBook != null) {
        BookReaderView(
            book = activeReadingBook!!,
            currentLanguage = currentLanguage,
            onBack = { activeReadingBook = null }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CreamBg)
                .testTag("books_screen_container")
        ) {
            // Header Search & Filter Bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CreamSurface)
                    .padding(16.dp)
            ) {
                // Khanqah Qadriah Library Branding
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KhanqahOfficialLogo(
                        size = 42.dp,
                        elevation = 2.dp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isUrdu) "کتب خانہ • خانقاہ قادریہ بدایوں شریف" else "Library • Khanqah Qadriah Badaun",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Text(
                            text = if (isUrdu) "علمی و روحانی کتب، تصانیف اور رسائل" else "Islamic, Spiritual & Research Publications",
                            fontSize = 11.sp,
                            color = TextCharcoal
                        )
                    }
                }

                // Author Filter Notice if active
                if (authorFilter != null) {
                    val authorScholar = KhanqahRepository.getScholarById(authorFilter)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = GoldGlow)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "مصنف: ${authorScholar?.nameUrdu ?: authorFilter}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldDark
                            )
                            TextButton(
                                onClick = onClearAuthorFilter,
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(text = "فلٹر ختم کریں ✕", fontSize = 11.sp, color = EmeraldDark, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.URDU -> "کتاب، مصنف یا موضوع تلاش کریں..."
                                AppLanguage.HINDI -> "किताब, लेखक या विषय खोजें..."
                                AppLanguage.ENGLISH -> "Search books, authors or topics..."
                                AppLanguage.HINGLISH -> "Kitab ya Musannif talash karein..."
                            },
                            fontSize = 13.sp,
                            color = TextMuted
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = EmeraldPrimary
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextMuted)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldPrimary,
                        unfocusedBorderColor = Color(0xFFE0DACB),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Scrollable Category Filter Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BookCategory.values().forEach { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = {
                                if (authorFilter != null) onClearAuthorFilter()
                                selectedCategory = cat
                            },
                            label = {
                                Text(
                                    text = when (currentLanguage) {
                                        AppLanguage.URDU -> cat.urduName
                                        AppLanguage.HINDI -> cat.hindiName
                                        AppLanguage.ENGLISH -> cat.englishName
                                        AppLanguage.HINGLISH -> cat.hinglishName
                                    },
                                    fontSize = 12.sp,
                                    fontWeight = if (selectedCategory == cat) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EmeraldPrimary,
                                selectedLabelColor = Color.White,
                                containerColor = CreamCard,
                                labelColor = TextCharcoal
                            )
                        )
                    }
                }
            }

            // Books List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 90.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (filteredBooks.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = CreamSurface)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(Icons.Default.MenuBook, contentDescription = null, tint = TextMuted, modifier = Modifier.size(36.dp))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "کوئی کتاب نہیں ملی", fontSize = 14.sp, color = TextCharcoal, fontWeight = FontWeight.Bold)
                                Text(text = "برائے مہربانی دیگر کیٹیگری یا مصنف کو منتخب فرمائیں", fontSize = 12.sp, color = TextMuted)
                            }
                        }
                    }
                }

                items(filteredBooks, key = { it.id }) { book ->
                    BookCardItem(
                        book = book,
                        currentLanguage = currentLanguage,
                        onClickRead = { activeReadingBook = book },
                        onOpenOfficialWebsite = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(book.officialWebsiteUrl))
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BookCardItem(
    book: Book,
    currentLanguage: AppLanguage,
    onClickRead: () -> Unit,
    onOpenOfficialWebsite: () -> Unit
) {
    val isUrdu = currentLanguage == AppLanguage.URDU

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickRead() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(EmeraldSoftBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isUrdu) book.category.urduName else book.category.englishName,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldPrimary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoStories,
                        contentDescription = "Chapters",
                        tint = GoldDark,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${book.chapters.size} ابواب",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = when (currentLanguage) {
                    AppLanguage.URDU -> book.titleUrdu
                    AppLanguage.HINDI -> if (book.titleHindi.isNotBlank()) book.titleHindi else book.titleUrdu
                    AppLanguage.ENGLISH -> book.titleEnglish
                    AppLanguage.HINGLISH -> if (book.titleHinglish.isNotBlank()) book.titleHinglish else book.titleEnglish
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "مصنف: " + when (currentLanguage) {
                    AppLanguage.URDU -> book.authorUrdu
                    AppLanguage.HINDI -> if (book.authorHindi.isNotBlank()) book.authorHindi else book.authorUrdu
                    AppLanguage.ENGLISH -> book.authorEnglish
                    AppLanguage.HINGLISH -> if (book.authorHinglish.isNotBlank()) book.authorHinglish else book.authorEnglish
                },
                fontSize = 12.sp,
                color = EmeraldDark,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = when (currentLanguage) {
                    AppLanguage.URDU -> book.descriptionUrdu
                    AppLanguage.HINDI -> if (book.descriptionHindi.isNotBlank()) book.descriptionHindi else book.descriptionUrdu
                    else -> if (book.descriptionEnglish.isNotBlank()) book.descriptionEnglish else book.descriptionUrdu
                },
                fontSize = 12.sp,
                color = TextMuted,
                lineHeight = 18.sp,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = book.publicationInfo,
                    fontSize = 10.sp,
                    color = TextMuted,
                    modifier = Modifier.weight(1f)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(
                        onClick = onOpenOfficialWebsite,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Open qadri.in",
                            tint = GoldDark,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Button(
                        onClick = onClickRead,
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (isUrdu) "مطالعہ کریں" else "Read",
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookReaderView(
    book: Book,
    currentLanguage: AppLanguage,
    onBack: () -> Unit
) {
    var currentChapterIndex by remember { mutableIntStateOf(0) }
    var readerFontSize by remember { mutableFloatStateOf(16f) }
    var showFontDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val currentChapter = book.chapters.getOrNull(currentChapterIndex) ?: book.chapters[0]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
    ) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = book.titleUrdu,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1
                    )
                    Text(
                        text = "باب ${currentChapter.chapterNumber}: ${currentChapter.titleUrdu}",
                        fontSize = 11.sp,
                        color = GoldLight,
                        maxLines = 1
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(book.officialWebsiteUrl))
                    context.startActivity(intent)
                }) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = "qadri.in",
                        tint = GoldLight
                    )
                }
                IconButton(onClick = { showFontDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.FormatSize,
                        contentDescription = "Font",
                        tint = GoldLight
                    )
                }
                IconButton(onClick = {
                    Toast.makeText(context, "صفحہ بک مارک ہو گیا", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        imageVector = Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = EmeraldDark)
        )

        // Chapter Tab Selector
        if (book.chapters.size > 1) {
            ScrollableTabRow(
                selectedTabIndex = currentChapterIndex,
                containerColor = EmeraldPrimary,
                contentColor = Color.White,
                edgePadding = 16.dp
            ) {
                book.chapters.forEachIndexed { index, ch ->
                    Tab(
                        selected = currentChapterIndex == index,
                        onClick = { currentChapterIndex = index },
                        text = {
                            Text(
                                text = "باب ${ch.chapterNumber}: ${ch.titleUrdu}",
                                fontSize = 12.sp,
                                color = if (currentChapterIndex == index) GoldLight else Color.White.copy(alpha = 0.8f)
                            )
                        }
                    )
                }
            }
        }

        // Reader Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 14.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = currentChapter.titleUrdu,
                            fontSize = (readerFontSize + 4).sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Divider(color = GoldLight.copy(alpha = 0.5f))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = currentChapter.contentUrdu,
                            fontSize = readerFontSize.sp,
                            lineHeight = (readerFontSize * 1.8f).sp,
                            color = TextCharcoal
                        )
                    }
                }
            }
        }
    }

    if (showFontDialog) {
        AlertDialog(
            onDismissRequest = { showFontDialog = false },
            title = { Text("فونٹ سائز ایڈجسٹ کریں", color = EmeraldDark, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("سائز: ${readerFontSize.toInt()} pt")
                    Slider(
                        value = readerFontSize,
                        onValueChange = { readerFontSize = it },
                        valueRange = 14f..28f,
                        colors = SliderDefaults.colors(
                            thumbColor = EmeraldPrimary,
                            activeTrackColor = EmeraldPrimary
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showFontDialog = false }) {
                    Text("مکمل", color = EmeraldPrimary, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}
