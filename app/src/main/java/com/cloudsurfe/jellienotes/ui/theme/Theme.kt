package com.cloudsurfe.jellienotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme()

private val LightColorScheme = lightColorScheme()

@Composable
fun JellyNotesTheme(
    darkMode: Boolean?,
    content: @Composable () -> Unit
) {
    val isDarkMode = isDarkMode(darkMode = darkMode)
    val colors = if (isDarkMode) DarkColorScheme else LightColorScheme

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

