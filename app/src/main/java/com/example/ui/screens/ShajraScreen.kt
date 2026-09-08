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
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import com.example.data.model.ShajraElder
import com.example.data.model.ShajraVerse
import com.example.data.repository.ShajraRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*

@Composable
fun ShajraScreen(
    isUrdu: Boolean = true
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Manzoom Shajra, 1: Chain of Elders (Silsila)
    var searchQuery by remember { mutableStateOf("") }
    var expandedElderStep by remember { mutableStateOf<Int?>(null) }

    val elders = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            ShajraRepository.SHAJRA_ELDERS
        } else {
            val q = searchQuery.trim().lowercase()
            ShajraRepository.SHAJRA_ELDERS.filter {
                it.nameUrdu.contains(q, ignoreCase = true) ||
                it.nameHindi.contains(q, ignoreCase = true) ||
                it.nameEnglish.contains(q, ignoreCase = true) ||
                it.restingPlaceUrdu.contains(q, ignoreCase = true) ||
                it.restingPlaceEnglish.contains(q, ignoreCase = true) ||
                it.titleUrdu.contains(q, ignoreCase = true)
            }
        }
    }

    val verses = remember { ShajraRepository.MANZOOM_SHAJRA_VERSES }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("shajra_screen_container"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 0. Khanqah Header Card
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
                            text = "شجرہ شریف سلسلہ عالیہ قادریہ رزاقیہ",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "خانقاہِ عالیہ قادریہ بدایوں شریف، یو پی، انڈیا",
                            fontSize = 12.sp,
                            color = GoldDark,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    IconButton(
                        onClick = {
                            val fullText = buildString {
                                appendLine("✦ شجرہ شریف سلسلہ عالیہ قادریہ رزاقیہ ✦")
                                appendLine("خانقاہِ عالیہ قادریہ بدایوں شریف")
                                appendLine()
                                verses.forEach {
                                    appendLine(it.arabicUrduText)
                                }
                            }
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, fullText)
                            }
                            context.startActivity(Intent.createChooser(intent, "Share Shajra Shareef"))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Shajra",
                            tint = EmeraldPrimary
                        )
                    }
                }
            }
        }

        // 1. Navigation Tabs (Manzoom vs Silsila Elders)
        item {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = CreamSurface,
                contentColor = EmeraldPrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = GoldPrimary,
                        height = 3.dp
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Text(
                            text = if (isUrdu) "منظوم شجرہ شریف" else "Manzoom Shajra",
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
                            text = if (isUrdu) "اکابرینِ سلسلہ (۱۹)" else "Spiritual Lineage (19)",
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                )
            }
        }

        // 2. Tab Content
        if (selectedTab == 0) {
            // Manzoom Shajra View
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
                                text = "بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِیْمِ",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldLight
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "یا الٰہی رحم فرما مصطفیٰ کے واسطے • یا رسول اللہ کرم کیجے خدا کے واسطے",
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            items(verses) { verse ->
                ManzoomVerseCard(verse = verse, isUrdu = isUrdu)
            }
        } else {
            // Silsila Elders List
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = if (isUrdu) "کسی بھی بزرگ یا مزار کا نام تلاش فرمائیں..." else "Search elders by name or mazar...",
                            fontSize = 12.sp,
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
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldPrimary,
                        unfocusedBorderColor = Color.Black.copy(alpha = 0.1f),
                        focusedContainerColor = CreamSurface,
                        unfocusedContainerColor = CreamSurface
                    ),
                    singleLine = true
                )
            }

            items(elders) { elder ->
                val isExpanded = expandedElderStep == elder.stepNumber
                ShajraElderCard(
                    elder = elder,
                    isExpanded = isExpanded,
                    isUrdu = isUrdu,
                    onToggleExpand = {
                        expandedElderStep = if (isExpanded) null else elder.stepNumber
                    }
                )
            }
        }
    }
}

@Composable
fun ManzoomVerseCard(
    verse: ShajraVerse,
    isUrdu: Boolean
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(GoldGlow),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${verse.id}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldDark
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = verse.arabicUrduText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = verse.transliteration,
                    fontSize = 11.sp,
                    color = TextCharcoal,
                    textAlign = TextAlign.Start
                )
                if (verse.translationUrdu.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = verse.translationUrdu,
                        fontSize = 10.sp,
                        color = TextMuted,
                        textAlign = TextAlign.Start
                    )
                }
            }

            IconButton(
                onClick = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Shajra Verse", "${verse.arabicUrduText}\n${verse.transliteration}")
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "شعر کاپی ہو گیا", Toast.LENGTH_SHORT).show()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy verse",
                    tint = GoldDark,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun ShajraElderCard(
    elder: ShajraElder,
    isExpanded: Boolean,
    isUrdu: Boolean,
    onToggleExpand: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleExpand() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Step Number badge
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (elder.stepNumber in 13..19) GoldGlow else EmeraldSoftBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${elder.stepNumber}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (elder.stepNumber in 13..19) GoldDark else EmeraldPrimary
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = elder.nameUrdu,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Text(
                            text = elder.nameHindi,
                            fontSize = 11.sp,
                            color = TextCharcoal
                        )
                        Text(
                            text = elder.nameEnglish,
                            fontSize = 10.sp,
                            color = TextMuted
                        )
                    }
                }

                IconButton(onClick = onToggleExpand) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Toggle Expand",
                        tint = EmeraldPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mazar / Maqam Row
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = CreamBg,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Mazar",
                        tint = GoldDark,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = elder.restingPlaceUrdu,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextCharcoal
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    HorizontalDivider(color = Color.Black.copy(alpha = 0.06f))
                    Spacer(modifier = Modifier.height(8.dp))

                    // Title
                    Text(
                        text = "لقب و خطابات: ${elder.titleUrdu}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GoldDark
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Spiritual Faiz
                    Text(
                        text = "فیض و احوال: ${elder.spiritualFaizUrdu}",
                        fontSize = 11.sp,
                        color = TextCharcoal,
                        lineHeight = 16.sp
                    )

                    if (elder.verseUrdu.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = GoldGlow.copy(alpha = 0.35f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "شعرِ شجرہ: ${elder.verseUrdu}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                val shareTxt = "${elder.nameUrdu}\n${elder.nameHindi}\nمقامِ وصال: ${elder.restingPlaceUrdu}\nفیضان: ${elder.spiritualFaizUrdu}"
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, shareTxt)
                                }
                                context.startActivity(Intent.createChooser(intent, "Share Elder Profile"))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                modifier = Modifier.size(14.dp),
                                tint = EmeraldPrimary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("شیئر کریں", fontSize = 11.sp, color = EmeraldPrimary)
                        }
                    }
                }
            }
        }
    }
}
