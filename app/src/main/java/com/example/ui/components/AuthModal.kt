package com.example.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.repository.UserProfileRepository
import com.example.ui.theme.*

@Composable
fun AuthModal(
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    var isSignUp by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("auth_modal_card"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CreamSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Logo and Close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextMuted
                        )
                    }
                    KhanqahOfficialLogo(
                        size = 50.dp,
                        circular = true,
                        elevation = 2.dp
                    )
                    Spacer(modifier = Modifier.width(36.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "خانقاہِ عالیہ قادریہ بدایوں شریف",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = if (isSignUp) "نیا عقیدت مند اکاؤنٹ بنائیں" else "لاگ ان / اپنے اکاؤنٹ میں داخل ہوں",
                    fontSize = 12.sp,
                    color = GoldDark,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Mode Selector Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(EmeraldSoftBg)
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (!isSignUp) EmeraldPrimary else Color.Transparent)
                            .clickable { isSignUp = false }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "لاگ ان (Sign In)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (!isSignUp) Color.White else TextCharcoal
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSignUp) EmeraldPrimary else Color.Transparent)
                            .clickable { isSignUp = true }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "رجسٹریشن (Sign Up)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSignUp) Color.White else TextCharcoal
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Name field (Sign up only)
                if (isSignUp) {
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("آپ کا نام مبارک") },
                        placeholder = { Text("مثال: محمد احمد قادری") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = EmeraldPrimary)
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Email field
                OutlinedTextField(
                    value = emailInput,
                    onValueChange = { emailInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("ای میل (Email)") },
                    placeholder = { Text("your.email@example.com") },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null, tint = EmeraldPrimary)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Password field
                OutlinedTextField(
                    value = passwordInput,
                    onValueChange = { passwordInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("پاس ورڈ (Password)") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = EmeraldPrimary)
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = TextMuted
                            )
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Submit Button
                Button(
                    onClick = {
                        if (emailInput.isBlank()) {
                            Toast.makeText(context, "برائے کرم درست ای میل درج فرمائیں", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (passwordInput.length < 4) {
                            Toast.makeText(context, "پاس ورڈ کم از کم 4 حروف پر مشتمل ہو", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        UserProfileRepository.login(emailInput, if (isSignUp) nameInput else null)
                        Toast.makeText(
                            context,
                            if (isSignUp) "خوش آمدید! آپ کا اکاؤنٹ کامیابی سے بن گیا" else "کامیابی سے لاگ ان ہو گیا",
                            Toast.LENGTH_SHORT
                        ).show()
                        onSuccess()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("auth_submit_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text(
                        text = if (isSignUp) "اکاؤنٹ بنائیں (Create Account)" else "داخل ہوں (Sign In)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Google Sign In Button
                OutlinedButton(
                    onClick = {
                        UserProfileRepository.loginWithGoogle(
                            accountName = "خادمِ درگاہِ قادریہ",
                            accountEmail = "qadri.devotee@gmail.com"
                        )
                        Toast.makeText(context, "گوگل کے ذریعے کامیابی سے لاگ ان ہوا", Toast.LENGTH_SHORT).show()
                        onSuccess()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("google_auth_button"),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.4f))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = EmeraldDark,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "گوگل اکاؤنٹ کے ساتھ جاری رکھیں (Google)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextCharcoal
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Continue as Guest Button
                TextButton(
                    onClick = {
                        UserProfileRepository.continueAsGuest()
                        Toast.makeText(context, "مہمان موڈ میں جاری ہے", Toast.LENGTH_SHORT).show()
                        onSuccess()
                    },
                    modifier = Modifier.testTag("guest_auth_button")
                ) {
                    Text(
                        text = "مہمان کے طور پر جاری رکھیں (Continue as Guest)",
                        fontSize = 12.sp,
                        color = GoldDark,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
