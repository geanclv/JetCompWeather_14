package com.geancarloleiva.jetcompweather_14.screen.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.geancarloleiva.jetcompweather_14.R
import com.geancarloleiva.jetcompweather_14.data.DataOrException
import com.geancarloleiva.jetcompweather_14.model.Weather
import com.geancarloleiva.jetcompweather_14.model.WeatherItem
import com.geancarloleiva.jetcompweather_14.utils.Constants
import com.geancarloleiva.jetcompweather_14.utils.formatDate
import com.geancarloleiva.jetcompweather_14.utils.formatDateTime
import com.geancarloleiva.jetcompweather_14.utils.formatDecimals
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
    val weatherItem = data.list[0]
    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ContentHeader(weather = weatherItem)
        Divider(color = MaterialTheme.colors.onSecondary)
        HumidityWindPressureRow(weather = weatherItem)
        Divider(color = MaterialTheme.colors.onSecondary)
        SunriseSunSet(weather = weatherItem)
        ContentDetail(data = data)
    }
}

@Composable
fun ContentHeader(weather: WeatherItem) {
    //val firstData = weather
    /*val currentDate = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
    val today = currentDate.format(formatter)*/
    val imageUrl = "${Constants.API_IMG}${weather.weather[0].icon}${Constants.API_IMG_EXT}"

    /*Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {*/
    Text(
        text = formatDate(weather.dt),
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
                text = "${formatDecimals(weather.temp.day)}°",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = weather.weather[0].main,
                fontStyle = FontStyle.Italic
            )
        }
    }
    //}
}

@Composable
fun WeatherStateImg(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "",
        modifier = Modifier.size(80.dp)
    )
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        //Humidity
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.humidity}%",
                style = MaterialTheme.typography.caption
            )
        }
        //Pressure
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure_gauge),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.pressure} psi",
                style = MaterialTheme.typography.caption
            )
        }
        //Wind
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.speed} ${Constants.UNIT_KPH}",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun SunriseSunSet(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        //Sunrise
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${formatDateTime(weather.sunrise)}",
                style = MaterialTheme.typography.caption
            )
        }
        //Sunset
        Row(modifier = Modifier.padding(4.dp)) {
            Text(
                text = "${formatDateTime(weather.sunset)}",
                style = MaterialTheme.typography.caption
            )
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ContentDetail(data: Weather) {
    Text(
        text = stringResource(id = R.string.app_weather_detail_title),
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.Bold
    )
    ContentDetailItem(data)
}

@Composable
fun ContentDetailItem(data: Weather) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color(0xffeef1ef),
        shape = RoundedCornerShape(size = 14.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(2.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            items(items = data.list) { item: WeatherItem ->
                WeatherDetailRow(weather = item)
            }
        }
    }
}

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "${Constants.API_IMG}${weather.weather[0].icon}${Constants.API_IMG_EXT}"

    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(8.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Day of the week
            Text(
                text = formatDate(weather.dt).split(",")[0],
                modifier = Modifier.padding(start = 15.dp)
            )
            //Weather icon
            WeatherStateImg(imageUrl = imageUrl)
            //Weather description
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xffffc400)
            ) {
                Text(
                    text = weather.weather[0].description,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            //Highs & Lows
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue.copy(0.7f),
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append("${formatDecimals(weather.temp.max)}°")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.LightGray,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append("${formatDecimals(weather.temp.min)}°")
                    }
                },
                modifier = Modifier.padding(end = 15.dp)
            )
        }
    }
}
