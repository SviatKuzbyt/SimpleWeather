package com.example.simpleweather.data.cities.database

import android.content.Context
import androidx.room.Room
import com.example.simpleweather.data.elements.City

class CitiesDBRepository(context: Context, test: Boolean = false) {

    private val dao = if (test)
        Room.inMemoryDatabaseBuilder(context, CitiesRoom::class.java).build().dao()
    else
        CitiesRoom.getInstance(context).dao()

    fun getCities(): MutableList<City>{
        return try {
            dao.getCities()
        } catch (e: Exception){
            mutableListOf(City("DB error","Помилка БД"))
        }
    }

    fun addCity(city: City){
        try {
            dao.addCity( CityEntity(0, city.nameEn, city.nameUa) )
        } catch (_: Exception){}
    }

    fun deleteCity(city: String){
        try {
            dao.delete(city)
        } catch (_: Exception){}
    }
}