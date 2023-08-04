package com.example.simpleweather.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Test

class WeatherRepositoryTest{
    private val weatherRepository = WeatherRepository()
    @Test
    fun testLoadInfo() = runBlocking {
        withContext(Dispatchers.IO){
            val result = weatherRepository.getWeather("самбір")
            println(result)
        }
    }
}