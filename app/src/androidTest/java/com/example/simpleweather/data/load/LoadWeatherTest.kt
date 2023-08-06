package com.example.simpleweather.data.load

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Test

class LoadWeatherTest{
    private val loadWeather = LoadWeather()
    @Test
    fun testLoadInfo() = runBlocking {
        withContext(Dispatchers.IO){
            val result = loadWeather.getWeather("самбір")
            println(result)
        }
    }
}