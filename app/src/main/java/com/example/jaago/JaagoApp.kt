package com.example.jaago

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jaago.ui.screens.AlarmScreen

@Composable
fun JaagoApp(
    modifier: Modifier
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.AlarmScreen.route
    ) {
        composable(
            route = Screens.AlarmScreen.route
        ) {
            AlarmScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

sealed class Screens(val route: String) {
        object AlarmScreen : Screens("AlarmScreen")
        object ClockScreen : Screens("ClockScreen")
        object StopWatchScreen : Screens("StopWatchScreen")
        object TimerScreen : Screens("TimerScreen")
}
