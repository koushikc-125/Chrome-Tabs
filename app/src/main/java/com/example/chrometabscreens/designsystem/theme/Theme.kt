package com.example.chrometabscreens.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = Neutral900,
    onBackground = Neutral100,
    primary = WarmNeutral300,
    onPrimary = Neutral900,
    secondary = Neutral400,
    onSecondary = Neutral900,
    tertiary = Neutral850,
    onTertiary = Neutral825,
    surface = Neutral800,
    onSurface = Neutral100,
    outline = Neutral600,
    scrim = Neutral550
)

private val LightColorScheme = lightColorScheme(
    background = Neutral200,
    onBackground = Neutral700,
    primary = Neutral600,
    onPrimary = Neutral200,
    secondary = Neutral500,
    onSecondary = Neutral200,
    surface = Neutral100,
    onSurface = Neutral700,
    tertiary = Neutral250,
    onTertiary = Neutral275,
    outline = Neutral400,
    surfaceVariant = Neutral300,
    onSurfaceVariant = Neutral600,
    scrim = Neutral650
)

@Composable
fun ChromeTabScreensTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}