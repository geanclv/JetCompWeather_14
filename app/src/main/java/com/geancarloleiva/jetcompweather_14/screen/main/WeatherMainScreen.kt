package com.geancarloleiva.jetcompweather_14.screen.main

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

@Composable
fun WeatherMainScreen(
    navController: NavController,
    viewModel: ViewModel = hiltViewModel()
) {
    Text(text = "Main screen")
}