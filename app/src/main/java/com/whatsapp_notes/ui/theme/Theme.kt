package com.whatsapp_notes.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme // Not used for a dark-only theme, but good to have
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Define custom colors based on your Tailwind config
val Primary = Color(0xFF3B82F6) // primary
val Secondary = Color(0xFF1E1E1E) // secondary
val DarkDefault = Color(0xFF1E1E1E) // dark.DEFAULT
val DarkLighter = Color(0xFF242424) // dark.lighter
val DarkDarker = Color(0xFF171717) // dark.darker

val Gray300 = Color(0xFFD1D5DB) // text-gray-300
val Gray400 = Color(0xFF9CA3AF) // text-gray-400
val Gray500 = Color(0xFF6B7280) // text-gray-500
val Blue300 = Color(0xFF93C5FD) // text-blue-300
val Blue900 = Color(0xFF1E3A8A) // bg-blue-900
val Green300 = Color(0xFF6EE7B7) // text-green-300
val Green900 = Color(0xFF064E3B) // bg-green-900
val Purple300 = Color(0xFFC4B5FD) // text-purple-300
val Purple900 = Color(0xFF4C1D95) // bg-purple-900
val Red400 = Color(0xFFEF4444) // bg-red-400
val Blue400 = Color(0xFF60A5FA) // bg-blue-400
val Purple400 = Color(0xFFA78BFA) // bg-purple-400
val Orange400 = Color(0xFFFB923C) // bg-orange-400
val Teal400 = Color(0xFF2DD4BF) // bg-teal-400
val Pink400 = Color(0xFFF472B6) // bg-pink-400
val Indigo400 = Color(0xFF818CF8) // bg-indigo-400
val Yellow400 = Color(0xFFFACC15) // bg-yellow-400


// Define a dark color scheme based on the HTML's dark theme
private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    secondary = Secondary,
    onSecondary = Color.White,
    background = DarkDefault,
    onBackground = Color.White,
    surface = DarkLighter,
    onSurface = Color.White,
    surfaceVariant = DarkDarker,
    onSurfaceVariant = Gray300,
    error = Red400, // Using a red color for errors
    onError = Color.White
)

// Although the HTML is dark-themed, a light scheme is included for completeness
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    secondary = Secondary,
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color.LightGray,
    onSurfaceVariant = Color.DarkGray,
    error = Color.Red,
    onError = Color.White
)

/**
 * Composable function for the Notes App theme.
 * Applies a dark theme by default, aligning with the provided HTML.
 *
 * @param darkTheme Boolean to force dark theme, defaults to system setting.
 * @param content The UI content to apply the theme to.
 */
@Composable
fun NotesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Use system dark theme setting
    content: @Composable () -> Unit
) {
    // For this app, we are primarily targeting a dark theme as per the HTML.
    // We can simplify and always use DarkColorScheme if the app is strictly dark.
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        // If a light theme is ever needed, you'd define a LightColorScheme
        // For now, we'll default to DarkColorScheme even if system is light,
        // to match the HTML's dark-only design.
        DarkColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb() // Set status bar to background color
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme // Adjust status bar icons
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Typography will be defined in Type.kt
        content = content
    )
}
