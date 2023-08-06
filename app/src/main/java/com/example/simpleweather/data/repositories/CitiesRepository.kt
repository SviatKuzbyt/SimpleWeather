package com.example.simpleweather.data.repositories

import android.content.Context
import com.example.simpleweather.data.City
import com.example.simpleweather.data.database.CitiesDBRepository
import com.example.simpleweather.data.load.LoadCity

class CitiesRepository(context: Context) {
    private val dataBase = CitiesDBRepository(context)
    private val loadCity = LoadCity()

    fun addCity(name: String): City{
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