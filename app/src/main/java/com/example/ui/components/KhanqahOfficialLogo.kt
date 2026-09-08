package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*

/**
 * Official unified emblem and logo for Khanqah Qadriah Majeediah, Budaun Shareef.
 * Displays the authentic green dome & arch emblem with thin, elegant proportions.
 */
@Composable
fun KhanqahThinLogo(
    modifier: Modifier = Modifier,
    height: Dp = 38.dp,
    contentDescription: String = "Official Logo of Khanqah Qadriah Badaun Shareef"
) {
    Image(
        painter = painterResource(id = R.drawable.img_khanqah_logo),
        contentDescription = contentDescription,
        modifier = modifier
            .height(height)
            .aspectRatio(1.463f)
            .testTag("khanqah_thin_logo"),
        contentScale = androidx.compose.ui.layout.ContentScale.Fit
    )
}

@Composable
fun KhanqahOfficialLogo(
    modifier: Modifier = Modifier,
    size: Dp = 72.dp,
    showSubtext: Boolean = false,
    showUrduName: Boolean = false,
    circular: Boolean = false,
    standalone: Boolean = false,
    thinStyle: Boolean = true,
    goldGlow: Boolean = true,
    elevation: Dp = 2.dp
) {
    val naturalRatio = 1.463f
    val cornerShape = if (circular) CircleShape else RoundedCornerShape(if (size > 100.dp) 16.dp else if (size > 60.dp) 12.dp else 8.dp)

    Column(
        modifier = modifier.testTag("khanqah_official_logo_container"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (standalone) {
            Image(
                painter = painterResource(id = R.drawable.img_khanqah_logo),
                contentDescription = "Official Logo of Khanqah Qadriah Badaun Shareef",
                modifier = Modifier
                    .height(size)
                    .aspectRatio(naturalRatio)
                    .testTag("khanqah_official_logo_standalone"),
                contentScale = androidx.compose.ui.layout.ContentScale.Fit
            )
        } else if (thinStyle) {
            // Elegant thin presentation with natural aspect ratio and hairline gold border
            Surface(
                modifier = Modifier
                    .height(size)
                    .width(size * 1.36f)
                    .shadow(
                        elevation = if (elevation > 2.dp) 2.dp else elevation,
                        shape = cornerShape,
                        ambientColor = GoldPrimary.copy(alpha = 0.3f),
                        spotColor = GoldDark.copy(alpha = 0.4f)
                    )
                    .testTag("khanqah_official_logo_thin_badge"),
                shape = cornerShape,
                color = Color.White,
                border = BorderStroke(
                    width = 0.7.dp,
                    color = GoldPrimary.copy(alpha = 0.6f)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(cornerShape)
                        .background(Color.White)
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_khanqah_logo),
                        contentDescription = "Official Logo of Khanqah Qadriah Badaun Shareef",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Fit
                    )
                }
            }
        } else {
            Surface(
                modifier = Modifier
                    .size(size)
                    .shadow(
                        elevation = elevation,
                        shape = cornerShape,
                        ambientColor = GoldPrimary,
                        spotColor = GoldDark
                    )
                    .testTag("khanqah_official_logo_badge"),
                shape = cornerShape,
                color = Color.White,
                border = BorderStroke(
                    width = 0.8.dp,
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            GoldLight,
                            GoldPrimary,
                            GoldDark,
                            GoldLight
                        )
                    )
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(cornerShape)
                        .background(Color.White)
                        .padding(if (circular) 4.dp else 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_khanqah_logo),
                        contentDescription = "Official Logo of Khanqah Qadriah Badaun Shareef",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Fit
                    )
                }
            }
        }

        if (showUrduName) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "خانقاہِ قادریہ بدایوں شریف",
                fontSize = if (size >= 80.dp) 18.sp else 14.sp,
                fontWeight = FontWeight.Bold,
                color = GoldLight,
                textAlign = TextAlign.Center
            )
        }

        if (showSubtext) {
            Text(
                text = "Khanqah Qadriah",
                fontSize = if (size >= 80.dp) 13.sp else 10.sp,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Badaun Shareef (U.P. India)",
                fontSize = if (size >= 80.dp) 10.sp else 8.sp,
                color = TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Animated Loading State with official Khanqah Logo pulsing
 */
@Composable
fun KhanqahLoadingIndicator(
    modifier: Modifier = Modifier,
    message: String = "لوڈ ہو رہا ہے..."
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            KhanqahOfficialLogo(
                size = 64.dp,
                elevation = 6.dp
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = message,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = EmeraldDark
        )
    }
}
