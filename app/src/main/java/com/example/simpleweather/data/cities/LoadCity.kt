package com.example.simpleweather.data.cities

import com.example.simpleweather.data.elements.API_KEY
import com.example.simpleweather.data.elements.City
import org.json.JSONArray
import java.net.URL

class LoadCity {
    fun checkCity(name: String): City {
        return try {
            val link = "https://api.openweathermap.org/geo/1.0/direct?q=$name&limit=1&appid=$API_KEY"
            val text = URL(link).readText()

            if(text != "[]"){
                val json = JSONArray(text).getJSONObject(0)
                val enName = json.getString("name")
                val uaName = try {
                    json.getJSONObject("local_names").getString("uk")
                } catch (_: Exception){enName}
                City(enName, uaName)
            }
            else City("_", "no found")

        } catch (e: Exception){
            City("_", "other")
        }
    }
}