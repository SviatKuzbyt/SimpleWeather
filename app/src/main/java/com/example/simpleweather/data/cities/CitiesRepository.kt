package com.example.simpleweather.data.cities

import android.content.Context
import com.example.simpleweather.data.elements.City
import com.example.simpleweather.data.cities.database.CitiesDBRepository

class CitiesRepository(context: Context) {
    private val dataBase = CitiesDBRepository(context)
    private val loadCity = LoadCity()

    fun addCity(name: String): City {
        val result = loadCity.checkCity(name)
        if(result.nameEn != "_")
            dataBase.addCity(result)
        return result
    }

    fun deleteCity(city: City){
        dataBase.deleteCity(city.nameEn)
    }

    fun getCities() = dataBase.getCities()
}