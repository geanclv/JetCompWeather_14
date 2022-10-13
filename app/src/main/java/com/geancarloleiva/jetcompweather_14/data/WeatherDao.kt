package com.geancarloleiva.jetcompweather_14.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.geancarloleiva.jetcompweather_14.model.Favorite
import com.geancarloleiva.jetcompweather_14.model.MetricUnit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    //CRUD for Favorite
    @Query("SELECT * FROM tbl_favorite")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM tbl_favorite WHERE city = :city")
    suspend fun getFavoriteById(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("DELETE FROM tbl_favorite")
    suspend fun deleteAllFavorites()

    //CRUD for MetricUnit
    @Query("SELECT * FROM tbl_settings")
    fun getMetricUnits(): Flow<List<MetricUnit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createMetricUnit(metricUnit: MetricUnit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMetricUnit(metricUnit: MetricUnit)

    @Delete
    suspend fun deleteMetricUnit(metricUnit: MetricUnit)

    @Query("DELETE FROM tbl_settings")
    suspend fun deleteAllMetricUnits()
}