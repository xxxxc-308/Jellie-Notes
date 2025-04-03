package com.cloudsurfe.jellynotes.frontend.settings_screen

data class SettingsState(
    val firstLaunch : Boolean = true,
    val darkMode : Boolean? = null,
    val amoledTheme : Boolean = false
)