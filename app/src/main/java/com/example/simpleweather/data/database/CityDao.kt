package com.example.simpleweather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.simpleweather.data.City

@Dao
interface CityDao {
    @Query("SELECT nameEn, nameUa FROM CityEntity")
    fun getCities(): MutableList<City>

    @Insert
    fun addCity(city: CityEntity)

    @Query("DELETE FROM CityEntity WHERE nameEn=:city")
    fun delete(city: String)
}