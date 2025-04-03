package com.cloudsurfe.jellynotes.frontend.settings_screen

sealed class SettingsEvent{
    data object SetFirstLaunch : SettingsEvent()
    data class SetDarkMode(val darkMode : Boolean) : SettingsEvent()
    data class SetAmoledTheme(val amoledTheme : Boolean) : SettingsEvent()
}





