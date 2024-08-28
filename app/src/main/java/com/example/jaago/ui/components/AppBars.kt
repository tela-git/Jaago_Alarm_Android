package com.example.jaago.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jaago.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JaagoTopAppBar(
    modifier: Modifier,
    navController: NavHostController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "Jaago"
    TopAppBar(
        title = {
            Text(
                text = currentRoute,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun JaagoBottomAppBar(
    modifier: Modifier,
    navController: NavHostController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "Alarm"

    val screens: List<Pair<String, Int>> = listOf (
        Pair("Alarm", R.drawable.alarm_icon),
        Pair("Clock", R.drawable.clock_icon),
        Pair("Stopwatch", R.drawable.stopwatch_icon),
        Pair("Timer", R.drawable.timer_icon),
        )

    NavigationBar(

    )  {
        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.first,
                onClick = {
                    navController.navigate(screen.first)
                },
                icon = {
                    Icon(
                        painter = painterResource(screen.second),
                        contentDescription = screen.first
                    )
                },
                label = {
                    Text(
                        screen.first
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF00897B), // Teal 600
                    selectedTextColor = Color(0xFF00897B), // Teal 600
                    unselectedIconColor = Color(0xFF757575), // Grey 600
                    unselectedTextColor = Color(0xFF757575) // Grey 600
                )
            )
        }
    }
}

