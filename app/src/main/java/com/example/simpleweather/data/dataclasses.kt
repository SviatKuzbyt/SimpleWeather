package com.example.simpleweather.data

import com.example.simpleweather.R

data class WeatherInfo(
    val main: InfoMain,
    val detail: InfoDetail
)

data class InfoMain(
    var icon: Int = R.drawable.unknow,
    var clearBackground: Boolean = true,
    var label: String = "-",
    var temp: String = "-"
)

data class InfoDetail(
    var tempFeel: String = "-",
    var windSpeed: String = "-",
    var pressure: String = "-",
    var humidity: String = "-",
    var visibility: String = "-",
    var clouds: String = "-",
    var rain: String = "-",
    var snow: String = "-",
)