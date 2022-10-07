package com.geancarloleiva.jetcompweather_14.screen.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.geancarloleiva.jetcompweather_14.data.DataOrException
import com.geancarloleiva.jetcompweather_14.model.Weather
import com.geancarloleiva.jetcompweather_14.utils.Constants
import com.geancarloleiva.jetcompweather_14.viewmodel.main.MainViewModel
import com.geancarloleiva.jetcompweather_14.widget.WeatherAppBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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
            ) {
                //As onButtonClicked is the last param, we can use this lambda
                Log.e("GCLV", "MainScaffold: icon Button clicked")
            }
        }) {
        //Main content: city selected
        MainContent(data = weather)
    }
}

@Composable
fun MainContent(data: Weather) {
    val firstData = data!!.list[0]
    val currentDate = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
    val today = currentDate.format(formatter)
    val imageUrl = "${Constants.API_IMG}${firstData.weather[0].icon}${Constants.API_IMG_EXT}"

    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = today,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xffffc400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImg(imageUrl = imageUrl)
                Text(
                    text = firstData.feels_like.day.toString(),
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = firstData.weather[0].main,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Composable
fun WeatherStateImg(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "",
        modifier = Modifier.size(80.dp)
    )
}
