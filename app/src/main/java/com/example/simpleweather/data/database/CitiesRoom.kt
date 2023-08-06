package com.example.simpleweather.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [CityEntity::class])
abstract class CitiesRoom: RoomDatabase() {
    abstract fun dao(): CityDao

    companion object {
        @Volatile private var instance: CitiesRoom? = null
        fun getInstance(context: Context): CitiesRoom {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): CitiesRoom {
            return Room.databaseBuilder(context, CitiesRoom::class.java, "cities-db")
                .build()
        }
    }
}