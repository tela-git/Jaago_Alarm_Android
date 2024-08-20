package com.example.jaago.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JaagoTopAppBar(
    modifier: Modifier,
    navController: NavHostController
) {
    TopAppBar(
        title = {
            Text(
                text = navController.currentDestination?.route ?: "Jaago"
            )
        }
    )
}