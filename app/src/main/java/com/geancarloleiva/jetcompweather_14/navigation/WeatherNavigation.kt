package com.geancarloleiva.jetcompweather_14.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.geancarloleiva.jetcompweather_14.screen.about.WeatherAboutScreen
import com.geancarloleiva.jetcompweather_14.screen.favorite.WeatherFavoriteScreen
import com.geancarloleiva.jetcompweather_14.screen.main.WeatherMainScreen
import com.geancarloleiva.jetcompweather_14.screen.search.WeatherSearchScreen
import com.geancarloleiva.jetcompweather_14.screen.settings.WeatherSettingsScreen
import com.geancarloleiva.jetcompweather_14.screen.splash.WeatherSplashScreen
import com.geancarloleiva.jetcompweather_14.viewmodel.main.MainViewModel

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {
        //Possible destinations when the user navigates
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }
        //creating a variable to send params in the url
        val route = WeatherScreens.MainScreen.name
        val param = "city"
        composable(
            "$route/{$param}",
            arguments = listOf(
                navArgument(name = param) {
                    type = NavType.StringType
                }
            )
        ) { navBack ->
            navBack.arguments?.getString(param).let { paramValue ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                WeatherMainScreen(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    city = paramValue
                )
            }
        }
        composable(WeatherScreens.SearchScreen.name) {
            //viewmodel
            WeatherSearchScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            //viewmodel
            WeatherAboutScreen(navController = navController)
        }
        composable(WeatherScreens.FavoriteScreen.name) {
            //viewmodel
            WeatherFavoriteScreen(navController = navController)
        }
        composable(WeatherScreens.SettingsScreen.name) {
            //viewmodel
            WeatherSettingsScreen(navController = navController)
        }
    }
}