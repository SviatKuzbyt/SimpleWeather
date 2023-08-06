package com.example.simpleweather.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.simpleweather.data.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CitiesDBRepositoryTest {
    private lateinit var citiesDBRepository: CitiesDBRepository
    private val testValue = City("Sambir", "Самбір")

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        citiesDBRepository = CitiesDBRepository(context, true)
    }
    @Test
    fun testAddCityAndRead() = runBlocking {
        withContext(Dispatchers.IO) {
            citiesDBRepository.addCity(testValue)
            Assert.assertTrue(testValue in citiesDBRepository.getCities())
        }
    }

    @Test
    fun testDeleteCityAndRead() = runBlocking {
        withContext(Dispatchers.IO) {
            citiesDBRepository.deleteCity(testValue.nameEn)
            Assert.assertTrue(testValue !in citiesDBRepository.getCities())
        }
    }
}

