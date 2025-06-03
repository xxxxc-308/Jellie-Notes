package com.cloudsurfe.jellienotes.modules.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.cloudsurfe.jellienotes.modules.presentation.home_screen.HomeScreen
import com.cloudsurfe.jellienotes.modules.presentation.onboarding_screen.OnBoardingScreen
import com.cloudsurfe.jellienotes.modules.presentation.plugin_screen.PluginScreen
import com.cloudsurfe.jellienotes.modules.presentation.setting_screen.SettingEvent
import com.cloudsurfe.jellienotes.modules.presentation.setting_screen.SettingScreen
import com.cloudsurfe.jellienotes.modules.presentation.setting_screen.SettingViewModel


@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    backstack: NavBackStack,
    settingViewModel: SettingViewModel,
    finishActivity: () -> Unit
) {

    NavDisplay(
        modifier = modifier,
        backStack = backstack,
        onBack = { backstack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<OnBoarding> {
                OnBoardingScreen(
                    onClick = {
                        when (it) {
                            SettingEvent.SetFirstLaunch -> settingViewModel.onEvent(it)
                            else -> Unit
                        }
                        backstack.add(Home)
                    }
                )
            }
            entry<Home> {
                BackHandler {
                    finishActivity()
                }
                HomeScreen(
                    onClick = {
                        backstack.add(Setting)
                    }
                )
            }
            entry<Setting>(
                metadata = NavDisplay.transitionSpec {
                    sharedXTransitionIn(initial = { it }) togetherWith ExitTransition.None
                } + NavDisplay.popTransitionSpec {
                    EnterTransition.None togetherWith sharedXTransitionOut(target = { it })
                }
            ) {
                SettingScreen(
                    onClick = {
                        backstack.add(Plugin)
                    }
                )
            }
            entry<Plugin> {
                PluginScreen()
            }
        }
    )
}