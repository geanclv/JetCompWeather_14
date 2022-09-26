package com.geancarloleiva.jetcompweather_14.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geancarloleiva.jetcompweather_14.screen.WeatherMainScreen
import com.geancarloleiva.jetcompweather_14.screen.WeatherSplashScreen

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
            WeatherMainScreen(navController = navController)
        }
    }
}