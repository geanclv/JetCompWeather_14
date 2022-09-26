package com.geancarloleiva.jetcompweather_14.repository

import com.geancarloleiva.jetcompweather_14.data.DataOrException
import com.geancarloleiva.jetcompweather_14.model.Weather
import com.geancarloleiva.jetcompweather_14.network.WeatherApi
import javax.inject.Inject

class WeatherRepository
@Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(queryCity: String)
            : DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(queryCity)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }

        return DataOrException(data = response)
    }
}