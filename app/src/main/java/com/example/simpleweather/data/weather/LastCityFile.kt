package com.example.simpleweather.data.weather

import android.content.Context
import com.example.simpleweather.data.elements.City
import java.io.File

class LastCityFile(context: Context) {
    private val filePath = File(context.filesDir, "lastCity.txt")

    fun write(city: City) {
        try {
            filePath.writeText(convertToString(city))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun convertToString(city: City) = "${city.nameEn}\n${city.nameUa}"

    fun read(): City?{
        return try{
            convertToCity(filePath.readText())
        } catch (e: Exception){
            null
        }
    }

    private fun convertToCity(text: String): City{
        val lines = text.split('\n')
        return City(lines[0], lines[1])
    }
}