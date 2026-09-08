package com.example.ui.screens

import android.content.Intent
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
import com.example.data.model.Scholar
import com.example.data.repository.KhanqahRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*

@Composable
fun AkabirBadaunScreen(
    isUrdu: Boolean = true,
    onBack: () -> Unit,
    onOpenBook: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedScholar by remember { mutableStateOf<Scholar?>(null) }
    val context = LocalContext.current

    val scholars = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            KhanqahRepository.SCHOLARS_LIST
        } else {
            val query = searchQuery.trim().lowercase()
            KhanqahRepository.SCHOLARS_LIST.filter {
                it.nameUrdu.contains(query, ignoreCase = true) ||
                it.nameEnglish.contains(query, ignoreCase = true) ||
                it.titleUrdu.contains(query, ignoreCase = true) ||
                it.biographyUrdu.contains(query, ignoreCase = true)
            }
        }
    }

    val currentSelected = selectedScholar
    if (currentSelected != null) {
        ScholarDetailDialog(
            scholar = currentSelected,
            isUrdu = isUrdu,
            onDismiss = { selectedScholar = null },
            onOpenBook = { bookId ->
                selectedScholar = null
                onOpenBook(bookId)
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("akabir_badaun_screen")
    ) {
        // Header Bar
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
            colors = CardDefaults.cardColors(containerColor = EmeraldDark),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(EmeraldDark, EmeraldPrimary)
                        )
                    )
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        KhanqahOfficialLogo(size = 38.dp, circular = true)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "انسائیکلوپیڈیا اکابرینِ بدایوں شریف",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Budaun Akabireen Encyclopedia",
                                fontSize = 11.sp,
                                color = GoldLight
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Search Box
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = "بزرگ کا نام، لقب یا کتاب تلاش فرمائیں...",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldPrimary)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = null, tint = TextMuted)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = GoldLight,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }
        }

        // Scholars List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 90.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = EmeraldSoftBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldLight.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "خانقاہِ عالیہ قادریہ بدایوں شریف کے اکابرین نے علم و ادب، تحریکِ آزادی اور عشقِ رسول ﷺ میں لازوال خدمات انجام دیں ہیں۔",
                            fontSize = 11.sp,
                            color = TextCharcoal,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            items(scholars, key = { it.id }) { scholar ->
                ScholarCard(
                    scholar = scholar,
                    onClick = { selectedScholar = scholar }
                )
            }
        }
    }
}

@Composable
fun ScholarCard(
    scholar: Scholar,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("scholar_card_${scholar.id}"),
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(GoldGlow)
                            .border(1.5.dp, GoldLight, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoStories,
                            contentDescription = null,
                            tint = EmeraldDark,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = scholar.nameUrdu,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                        Text(
                            text = scholar.titleUrdu,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = GoldDark
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = EmeraldSoftBg
                ) {
                    Text(
                        text = "${scholar.booksAuthored.size} کتب",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = scholar.biographyUrdu,
                fontSize = 12.sp,
                color = TextCharcoal,
                maxLines = 3,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "سنہ پیدائش و وصال: ${scholar.eraOrDates}",
                    fontSize = 10.sp,
                    color = TextMuted
                )

                Text(
                    text = "تفصیلات و کتب ملاحظہ کریں ←",
                    fontSize = 11.sp,
                    color = EmeraldPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ScholarDetailDialog(
    scholar: Scholar,
    isUrdu: Boolean,
    onDismiss: () -> Unit,
    onOpenBook: (String) -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
            ) {
                Text("بند کریں", color = Color.White)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = {
                    val shareText = buildString {
                        append("✨ ${scholar.nameUrdu} (${scholar.titleUrdu})\n")
                        append("خانقاہِ عالیہ قادریہ بدایوں شریف\n\n")
                        append("${scholar.biographyUrdu}\n\n")
                        append("کتب: ${scholar.booksAuthored.joinToString("، ")}\n")
                        append("مستند معلومات بذریعہ: خانقاہ قادریہ ایپ www.qadri.in")
                    }
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, shareText)
                    }
                    context.startActivity(Intent.createChooser(intent, "سوانح شیئر کریں"))
                }
            ) {
                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("شیئر")
            }
        },
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                KhanqahOfficialLogo(size = 46.dp, circular = true)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = scholar.nameUrdu,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = scholar.titleUrdu,
                    fontSize = 12.sp,
                    color = GoldDark,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Text(
                        text = "تفصیلی سوانح حیات:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                    Text(
                        text = scholar.biographyUrdu,
                        fontSize = 13.sp,
                        color = TextCharcoal,
                        lineHeight = 20.sp
                    )
                }

                if (scholar.booksAuthored.isNotEmpty()) {
                    item {
                        Divider(color = CreamBg)
                        Text(
                            text = "شاہکار تصانیف و قلمی یادگاریں:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                    }

                    items(scholar.booksAuthored) { bookTitle ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = EmeraldSoftBg,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Book, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = bookTitle,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextCharcoal
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        containerColor = CreamSurface,
        shape = RoundedCornerShape(20.dp)
    )
}
