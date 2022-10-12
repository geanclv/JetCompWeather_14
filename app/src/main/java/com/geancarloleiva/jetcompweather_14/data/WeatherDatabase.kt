package com.geancarloleiva.jetcompweather_14.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geancarloleiva.jetcompweather_14.model.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}