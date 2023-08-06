package com.example.simpleweather.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nameEn: String,
    val nameUa: String
)