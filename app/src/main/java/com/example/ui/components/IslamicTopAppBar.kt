package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.repository.UserProfileRepository
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IslamicTopAppBar(
    title: String = "خانقاہ قادریہ",
    subtitle: String = "خانقاہِ قادریہ مجیدیہ، بدایوں شریف",
    currentLanguage: AppLanguage = AppLanguage.URDU,
    onSearchClick: () -> Unit,
    onLanguageToggle: () -> Unit,
    onAdminClick: () -> Unit,
    onAboutClick: () -> Unit,
    onProfileClick: () -> Unit = {},
    onKhanqahBadgeClick: () -> Unit = {}
) {
    val authState by UserProfileRepository.authState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(EmeraldDark, EmeraldPrimary, EmeraldDark)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Title and Subtitle with Khanqah Emblem
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onKhanqahBadgeClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KhanqahOfficialLogo(
                        size = 38.dp,
                        elevation = 3.dp
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = when (currentLanguage) {
                                    AppLanguage.URDU -> "خانقاہ قادریہ"
                                    AppLanguage.HINDI -> "ख़ानक़ाह क़ादरिया"
                                    AppLanguage.ENGLISH -> "Khanqah Qadriah"
                                    AppLanguage.HINGLISH -> "Khanqah Qadriah"
                                },
                                color = GoldLight,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "✦",
                                color = GoldPrimary,
                                fontSize = 12.sp
                            )
                        }
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.URDU -> "مجیدیہ، بدایوں شریف • qadri.in"
                                AppLanguage.HINDI -> "मजीदिया, बदायूँ शरीफ़ • qadri.in"
                                else -> "Majeediah, Budaun Shareef • qadri.in"
                            },
                            color = CreamBg.copy(alpha = 0.85f),
                            fontSize = 11.sp
                        )
                    }
                }

                // Action Icons
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onSearchClick,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = GoldLight,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Language Quick Badge Switcher
                    Surface(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clickable { onLanguageToggle() },
                        shape = RoundedCornerShape(8.dp),
                        color = GoldDark.copy(alpha = 0.3f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.6f))
                    ) {
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.URDU -> "اردو"
                                AppLanguage.HINDI -> "हिन्दी"
                                AppLanguage.ENGLISH -> "EN"
                                AppLanguage.HINGLISH -> "Hin"
                            },
                            color = GoldLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                        )
                    }

                    IconButton(
                        onClick = onProfileClick,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = if (authState.isLoggedIn) GoldPrimary else GoldLight.copy(alpha = 0.85f),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    IconButton(
                        onClick = onAdminClick,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = "Admin",
                            tint = GoldLight.copy(alpha = 0.85f),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(
                        onClick = onAboutClick,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About",
                            tint = GoldLight.copy(alpha = 0.85f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
