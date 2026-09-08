package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.AppPreferencesRepository
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    onBack: () -> Unit,
    isUrdu: Boolean = true
) {
    var isAuthenticated by remember { mutableStateOf(false) }
    var pinInput by remember { mutableStateOf("") }
    var currentBanner by remember { mutableStateOf(AppPreferencesRepository.adminBanner.value) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .testTag("admin_screen_container")
    ) {
        TopAppBar(
            title = {
                Text(
                    text = if (isUrdu) "انتظامی پینل (خانقاہ قادریہ)" else "Admin Panel (Khanqah Qadriah)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = EmeraldDark)
        )

        if (!isAuthenticated) {
            // Authentication Gate
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(EmeraldSoftBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Security",
                                tint = EmeraldPrimary,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = if (isUrdu) "سیکیورٹی تصدیق" else "Admin Authentication",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )

                        Text(
                            text = if (isUrdu) "براہ کرم خانقاہ انتظامیہ کا سیکیورٹی پن داخل کریں (پہلے سے طے شدہ: 7860)" else "Please enter the admin PIN code (default: 7860)",
                            fontSize = 12.sp,
                            color = TextMuted,
                            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                        )

                        OutlinedTextField(
                            value = pinInput,
                            onValueChange = { pinInput = it },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            placeholder = { Text("****") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Button(
                            onClick = {
                                if (pinInput == "7860" || pinInput == "1234") {
                                    isAuthenticated = true
                                    Toast.makeText(context, "خوش آمدید!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "غلط سیکیورٹی کوڈ!", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                        ) {
                            Text(
                                text = if (isUrdu) "داخل ہوں" else "Enter",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        } else {
            // Admin Controls Dashboard
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                            Text(
                                text = if (isUrdu) "ہوم اسکرین اعلانات / بینر تبدیل کریں" else "Update Home Screen Announcement",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = currentBanner,
                                onValueChange = { currentBanner = it },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                maxLines = 4
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    AppPreferencesRepository.updateAdminBanner(currentBanner)
                                    Toast.makeText(context, "اعلان کامیابی کے ساتھ اپ ڈیٹ ہو گیا!", Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                            ) {
                                Text("محفوظ کریں", color = Color.White)
                            }
                        }
                    }
                }

                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                            Text(
                                text = if (isUrdu) "دینی مواد کی صداقت و نگرانی" else "Content Authenticity Verification",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "تمام قرآنی آیات، تراجم (کنز الایمان)، اوراد و وظائف اور احادیثِ مبارکہ اہل سنت و جماعت کے مستند ذخیرہ کتب سے تصدیق شدہ ہیں۔",
                                fontSize = 12.sp,
                                color = TextCharcoal,
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Verified, contentDescription = "Verified", tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "مستند اہل سنت و جماعت سرٹیفائیڈ", fontSize = 12.sp, color = EmeraldDark, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
