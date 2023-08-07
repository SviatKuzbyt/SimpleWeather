package com.example.simpleweather.data.weather

import android.content.Context
import com.example.simpleweather.data.elements.City

class WeatherRepository(context: Context) {
    private val load = LoadWeather()
    private val file = LastCityFile(context)

    fun load(name: String) = load.getWeather(name)

    fun getLastCity() = file.read()

    fun setLastCity(city: City){
        file.write(city)
    }
}