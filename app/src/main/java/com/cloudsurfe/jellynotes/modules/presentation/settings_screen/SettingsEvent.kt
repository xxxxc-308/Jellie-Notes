package com.cloudsurfe.jellynotes.modules.presentation.settings_screen

sealed class SettingsEvent{
    data object SetFirstLaunch : SettingsEvent()
    data class SetDarkMode(val darkMode : Boolean) : SettingsEvent()
}





