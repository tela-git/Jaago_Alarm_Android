package com.example.jaago

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jaago.ui.components.JaagoBottomAppBar
import com.example.jaago.ui.components.JaagoTopAppBar
import com.example.jaago.ui.screens.alarm.AlarmsViewScreen
import com.example.jaago.ui.screens.ClockScreen
import com.example.jaago.ui.screens.StopWatchScreen
import com.example.jaago.ui.screens.TimerScreen


@Composable
fun JaagoApp(
    navController: NavHostController,
    modifier: Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            JaagoTopAppBar(
                modifier = modifier,
                navController = navController
            )
        },
        bottomBar = {
            JaagoBottomAppBar(
                modifier = modifier,
                navController = navController,
            )
        }

    ) {innerPadding->
        NavHost(
            navController = navController,
            startDestination = Screens.AlarmScreen.route
        ) {
            composable(
                route = Screens.AlarmScreen.route
            ) {
                AlarmsViewScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }
            composable(
                route = Screens.ClockScreen.route
            ) {
                ClockScreen(
                    modifier = Modifier
                )
            }
            composable(
                route = Screens.StopWatchScreen.route
            ) {
                StopWatchScreen(
                    modifier = Modifier
                )
            }
            composable(
                route = Screens.TimerScreen.route
            ) {
                TimerScreen(
                    modifier = Modifier
                )
            }
        }

    }
}

sealed class Screens(val route: String) {
    object AlarmScreen : Screens("Alarm")
    object ClockScreen : Screens("Clock")
    object StopWatchScreen : Screens("Stopwatch")
    object TimerScreen : Screens("Timer")
}