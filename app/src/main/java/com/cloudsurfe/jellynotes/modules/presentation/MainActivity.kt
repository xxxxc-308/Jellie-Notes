package com.cloudsurfe.jellynotes.modules.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cloudsurfe.jellynotes.core.SettingsConstants
import com.cloudsurfe.jellynotes.data.setting.SettingsDataStore
import com.cloudsurfe.jellynotes.modules.presentation.settings_screen.SettingsViewModel
import com.cloudsurfe.jellynotes.ui.theme.JellyNotesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val settingsViewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var settingsDataStore: SettingsDataStore
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.Companion.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.Companion.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { settingsViewModel.setKeepOnScreenCondition }
        setContent {
            val firstLaunch =
                runBlocking { settingsDataStore.getBoolean(SettingsConstants.FIRST_LAUNCH) ?: true }
            val settingState = settingsViewModel.state.collectAsStateWithLifecycle()
            JellyNotesTheme(
                darkMode = settingState.value.darkMode,
            ) {

            }
        }
    }

}