package com.cloudsurfe.jellynotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme()

private val LightColorScheme = lightColorScheme()

private val AmoledDarkColors = darkColorScheme()

@Composable
fun JellyNotesTheme(
    darkMode: Boolean?,
    amoledMode: Boolean,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val isDarkMode = isDarkMode(darkMode = darkMode)
    val colors = when {
        isDarkMode && amoledMode -> AmoledDarkColors
        isDarkMode && !amoledMode -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        shapes = Shapes(),
        typography = Typography(),
        content = content
    )

}

@Composable
private fun isDarkMode(darkMode: Boolean?): Boolean {
    return when (darkMode) {
        true -> true
        false -> false
        else -> isSystemInDarkTheme()
    }
}

