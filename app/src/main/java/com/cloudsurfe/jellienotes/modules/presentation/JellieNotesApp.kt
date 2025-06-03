package com.cloudsurfe.jellienotes.modules.presentation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.cloudsurfe.jellienotes.modules.navigation.MainNavigation
import com.cloudsurfe.jellienotes.modules.presentation.setting_screen.SettingViewModel

@Composable
fun JellieNotesApp(
    startDestination: NavKey,
    settingSViewModel: SettingViewModel,
    finishActivity: () -> Unit
) {

    val backstack = rememberNavBackStack(startDestination)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { paddingValues ->
        MainNavigation(
            modifier = Modifier
                .consumeWindowInsets(paddingValues),
            backstack = backstack,
            settingViewModel = settingSViewModel,
            finishActivity = finishActivity
        )
    }
}