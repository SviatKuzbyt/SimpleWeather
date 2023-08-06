package com.example.simpleweather.data.weather

import android.content.Context
import com.example.simpleweather.ui.elements.ChangeCity

class WeatherRepository(context: Context) {
    private val load = LoadWeather()
    private val file = LastCityFile(context)

    fun load(name: String) = load.getWeather(name)

    fun getLastCity() = file.read()

    fun setLastCity(){
        file.write(ChangeCity.city)
    }
}