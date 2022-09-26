package com.geancarloleiva.jetcompweather_14.screen.main

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.geancarloleiva.jetcompweather_14.data.DataOrException
import com.geancarloleiva.jetcompweather_14.model.Weather
import com.geancarloleiva.jetcompweather_14.viewmodel.main.MainViewModel
import com.geancarloleiva.jetcompweather_14.widget.WeatherAppBar

@Composable
fun WeatherMainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    //Data in "state" mode: to be updated by coroutines when necessary
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        //value = mainViewModel.data.value
        value = mainViewModel.getWeatherData("Lima")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weatherData.data!!, navController)
    }
}

@Composable
fun MainScaffold(
    weather: Weather,
    navController: NavController
) {
    Scaffold(topBar = {

    }) {
        //Main content: city selected
        MainContent(data = weather)
    }
}

@Composable
fun MainContent(data: Weather) {

}