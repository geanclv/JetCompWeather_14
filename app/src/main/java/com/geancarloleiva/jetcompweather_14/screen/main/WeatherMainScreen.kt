package com.geancarloleiva.jetcompweather_14.screen.main

import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
    Scaffold(backgroundColor = Color.White,
        topBar = {
            WeatherAppBar(
                title = "${weather.city.name}, ${weather.city.country}",
                navController = navController,
                elevation = 3.dp,
                //icon = Icons.Default.ArrowBack, //here only for example
                //action called when in TopAppBar implementation. ID 27676 (just to find and reference)
                /*onButtonClicked = {
                    Log.e("GCLV", "MainScaffold: icon Button clicked", )
                }*/
            ){
                //As onButtonClicked is the last param, we can use this lambda
                Log.e("GCLV", "MainScaffold: icon Button clicked", )
            }
        }) {
        //Main content: city selected
        MainContent(data = weather)
    }
}

@Composable
fun MainContent(data: Weather) {

}