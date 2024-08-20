package com.example.jaago

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jaago.ui.components.JaagoTopAppBar
import com.example.jaago.ui.screens.AlarmScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
        }

    ) {
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
}

sealed class Screens(val route: String) {
        object AlarmScreen : Screens("AlarmScreen")
        object ClockScreen : Screens("ClockScreen")
        object StopWatchScreen : Screens("StopWatchScreen")
        object TimerScreen : Screens("TimerScreen")
}
