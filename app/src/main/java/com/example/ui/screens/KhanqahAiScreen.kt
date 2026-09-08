package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.SavedContentItem
import com.example.data.repository.UserProfileRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*
import com.example.util.ChatMessage
import com.example.util.KhanqahAiAssistantEngine
import kotlinx.coroutines.launch

@Composable
fun KhanqahAiScreen(
    isUrdu: Boolean = true,
    onBack: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val context = LocalContext.current

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                id = "welcome_msg",
                text = "السلام علیکم ورحمۃ اللہ وبرکاتہ!\nمیں خانقاہِ عالیہ قادریہ بدایوں شریف کا علمی و فکری معاون ہوں۔ آپ خانقاہ کی تاریخ، اکابرین، مسلکِ اہل سنت، اوراد و وظائف یا کتب کے بارے میں کوئی بھی سوال دریافت فرما سکتے ہیں۔",
                isFromUser = false,
                sourceBook = "خانقاہِ عالیہ قادریہ بدایوں شریف"
            )
        )
    }

    fun handleSend(questionText: String) {
        if (questionText.isBlank() || isLoading) return
        val userMsg = ChatMessage(
            id = "user_${System.currentTimeMillis()}",
            text = questionText.trim(),
            isFromUser = true
        )
        messages.add(userMsg)
        inputText = ""
        isLoading = true

        coroutineScope.launch {
            listState.animateScrollToItem(messages.size - 1)
            val response = KhanqahAiAssistantEngine.getAnswer(userMsg.text)
            val assistantMsg = ChatMessage(
                id = "ai_${System.currentTimeMillis()}",
                text = response.answer,
                isFromUser = false,
                sourceBook = response.referenceBook
            )
            messages.add(assistantMsg)
            isLoading = false
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("khanqah_ai_screen")
    ) {
        // Top App Bar
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
            colors = CardDefaults.cardColors(containerColor = EmeraldDark),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    KhanqahOfficialLogo(size = 38.dp, circular = true)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "علمی و روحانی معاون (خانقاہ AI)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Khanqah Qadriah Spiritual Assistant",
                            fontSize = 10.sp,
                            color = GoldLight
                        )
                    }
                }

                IconButton(onClick = {
                    messages.clear()
                    messages.add(
                        ChatMessage(
                            id = "welcome_msg",
                            text = "السلام علیکم ورحمۃ اللہ وبرکاتہ! آپ خانقاہِ قادریہ بدایوں شریف کے متعلق کوئی بھی سوال دریافت فرما سکتے ہیں۔",
                            isFromUser = false,
                            sourceBook = "خانقاہِ عالیہ قادریہ"
                        )
                    )
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Clear Chat", tint = GoldLight)
                }
            }
        }

        // Suggested Questions Carousel
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(KhanqahAiAssistantEngine.PRESET_QUESTIONS) { q ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = EmeraldSoftBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.3f)),
                    modifier = Modifier.clickable { handleSend(q) }
                ) {
                    Text(
                        text = q,
                        fontSize = 11.sp,
                        color = EmeraldDark,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                if (msg.isFromUser) {
                    // User Message Bubble (Right aligned)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Card(
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = EmeraldPrimary),
                            modifier = Modifier.widthIn(max = 280.dp)
                        ) {
                            Text(
                                text = msg.text,
                                fontSize = 13.sp,
                                color = Color.White,
                                modifier = Modifier.padding(12.dp),
                                lineHeight = 18.sp
                            )
                        }
                    }
                } else {
                    // Assistant Message Bubble (Left aligned)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top
                    ) {
                        KhanqahOfficialLogo(size = 32.dp, circular = true)
                        Spacer(modifier = Modifier.width(8.dp))

                        Card(
                            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = CreamSurface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier.widthIn(max = 300.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = msg.text,
                                    fontSize = 13.sp,
                                    color = TextCharcoal,
                                    lineHeight = 20.sp
                                )

                                if (msg.sourceBook != null) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = EmeraldSoftBg
                                    ) {
                                        Text(
                                            text = "حوالہ: ${msg.sourceBook}",
                                            fontSize = 10.sp,
                                            color = EmeraldPrimary,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    // Copy Action
                                    IconButton(
                                        onClick = {
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            clipboard.setPrimaryClip(ClipData.newPlainText("Khanqah AI", msg.text))
                                            Toast.makeText(context, "کاپی ہو گیا", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = TextMuted, modifier = Modifier.size(14.dp))
                                    }

                                    // Bookmark Action
                                    IconButton(
                                        onClick = {
                                            UserProfileRepository.saveContentItem(
                                                SavedContentItem(
                                                    id = "chat_saved_${msg.id}",
                                                    title = "علمی فتویٰ / رہنمائی",
                                                    subtitle = msg.sourceBook ?: "خانقاہِ عالیہ قادریہ",
                                                    type = "AI_FATWA",
                                                    content = msg.text
                                                )
                                            )
                                            Toast.makeText(context, "محفوظ لسٹ میں شامل کر لیا گیا", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(Icons.Default.BookmarkAdd, contentDescription = "Save", tint = GoldDark, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (isLoading) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        KhanqahOfficialLogo(size = 28.dp, circular = true)
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = CreamSurface
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = EmeraldPrimary,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "کتب و ماخذ سے معلومات مرتب ہو رہی ہیں...",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                        }
                    }
                }
            }
        }

        // Bottom Input Row
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 76.dp),
            color = CreamSurface,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(
                            text = "خانقاہ قادریہ یا دینی مسئلہ پوچھیں...",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("ai_input_field"),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = EmeraldPrimary,
                        unfocusedBorderColor = Color(0xFFE0DACB)
                    ),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(8.dp))

                FloatingActionButton(
                    onClick = { handleSend(inputText) },
                    modifier = Modifier
                        .size(44.dp)
                        .testTag("ai_send_button"),
                    containerColor = EmeraldPrimary,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
