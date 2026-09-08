package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.data.model.SalamItem
import com.example.data.model.SalamVerse
import com.example.data.repository.SalamRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*

@Composable
fun SalamScreen(
    isUrdu: Boolean = true
) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf("سب") }
    var selectedItem by remember { mutableStateOf<SalamItem?>(null) }

    val categories = listOf("سب", "سلام", "قصیدہ", "منقبت", "درود")

    val filteredItems = remember(selectedCategory) {
        if (selectedCategory == "سب") {
            SalamRepository.ITEMS
        } else {
            SalamRepository.ITEMS.filter { it.category == selectedCategory }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (selectedItem != null) {
            SalamDetailViewer(
                item = selectedItem!!,
                isUrdu = isUrdu,
                onBack = { selectedItem = null }
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CreamBg)
                    .testTag("salam_screen_container"),
                contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // 0. Official Khanqah Header
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            KhanqahOfficialLogo(
                                size = 48.dp,
                                circular = true,
                                elevation = 2.dp
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "سلام و درود و مناقبِ عالیہ",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "خانقاہِ قادریہ بدایوں شریف سے منظور شدہ کلام",
                                    fontSize = 12.sp,
                                    color = GoldDark,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // 1. Category Filter Tabs
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.forEach { cat ->
                            val isSelected = selectedCategory == cat
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedCategory = cat },
                                label = {
                                    Text(
                                        text = cat,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = EmeraldPrimary,
                                    selectedLabelColor = Color.White,
                                    containerColor = CreamSurface,
                                    labelColor = TextCharcoal
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                    }
                }

                // 2. Salam Items List
                items(filteredItems, key = { it.id }) { item ->
                    SalamCardItem(
                        item = item,
                        isUrdu = isUrdu,
                        onClick = { selectedItem = item }
                    )
                }
            }
        }
    }
}

@Composable
fun SalamCardItem(
    item: SalamItem,
    isUrdu: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
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
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = EmeraldSoftBg
                ) {
                    Text(
                        text = item.category,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Text(
                    text = "${item.verses.size} اشعار",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.titleUrdu,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldDark
            )

            Text(
                text = item.titleEnglish,
                fontSize = 11.sp,
                color = TextCharcoal
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.poetUrdu,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = GoldDark
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.descriptionUrdu,
                fontSize = 11.sp,
                color = TextMuted,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun SalamDetailViewer(
    item: SalamItem,
    isUrdu: Boolean,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
    ) {
        // Top App Bar
        @OptIn(ExperimentalMaterial3Api::class)
        TopAppBar(
            title = {
                Text(
                    text = item.titleUrdu,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = EmeraldDark
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        val fullKalam = buildString {
                            appendLine("✦ ${item.titleUrdu} ✦")
                            appendLine(item.poetUrdu)
                            appendLine("خانقاہِ عالیہ قادریہ بدایوں شریف")
                            appendLine()
                            item.verses.forEach {
                                appendLine(it.urduText)
                                appendLine()
                            }
                        }
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, fullKalam)
                        }
                        context.startActivity(Intent.createChooser(intent, "Share Kalam"))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = EmeraldDark
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = CreamSurface)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 90.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = EmeraldDark)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    listOf(EmeraldDark, Color(0xFF071F16))
                                )
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = item.titleUrdu,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldLight,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.poetUrdu,
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Verses List
            items(item.verses) { verse ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = verse.urduText,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark,
                            textAlign = TextAlign.Center,
                            lineHeight = 26.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = verse.transliteration,
                            fontSize = 12.sp,
                            color = TextCharcoal,
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp
                        )

                        if (verse.englishMeaning.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = verse.englishMeaning,
                                fontSize = 11.sp,
                                color = TextMuted,
                                textAlign = TextAlign.Center,
                                lineHeight = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        IconButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Verse", "${verse.urduText}\n${verse.transliteration}")
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "شعر کاپی ہو گیا", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy",
                                tint = GoldDark,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
