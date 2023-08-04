package com.example.simpleweather.data

import com.example.simpleweather.R

data class WeatherInfo(
    val main: InfoMain,
    val detail: List<InfoDetail>
)

data class InfoMain(
    var icon: Int = R.drawable.unknow,
    var clearBackground: Boolean = true,
    var label: String = "-",
    var temp: String = "-"
)

data class InfoDetail(
    val label: Int,
    val value: String
)