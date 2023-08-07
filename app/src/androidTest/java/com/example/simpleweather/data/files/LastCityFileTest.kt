package com.example.simpleweather.data.files

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.simpleweather.data.elements.City
import com.example.simpleweather.data.weather.LastCityFile
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LastCityFileTest{
    private lateinit var lastCityFile: LastCityFile

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        lastCityFile = LastCityFile(context)
    }

    @Test
    fun checkWork(){
        val data = City("en", "ua")
        lastCityFile.write(data)
        val result = lastCityFile.read()
        println("RESULT $result")

        Assert.assertTrue(data == result)
    }
}