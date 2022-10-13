package com.geancarloleiva.jetcompweather_14.viewmodel.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geancarloleiva.jetcompweather_14.data.DataOrException
import com.geancarloleiva.jetcompweather_14.model.Weather
import com.geancarloleiva.jetcompweather_14.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    suspend fun getWeatherData(city: String, units: String)
    : DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(city, units)
    }

    /*val data: MutableState<DataOrException<Weather, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, true, Exception(""))
    )

    init {
        loadWeather()
    }

    fun loadWeather(){
        getWeather("Lima")
    }

    fun getWeather(queryCity: String){
        viewModelScope.launch {
            if(queryCity.isEmpty()) return@launch

            data.value.loading = true
            data.value = repository.getWeather(queryCity)

            if(data.value.data.toString().isNotEmpty())
                data.value.loading = false
        }
    }*/
}