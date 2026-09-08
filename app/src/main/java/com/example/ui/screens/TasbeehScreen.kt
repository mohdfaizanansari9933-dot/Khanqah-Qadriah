package com.example.ui.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Vibration
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
import com.example.data.model.DhikrItem
import com.example.data.repository.AppPreferencesRepository
import com.example.ui.theme.*

@Composable
fun TasbeehScreen(
    isUrdu: Boolean = true
) {
    var selectedDhikr by remember { mutableStateOf(AppPreferencesRepository.PRESET_DHIKRS[0]) }
    var currentCount by remember { mutableIntStateOf(0) }
    var targetCount by remember { mutableIntStateOf(selectedDhikr.targetCount) }
    var totalSessionCount by remember { mutableIntStateOf(0) }
    var isVibrationEnabled by remember { mutableStateOf(true) }
    var showResetDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    fun vibrate() {
        if (!isVibrationEnabled) return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(
                    VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                vibrator?.vibrate(35)
            }
        } catch (_: Exception) {}
    }

    fun onIncrement() {
        vibrate()
        currentCount++
        totalSessionCount++
        AppPreferencesRepository.incrementTodayTasbeeh()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .padding(16.dp)
            .testTag("tasbeeh_screen_container"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Dhikr Presets Horizontal Selector
        Column {
            Text(
                text = if (isUrdu) "منتخب مسنون ذکر و وظیفہ" else "Select Dhikr",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 6.dp)
            ) {
                items(AppPreferencesRepository.PRESET_DHIKRS) { dhikr ->
                    val isSelected = dhikr.id == selectedDhikr.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) EmeraldPrimary else CreamSurface)
                            .clickable {
                                selectedDhikr = dhikr
                                targetCount = dhikr.targetCount
                                currentCount = 0
                            }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = dhikr.nameUrdu,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else TextCharcoal
                        )
                    }
                }
            }
        }

        // Dhikr Arabic Card & Virtue
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = CreamSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = selectedDhikr.arabicText,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = selectedDhikr.meaningUrdu,
                    fontSize = 13.sp,
                    color = TextCharcoal,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(GoldGlow)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "فضیلت: ${selectedDhikr.virtueUrdu}",
                        fontSize = 11.sp,
                        color = GoldDark,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Large Circular Digital Counter Tap Button
        Box(
            modifier = Modifier
                .size(240.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(EmeraldLight, EmeraldPrimary, EmeraldDark)
                    )
                )
                .clickable { onIncrement() }
                .testTag("tasbeeh_count_button"),
            contentAlignment = Alignment.Center
        ) {
            // Inner decorative ring
            Box(
                modifier = Modifier
                    .size(210.dp)
                    .clip(CircleShape)
                    .background(EmeraldDark),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$currentCount",
                        fontSize = 54.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = GoldLight
                    )

                    Text(
                        text = "ہدف: $targetCount",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CreamBg.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "کل تسبیح: $totalSessionCount",
                        fontSize = 11.sp,
                        color = GoldLight.copy(alpha = 0.7f)
                    )
                }
            }
        }

        // Target Selectors (33 / 100 / 1000 / Free)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(33, 100, 1000, 0).forEach { target ->
                val label = if (target == 0) "آزاد" else "$target"
                val isSelected = targetCount == target
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) GoldDark else CreamSurface)
                        .clickable {
                            targetCount = target
                            currentCount = 0
                        }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else TextCharcoal
                    )
                }
            }
        }

        // Bottom Controls: Reset & Vibration Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 75.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { showResetDialog = true },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFC62828)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset")
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = if (isUrdu) "صفر کریں" else "Reset")
            }

            IconButton(
                onClick = { isVibrationEnabled = !isVibrationEnabled },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (isVibrationEnabled) EmeraldSoftBg else CreamCard)
            ) {
                Icon(
                    imageVector = Icons.Default.Vibration,
                    contentDescription = "Vibration",
                    tint = if (isVibrationEnabled) EmeraldPrimary else TextMuted
                )
            }
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = {
                Text(text = "تسبیح ری سیٹ کریں؟", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(text = "کیا آپ واقعی موجودہ کاؤنٹر کو صفر کرنا چاہتے ہیں؟", fontSize = 13.sp)
            },
            confirmButton = {
                TextButton(onClick = {
                    currentCount = 0
                    showResetDialog = false
                }) {
                    Text("ہاں، صفر کریں", color = Color(0xFFC62828))
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("منسوخ", color = TextMuted)
                }
            }
        )
    }
}
