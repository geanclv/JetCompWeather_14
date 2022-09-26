package com.geancarloleiva.jetcompweather_14.network

import com.geancarloleiva.jetcompweather_14.model.Weather
import com.geancarloleiva.jetcompweather_14.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET(value = Constants.API_URL)
    suspend fun getWeather(
        @Query("q") queryCity: String,
        @Query("units") units: String = Constants.UNIT_IMPERIAL,
        @Query("appid") appid: String = Constants.API_KEY
    ): Weather
}