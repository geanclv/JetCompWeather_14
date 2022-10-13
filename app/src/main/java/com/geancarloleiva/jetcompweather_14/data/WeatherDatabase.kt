package com.geancarloleiva.jetcompweather_14.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geancarloleiva.jetcompweather_14.model.Favorite
import com.geancarloleiva.jetcompweather_14.model.MetricUnit

@Database(entities = [Favorite::class, MetricUnit::class],
    version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}