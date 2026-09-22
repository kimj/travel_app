package com.mentalmachines.travel_app.ui.theme

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

private val LightColorScheme = lightColorScheme(
    primary = TravelPrimary,
    onPrimary = Color.White,
    primaryContainer = TravelPrimaryContainer,
    onPrimaryContainer = TravelOnPrimaryContainer,
    secondary = TravelSecondary,
    onSecondary = Color.White,
    secondaryContainer = TravelSecondaryContainer,
    onSecondaryContainer = TravelOnSecondaryContainer,
    tertiary = TravelTertiary,
    onTertiary = Color.White,
    tertiaryContainer = TravelTertiaryContainer,
    background = TravelBackgroundLight,
    onBackground = TravelOnSurfaceLight,
    surface = TravelSurfaceLight,
    onSurface = TravelOnSurfaceLight,
    surfaceVariant = TravelSurfaceVariantLight,
    onSurfaceVariant = TravelOnSurfaceVariantLight
)

private val DarkColorScheme = darkColorScheme(
    primary = TravelPrimaryLight,
    onPrimary = Color.Black,
    primaryContainer = TravelPrimaryDark,
    onPrimaryContainer = Color.White,
    secondary = TravelSecondaryLight,
    onSecondary = Color.White,
    tertiary = TravelTertiaryLight,
    onTertiary = Color.Black,
    background = TravelBackgroundDark,
    onBackground = TravelOnSurfaceDark,
    surface = TravelSurfaceDark,
    onSurface = TravelOnSurfaceDark,
    surfaceVariant = TravelSurfaceVariantDark,
    onSurfaceVariant = TravelOnSurfaceVariantDark
)

@Composable
fun TravelAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
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
