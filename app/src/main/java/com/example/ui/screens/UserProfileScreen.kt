package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.KhanqahRepository
import com.example.data.repository.ReminderPreferences
import com.example.data.repository.UserProfileRepository
import com.example.ui.components.AuthModal
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*

@Composable
fun UserProfileScreen(
    isUrdu: Boolean = true,
    onBack: () -> Unit
) {
    val currentUser by UserProfileRepository.currentUser.collectAsState()
    val savedItems by UserProfileRepository.savedItems.collectAsState()
    val reminders by UserProfileRepository.reminders.collectAsState()
    var selectedTab by remember { mutableStateOf(0) } // 0: Saved Content, 1: Reminders, 2: Khanqah Info
    var showAuthModal by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showAuthModal) {
        AuthModal(
            onDismiss = { showAuthModal = false },
            onSuccess = { showAuthModal = false }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("user_profile_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header Card with Profile Info
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = EmeraldDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                listOf(EmeraldDark, EmeraldPrimary)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = onBack) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }
                            KhanqahOfficialLogo(
                                size = 44.dp,
                                circular = true,
                                elevation = 2.dp
                            )
                            IconButton(onClick = { showAuthModal = true }) {
                                Icon(
                                    imageVector = if (currentUser?.isGuest == true) Icons.Default.Login else Icons.Default.Sync,
                                    contentDescription = "Auth",
                                    tint = GoldLight
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // User Avatar
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(GoldGlow)
                                .border(2.dp, GoldLight, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (currentUser?.isGuest == true) Icons.Default.PersonOutline else Icons.Default.Person,
                                contentDescription = null,
                                tint = EmeraldDark,
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = currentUser?.name ?: "زائرِ خانقاہ (مہمان)",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = currentUser?.email ?: "guest@khanqahqadriah.in",
                            fontSize = 12.sp,
                            color = GoldLight
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Status Badge
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = GoldDark.copy(alpha = 0.25f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldLight.copy(alpha = 0.6f))
                        ) {
                            Text(
                                text = if (currentUser?.isGuest == true) "مہمان اکاؤنٹ • لاگ ان کریں" else "معتمدِ خانقاہ قادریہ بدایوں شریف",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = GoldLight,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Action Button
                        if (currentUser?.isGuest == true) {
                            Button(
                                onClick = { showAuthModal = true },
                                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = EmeraldDark)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "مکمل پروفائل کے لیے لاگ ان کریں",
                                    color = EmeraldDark,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            OutlinedButton(
                                onClick = {
                                    UserProfileRepository.logout()
                                    Toast.makeText(context, "لاگ آؤٹ ہو گیا", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                            ) {
                                Text(text = "لاگ آؤٹ (Sign Out)", color = Color.White, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }

        // Tab Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CreamSurface)
                    .padding(4.dp)
            ) {
                listOf("محفوظ شدہ مواد", "روزانہ یاد دہانیاں", "خانقاہ رابطہ").forEachIndexed { index, title ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (selectedTab == index) EmeraldPrimary else Color.Transparent)
                            .clickable { selectedTab = index }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedTab == index) Color.White else TextCharcoal
                        )
                    }
                }
            }
        }

        // Tab Content
        when (selectedTab) {
            0 -> {
                // Bookmarks & Saved Content
                if (savedItems.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = CreamSurface)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.BookmarkBorder,
                                    contentDescription = null,
                                    tint = TextMuted,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "ابھی کوئی مواد محفوظ نہیں ہے",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextCharcoal
                                )
                                Text(
                                    text = "روزانہ حدیث، کتب یا اشعار کے کارڈز سے مواد محفوظ فرمائیں۔",
                                    fontSize = 11.sp,
                                    color = TextMuted,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    items(savedItems, key = { it.id }) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = CreamSurface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp)
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
                                            text = item.type,
                                            fontSize = 10.sp,
                                            color = EmeraldPrimary,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    Row {
                                        IconButton(
                                            onClick = {
                                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                                val clip = ClipData.newPlainText("Saved Content", "${item.title}\n${item.content}")
                                                clipboard.setPrimaryClip(clip)
                                                Toast.makeText(context, "کاپی ہو گیا", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = TextMuted, modifier = Modifier.size(16.dp))
                                        }

                                        IconButton(
                                            onClick = {
                                                UserProfileRepository.removeContentItem(item.id)
                                                Toast.makeText(context, "محفوظ لسٹ سے ہٹا دیا گیا", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.7f), modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = item.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark
                                )

                                Text(
                                    text = item.subtitle,
                                    fontSize = 11.sp,
                                    color = GoldDark
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = item.content,
                                    fontSize = 13.sp,
                                    color = TextCharcoal,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            }

            1 -> {
                // Daily Reminder Preferences
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
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "اوقاتِ نماز و اذان کی یاد دہانیاں",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )

                            ReminderRow(
                                title = "فجر یاد دہانی",
                                subtitle = "نمازِ فجر و سحر الرٹ",
                                checked = reminders.fajrReminder,
                                onCheckedChange = { UserProfileRepository.updateReminders(reminders.copy(fajrReminder = it)) }
                            )

                            ReminderRow(
                                title = "ظہر یاد دہانی",
                                subtitle = "نمازِ ظہر الرٹ",
                                checked = reminders.dhuhrReminder,
                                onCheckedChange = { UserProfileRepository.updateReminders(reminders.copy(dhuhrReminder = it)) }
                            )

                            ReminderRow(
                                title = "عصر یاد دہانی",
                                subtitle = "نمازِ عصر الرٹ (حنفی وقت)",
                                checked = reminders.asrReminder,
                                onCheckedChange = { UserProfileRepository.updateReminders(reminders.copy(asrReminder = it)) }
                            )

                            ReminderRow(
                                title = "مغرب یاد دہانی",
                                subtitle = "نمازِ مغرب و افطار الرٹ",
                                checked = reminders.maghribReminder,
                                onCheckedChange = { UserProfileRepository.updateReminders(reminders.copy(maghribReminder = it)) }
                            )

                            ReminderRow(
                                title = "عشاء یاد دہانی",
                                subtitle = "نمازِ عشاء الرٹ",
                                checked = reminders.ishaReminder,
                                onCheckedChange = { UserProfileRepository.updateReminders(reminders.copy(ishaReminder = it)) }
                            )

                            Divider(modifier = Modifier.padding(vertical = 4.dp), color = CreamBg)

                            Text(
                                text = "روحانی پیغامات و اعراسِ مبارکہ",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )

                            ReminderRow(
                                title = "روزانہ حدیث و 'ایک اچھی بات'",
                                subtitle = "صبح کا ہندی روحانی پوسٹر نوٹیفکیشن",
                                checked = reminders.dailyHadithReminder,
                                onCheckedChange = { UserProfileRepository.updateReminders(reminders.copy(dailyHadithReminder = it)) }
                            )

                            ReminderRow(
                                title = "عرسِ مبارک الرٹس",
                                subtitle = "عرسِ قادری دولہا و اکابرین بدایوں شریف",
                                checked = reminders.ursAlerts,
                                onCheckedChange = { UserProfileRepository.updateReminders(reminders.copy(ursAlerts = it)) }
                            )
                        }
                    }
                }
            }

            2 -> {
                // Official Khanqah Contact & Info
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
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Text(
                                text = "خانقاہِ عالیہ قادریہ بدایوں شریف کا باضابطہ رابطہ",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )

                            // Sahib-e-Sajjada official Info
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = EmeraldSoftBg,
                                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.3f))
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "صاحبِ سجادہ و مسند نشین:",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldDark
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "جانشین حضور تاجدارِ اہل سنت حضرت علامہ مولانا شیخ عبد الغنی محمد عاطف قادری ازہری عشقی بدایونی، صاحبِ سجادہ خانقاہِ عالیہ قادریہ، بدایوں شریف (یو پی، بھارت)",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextCharcoal,
                                        lineHeight = 18.sp
                                    )
                                }
                            }

                            // Address Row
                            Row(verticalAlignment = Alignment.Top) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(text = "مرکزی پتہ:", fontSize = 11.sp, color = TextMuted)
                                    Text(text = KhanqahRepository.OFFICIAL_ADDRESS_URDU, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextCharcoal)
                                    Text(text = KhanqahRepository.OFFICIAL_ADDRESS_ENGLISH, fontSize = 11.sp, color = TextMuted)
                                }
                            }

                            // WhatsApp Integration Button
                            Button(
                                onClick = {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_WHATSAPP_URL))
                                        context.startActivity(intent)
                                    } catch (_: Exception) {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        clipboard.setPrimaryClip(ClipData.newPlainText("Khanqah WhatsApp", KhanqahRepository.OFFICIAL_WHATSAPP_PHONE))
                                        Toast.makeText(context, "نمبر کاپی ہو گیا: ${KhanqahRepository.OFFICIAL_WHATSAPP_PHONE}", Toast.LENGTH_LONG).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366))
                            ) {
                                Icon(Icons.Default.Chat, contentDescription = null, tint = Color.White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "واٹس ایپ پر رابطہ کریں (${KhanqahRepository.OFFICIAL_CONTACT_PHONE})",
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Website & YouTube links
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_WEBSITE_URL))
                                        context.startActivity(intent)
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.Language, contentDescription = null, tint = EmeraldDark, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "ویب سائٹ", fontSize = 12.sp, color = EmeraldDark)
                                }

                                OutlinedButton(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(KhanqahRepository.OFFICIAL_YOUTUBE_URL))
                                        context.startActivity(intent)
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.Red, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "یوٹیوب", fontSize = 12.sp, color = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReminderRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
            Text(text = subtitle, fontSize = 11.sp, color = TextMuted)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = EmeraldPrimary
            )
        )
    }
}
