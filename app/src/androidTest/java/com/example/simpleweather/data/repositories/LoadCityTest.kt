package com.example.simpleweather.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Test

class LoadCityTest{

    val loadCity = LoadCity()
    @Test
    fun testLoadCity() = runBlocking {
        withContext(Dispatchers.IO){
            println(loadCity.checkCity("Краків"))
            println(loadCity.checkCity("dsfgnhdsjkh"))
        }
    }
}