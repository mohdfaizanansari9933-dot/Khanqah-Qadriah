package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Dua
import com.example.data.model.DuaCategory
import com.example.data.repository.DuasRepository
import com.example.ui.theme.*

@Composable
fun DuasScreen(
    isUrdu: Boolean = true
) {
    var selectedCategory by remember { mutableStateOf<DuaCategory?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current

    val filteredDuas = remember(selectedCategory, searchQuery) {
        val base = if (selectedCategory == null) {
            DuasRepository.DUAS_LIST
        } else {
            DuasRepository.getDuasByCategory(selectedCategory!!)
        }
        if (searchQuery.isBlank()) {
            base
        } else {
            val q = searchQuery.trim().lowercase()
            base.filter {
                it.titleUrdu.contains(q) ||
                it.arabicText.contains(q) ||
                it.urduTranslation.contains(q) ||
                it.reference.contains(q)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("duas_screen_container")
    ) {
        // Search & Category Chips Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CreamSurface)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = if (isUrdu) "دعا، موقع یا حوالہ تلاش کریں..." else "Search dua or situation...",
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

            // Scrollable Category Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { selectedCategory = null },
                    label = { Text("تمام دعائیں", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EmeraldPrimary,
                        selectedLabelColor = Color.White,
                        containerColor = CreamCard,
                        labelColor = TextCharcoal
                    )
                )

                DuaCategory.values().forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = {
                            Text(
                                text = if (isUrdu) cat.urduName else cat.englishName,
                                fontSize = 12.sp
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

        // Duas List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 90.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(filteredDuas, key = { it.id }) { dua ->
                DuaCardItem(
                    dua = dua,
                    isUrdu = isUrdu,
                    onCopy = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Dua", "${dua.titleUrdu}\n\n${dua.arabicText}\n\n${dua.urduTranslation}\n(حوالہ: ${dua.reference})")
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "دعا کاپی ہو گئی", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
fun DuaCardItem(
    dua: Dua,
    isUrdu: Boolean,
    onCopy: () -> Unit
) {
    var readCount by remember { mutableIntStateOf(0) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Category Badge & Title & Copy Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(EmeraldSoftBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isUrdu) dua.category.urduName else dua.category.englishName,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldPrimary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onCopy, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            tint = TextMuted,
                            modifier = Modifier.size(17.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = dua.titleUrdu,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Arabic Text
            Text(
                text = dua.arabicText,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = EmeraldDark,
                lineHeight = 32.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Urdu Translation
            Text(
                text = dua.urduTranslation,
                fontSize = 14.sp,
                color = TextCharcoal,
                lineHeight = 22.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )

            // Transliteration
            if (dua.transliteration.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = dua.transliteration,
                    fontSize = 12.sp,
                    color = TextMuted,
                    lineHeight = 16.sp
                )
            }

            // Benefits / Virtue
            if (dua.benefitsUrdu != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(GoldGlow)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "فضیلت: ${dua.benefitsUrdu}",
                        fontSize = 11.sp,
                        color = GoldDark,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Footer: Reference & Interactive Repeat Counter
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dua.reference,
                    fontSize = 11.sp,
                    color = TextMuted
                )

                // Recommended repetition button
                Button(
                    onClick = {
                        if (readCount < dua.recommendedCount) {
                            readCount++
                        } else {
                            readCount = 0
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (readCount >= dua.recommendedCount) GoldDark else EmeraldPrimary
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (readCount >= dua.recommendedCount) "مکمل ($readCount/${dua.recommendedCount})" else "پڑھیں ($readCount/${dua.recommendedCount})",
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
