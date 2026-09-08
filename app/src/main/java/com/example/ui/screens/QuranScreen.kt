package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import com.example.data.model.Ayah
import com.example.data.model.Para
import com.example.data.model.Surah
import com.example.data.repository.AppPreferencesRepository
import com.example.data.repository.QuranAudioDownloadRepository
import com.example.data.repository.QuranRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.components.QuranPlayerBar
import com.example.ui.theme.*
import com.example.util.QuranAudioPlayerManager

@Composable
fun QuranScreen(
    isUrdu: Boolean = true,
    lastReadSurah: Int,
    lastReadAyah: Int,
    onSaveLastRead: (Int, Int) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Surahs, 1: Paras, 2: Bookmarks
    var searchQuery by remember { mutableStateOf("") }
    var readingSurah by remember { mutableStateOf<Surah?>(null) }
    val context = LocalContext.current

    val filteredSurahs = remember(searchQuery) {
        QuranRepository.searchQuran(searchQuery)
    }

    val paras = remember { QuranRepository.PARAS_LIST }

    Box(modifier = Modifier.fillMaxSize()) {
        if (readingSurah != null) {
            SurahReaderView(
                surah = readingSurah!!,
                isUrdu = isUrdu,
                onBack = { readingSurah = null },
                onSaveBookmark = { surahNum, ayahNum ->
                    onSaveLastRead(surahNum, ayahNum)
                    Toast.makeText(context, "بک مارک محفوظ کر لیا گیا", Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CreamBg)
                    .testTag("quran_screen_container")
            ) {
            // Search & Tab Bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CreamSurface)
                    .padding(16.dp)
            ) {
                // Khanqah Qadriah Header Branding
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
                            text = if (isUrdu) "القرآن الکریم • خانقاہ قادریہ" else "Holy Quran • Khanqah Qadriah",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Text(
                            text = if (isUrdu) "تلاوت، ترجمہ کنز الایمان و تفاسیر" else "Recitation with Kanzul Iman Translation",
                            fontSize = 11.sp,
                            color = TextCharcoal
                        )
                    }
                }

                // Search Input Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = if (isUrdu) "سورۃ کا نام، نمبر یا معنی تلاش کریں..." else "Search Surah by name or number...",
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("quran_search_field"),
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

                // Tab Row (Surahs / Paras / Bookmarks)
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = EmeraldPrimary,
                    divider = {}
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = {
                            Text(
                                text = if (isUrdu) "سورتیں (۱۱۴)" else "Surahs (114)",
                                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = {
                            Text(
                                text = if (isUrdu) "پارے (۳۰)" else "Paras (30)",
                                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = {
                            Text(
                                text = if (isUrdu) "بک مارک" else "Bookmarks",
                                fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        text = {
                            Text(
                                text = if (isUrdu) "ڈاؤنلوڈز" else "Offline",
                                fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                }
            }

            // Continue Reading Banner (if Surahs tab is active and no search)
            if (selectedTab == 0 && searchQuery.isEmpty()) {
                val lastSurah = QuranRepository.getSurah(lastReadSurah) ?: QuranRepository.SURAHS_LIST[0]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { readingSurah = lastSurah },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = EmeraldPrimary)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.BookmarkBorder,
                                    contentDescription = "Bookmark",
                                    tint = GoldLight,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isUrdu) "تلاوت جاری رکھیں (آخری مقام)" else "Continue Reading",
                                    color = GoldLight,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${lastSurah.urduName} (${lastSurah.arabicName})",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isUrdu) "آیت نمبر: $lastReadAyah" else "Ayah: $lastReadAyah",
                                color = CreamBg.copy(alpha = 0.85f),
                                fontSize = 11.sp
                            )
                        }

                        Button(
                            onClick = { readingSurah = lastSurah },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (isUrdu) "پڑھیں" else "Read",
                                color = EmeraldDark,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // List Content based on Selected Tab
            when (selectedTab) {
                0 -> {
                    // Surahs List
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp, 8.dp, 16.dp, 90.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredSurahs, key = { it.number }) { surah ->
                            SurahCardItem(
                                surah = surah,
                                isUrdu = isUrdu,
                                onClick = {
                                    readingSurah = surah
                                    onSaveLastRead(surah.number, 1)
                                }
                            )
                        }
                    }
                }
                1 -> {
                    // Paras List
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp, 8.dp, 16.dp, 90.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(paras, key = { it.number }) { para ->
                            ParaCardItem(
                                para = para,
                                isUrdu = isUrdu,
                                onClick = {
                                    val surah = QuranRepository.getSurah(para.startSurahNumber)
                                    if (surah != null) {
                                        readingSurah = surah
                                    }
                                }
                            )
                        }
                    }
                }
                2 -> {
                    // Bookmarks View
                    val lastSurah = QuranRepository.getSurah(lastReadSurah) ?: QuranRepository.SURAHS_LIST[0]
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = if (isUrdu) "محفوظ شدہ تلاوت" else "Saved Bookmarks",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )

                        SurahCardItem(
                            surah = lastSurah,
                            isUrdu = isUrdu,
                            onClick = { readingSurah = lastSurah }
                        )
                    }
                }
                3 -> {
                    // Offline Cached Audio List (Room Database)
                    val offlineAudios by QuranAudioDownloadRepository.observeAllCachedAudio(context).collectAsState(initial = emptyList())

                    if (offlineAudios.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CloudDownload,
                                contentDescription = null,
                                tint = GoldDark,
                                modifier = Modifier.size(56.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = if (isUrdu) "کوئی آف لائن تلاوت محفوظ نہیں ہے" else "No Offline Audio Downloaded",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (isUrdu) "سورتوں کی فہرست میں سے کسی بھی سورت کے ڈاؤنلوڈ بٹن پر کلک کر کے آف لائن سننے کے لیے محفوظ فرمائیں۔" else "Download any Surah from the list to listen offline without internet.",
                                fontSize = 12.sp,
                                color = TextMuted,
                                textAlign = TextAlign.Center,
                                lineHeight = 18.sp
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp, 8.dp, 16.dp, 90.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = if (isUrdu) "محفوظ شدہ سورتیں (آف لائن تلاوت)" else "Downloaded Surahs (Offline)",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldDark
                                    )
                                    Text(
                                        text = "${offlineAudios.size} سورتیں",
                                        fontSize = 12.sp,
                                        color = GoldDark,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                            items(offlineAudios, key = { it.surahNumber }) { cached ->
                                val matchedSurah = QuranRepository.getSurah(cached.surahNumber)
                                if (matchedSurah != null) {
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(14.dp),
                                        colors = CardDefaults.cardColors(containerColor = CreamSurface),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                                Box(
                                                    modifier = Modifier.size(38.dp).clip(CircleShape).background(EmeraldSoftBg),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.CheckCircle,
                                                        contentDescription = "Downloaded",
                                                        tint = EmeraldPrimary,
                                                        modifier = Modifier.size(20.dp)
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(12.dp))
                                                Column {
                                                    Text(
                                                        text = matchedSurah.urduName,
                                                        fontSize = 15.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = TextCharcoal
                                                    )
                                                    val sizeMb = ((cached.fileSizeBytes / (1024.0 * 1024.0)) * 10.0).toInt() / 10.0
                                                    Text(
                                                        text = "${matchedSurah.englishName} • $sizeMb MB",
                                                        fontSize = 11.sp,
                                                        color = TextMuted
                                                    )
                                                }
                                            }

                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                IconButton(
                                                    onClick = {
                                                        QuranAudioPlayerManager.togglePlayPause(context, cached.surahNumber)
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.PlayCircleFilled,
                                                        contentDescription = "Play Offline",
                                                        tint = EmeraldPrimary,
                                                        modifier = Modifier.size(28.dp)
                                                    )
                                                }
                                                IconButton(
                                                    onClick = {
                                                        QuranAudioPlayerManager.deleteOfflineSurah(context, cached.surahNumber)
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.DeleteOutline,
                                                        contentDescription = "Delete Offline Cache",
                                                        tint = Color(0xFFC62828),
                                                        modifier = Modifier.size(22.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

        // Floating Quran Player Bar at the bottom
        QuranPlayerBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 76.dp)
        )
    }
}

@Composable
fun SurahCardItem(
    surah: Surah,
    isUrdu: Boolean,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val playerState by QuranAudioPlayerManager.playerState.collectAsState()
    val isPlayingThis = playerState.currentSurahNumber == surah.number && playerState.isPlaying

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Surah Number Badge in Ornate Circle
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(EmeraldSoftBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${surah.number}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldPrimary
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = surah.urduName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                    Text(
                        text = "${surah.englishName} • ${surah.meaningUrdu}",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        QuranAudioPlayerManager.togglePlayPause(context, surah.number)
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (isPlayingThis) Icons.Default.PauseCircleFilled else Icons.Default.PlayCircleOutline,
                        contentDescription = "Play Surah",
                        tint = if (isPlayingThis) EmeraldPrimary else GoldDark,
                        modifier = Modifier.size(28.dp)
                    )
                }

                val isCached = remember(surah.number, playerState.isCachedOffline) {
                    QuranAudioPlayerManager.isSurahCached(context, surah.number, playerState.reciter)
                }

                IconButton(
                    onClick = {
                        QuranAudioPlayerManager.downloadSurahForOffline(context, surah.number)
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (isCached) Icons.Default.CheckCircle else Icons.Default.CloudDownload,
                        contentDescription = "Download Surah Offline",
                        tint = if (isCached) EmeraldPrimary else GoldDark,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = surah.arabicName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                    Text(
                        text = "${surah.revelationType} • ${surah.totalAyahs} آیات",
                        fontSize = 11.sp,
                        color = GoldDark,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun ParaCardItem(
    para: Para,
    isUrdu: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(GoldGlow),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${para.number}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldDark
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = if (isUrdu) "پارہ نمبر ${para.number}" else "Juz ${para.number}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                    Text(
                        text = para.urduName,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }

            Text(
                text = para.arabicName,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldPrimary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahReaderView(
    surah: Surah,
    isUrdu: Boolean,
    onBack: () -> Unit,
    onSaveBookmark: (Int, Int) -> Unit
) {
    val ayahs = remember(surah.number) {
        QuranRepository.getAyahsForSurah(surah.number)
    }
    var arabicFontSize by remember { mutableStateOf(26f) }
    var urduFontSize by remember { mutableStateOf(16f) }
    var showFontDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
    ) {
        // Reader Header
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "${surah.urduName} (${surah.arabicName})",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${surah.revelationType} • ${surah.totalAyahs} آیات • پارہ ${surah.startParaNumber}",
                        fontSize = 11.sp,
                        color = GoldLight
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
                val playerState by QuranAudioPlayerManager.playerState.collectAsState()
                val isPlayingThis = playerState.currentSurahNumber == surah.number && playerState.isPlaying

                IconButton(onClick = { QuranAudioPlayerManager.togglePlayPause(context, surah.number) }) {
                    Icon(
                        imageVector = if (isPlayingThis) Icons.Default.PauseCircleFilled else Icons.Default.PlayCircleOutline,
                        contentDescription = "Audio Recitation",
                        tint = GoldLight
                    )
                }
                IconButton(onClick = { showFontDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.FormatSize,
                        contentDescription = "Font Size",
                        tint = GoldLight
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = EmeraldDark)
        )

        // Ayahs List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Bismillah Header (except for Surah At-Tawbah #9)
            if (surah.number != 9) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamSurface)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                                fontSize = (arabicFontSize + 2).sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "اللہ کے نام سے شروع جو نہایت مہربان رحم فرمانے والا ہے۔ (کنز الایمان)",
                                fontSize = urduFontSize.sp,
                                color = TextMuted,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Ayahs
            items(ayahs, key = { it.ayahNumber }) { ayah ->
                AyahCard(
                    ayah = ayah,
                    arabicFontSize = arabicFontSize,
                    urduFontSize = urduFontSize,
                    onBookmark = {
                        onSaveBookmark(surah.number, ayah.ayahNumber)
                    },
                    onCopy = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Ayah", "${ayah.arabicText}\n${ayah.urduTranslation}")
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "آیت اور ترجمہ کاپی ہو گیا", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }

    if (showFontDialog) {
        AlertDialog(
            onDismissRequest = { showFontDialog = false },
            title = {
                Text(
                    text = "فونٹ سائز تبدیل کریں",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )
            },
            text = {
                Column {
                    Text(text = "عربی فونٹ سائز: ${arabicFontSize.toInt()} pt", fontSize = 13.sp)
                    Slider(
                        value = arabicFontSize,
                        onValueChange = { arabicFontSize = it },
                        valueRange = 20f..38f,
                        colors = SliderDefaults.colors(thumbColor = EmeraldPrimary, activeTrackColor = EmeraldPrimary)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "اردو ترجمہ سائز: ${urduFontSize.toInt()} pt", fontSize = 13.sp)
                    Slider(
                        value = urduFontSize,
                        onValueChange = { urduFontSize = it },
                        valueRange = 13f..24f,
                        colors = SliderDefaults.colors(thumbColor = GoldDark, activeTrackColor = GoldDark)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showFontDialog = false }) {
                    Text("مکمل", color = EmeraldPrimary)
                }
            }
        )
    }
}

@Composable
fun AyahCard(
    ayah: Ayah,
    arabicFontSize: Float,
    urduFontSize: Float,
    onBookmark: () -> Unit,
    onCopy: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Ayah Top Bar: Number and Action Icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ayah Badge
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(EmeraldSoftBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${ayah.ayahNumber}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldPrimary
                    )
                }

                Row {
                    IconButton(onClick = onCopy, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            tint = TextMuted,
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    IconButton(onClick = onBookmark, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.BookmarkAdd,
                            contentDescription = "Bookmark",
                            tint = GoldDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Arabic Text
            Text(
                text = ayah.arabicText,
                fontSize = arabicFontSize.sp,
                fontWeight = FontWeight.SemiBold,
                color = EmeraldDark,
                lineHeight = (arabicFontSize * 1.6f).sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Urdu Translation (Kanzul Iman)
            Text(
                text = ayah.urduTranslation,
                fontSize = urduFontSize.sp,
                color = TextCharcoal,
                lineHeight = (urduFontSize * 1.5f).sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )

            // Transliteration
            if (ayah.transliteration.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = ayah.transliteration,
                    fontSize = (urduFontSize - 3).sp,
                    color = TextMuted,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
