package com.example.simpleweather.data.database

import android.content.Context
import androidx.room.Room
import com.example.simpleweather.data.City

class CitiesDBRepository(context: Context, test: Boolean = false) {

    private val dao = if (test)
        Room.inMemoryDatabaseBuilder(context, CitiesRoom::class.java).build().dao()
    else
        CitiesRoom.getInstance(context).dao()

    fun getCities() = dao.getCities()

    fun addCity(city: City){
        dao.addCity(
            CityEntity(0, city.nameEn, city.nameUa)
        )
    }

    fun deleteCity(city: String){
        dao.delete(city)
    }
}