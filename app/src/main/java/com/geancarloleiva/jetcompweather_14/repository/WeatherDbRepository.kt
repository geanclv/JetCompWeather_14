package com.geancarloleiva.jetcompweather_14.repository

import androidx.room.*
import com.geancarloleiva.jetcompweather_14.data.WeatherDao
import com.geancarloleiva.jetcompweather_14.model.Favorite
import com.geancarloleiva.jetcompweather_14.model.MetricUnit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(
    private val weatherDao: WeatherDao
) {

    //CRUD for Favorite
    fun getFavorites(): Flow<List<Favorite>> {
        return weatherDao.getFavorites()
    }

    suspend fun getFavoriteById(city: String): Favorite {
        return weatherDao.getFavoriteById(city)
    }

    suspend fun createFavorite(favorite: Favorite) {
        return weatherDao.createFavorite(favorite)
    }

    suspend fun updateFavorite(favorite: Favorite) {
        return weatherDao.updateFavorite(favorite)
    }

    suspend fun deleteFavorite(favorite: Favorite) {
        return weatherDao.deleteFavorite(favorite)
    }

    suspend fun deleteAllFavorites() {
        return weatherDao.deleteAllFavorites()
    }

    //CRUD for MetricUnit
    fun getMetricUnits(): Flow<List<MetricUnit>> {
        return weatherDao.getMetricUnits()
    }

    suspend fun createMetricUnit(metricUnit: MetricUnit) {
        return weatherDao.createMetricUnit(metricUnit)
    }

    suspend fun updateMetricUnit(metricUnit: MetricUnit) {
        return weatherDao.updateMetricUnit(metricUnit)
    }

    suspend fun deleteMetricUnit(metricUnit: MetricUnit) {
        return weatherDao.deleteMetricUnit(metricUnit)
    }

    suspend fun deleteAllMetricUnits() {
        return weatherDao.deleteAllMetricUnits()
    }
}