package com.geancarloleiva.jetcompweather_14.repository

import androidx.room.*
import com.geancarloleiva.jetcompweather_14.data.WeatherDao
import com.geancarloleiva.jetcompweather_14.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(
    private val weatherDao: WeatherDao
) {

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
}