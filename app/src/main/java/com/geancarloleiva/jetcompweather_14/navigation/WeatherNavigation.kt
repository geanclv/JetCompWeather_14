package com.geancarloleiva.jetcompweather_14.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geancarloleiva.jetcompweather_14.screen.main.WeatherMainScreen
import com.geancarloleiva.jetcompweather_14.screen.splash.WeatherSplashScreen
import com.geancarloleiva.jetcompweather_14.viewmodel.main.MainViewModel

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name){
        //Possible destinations when the user navigates
        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }
        composable(WeatherScreens.MainScreen.name){
            val mainViewModel = hiltViewModel<MainViewModel>()
            WeatherMainScreen(navController = navController, mainViewModel = mainViewModel)
        }
    }
}