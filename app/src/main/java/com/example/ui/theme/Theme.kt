package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = EmeraldDark,
    primaryContainer = EmeraldPrimary,
    onPrimaryContainer = GoldLight,
    secondary = GoldLight,
    onSecondary = EmeraldDark,
    secondaryContainer = DarkGreenCard,
    onSecondaryContainer = CreamBg,
    tertiary = EmeraldLight,
    onTertiary = Color.White,
    background = DarkGreenBg,
    onBackground = CreamBg,
    surface = DarkGreenSurface,
    onSurface = CreamBg,
    surfaceVariant = DarkGreenCard,
    onSurfaceVariant = GoldLight,
    outline = DarkGreenBorder
)

private val LightColorScheme = lightColorScheme(
    primary = EmeraldPrimary,
    onPrimary = Color.White,
    primaryContainer = EmeraldSoftBg,
    onPrimaryContainer = EmeraldDark,
    secondary = GoldDark,
    onSecondary = Color.White,
    secondaryContainer = CreamCard,
    onSecondaryContainer = EmeraldDark,
    tertiary = GoldPrimary,
    onTertiary = EmeraldDark,
    background = CreamBg,
    onBackground = TextCharcoal,
    surface = CreamSurface,
    onSurface = TextCharcoal,
    surfaceVariant = ParchmentLight,
    onSurfaceVariant = TextMuted,
    outline = Color(0xFFDCD4C4)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
