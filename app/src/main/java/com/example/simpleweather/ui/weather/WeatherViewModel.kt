package com.example.simpleweather.ui.weather

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simpleweather.data.elements.City
import com.example.simpleweather.data.elements.WeatherInfo
import com.example.simpleweather.data.weather.WeatherRepository
import com.example.simpleweather.ui.elements.ChangeCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

sealed class ActivityMode(){
    object MainInfo: ActivityMode()
    object Load: ActivityMode()
    object Error: ActivityMode()
}

class WeatherViewModel(application: Application): AndroidViewModel(application) {
    private val weatherRepository = WeatherRepository(application)
    val mode = MutableLiveData<ActivityMode>()
    val cityLabel = MutableLiveData<String>()
    private var cityLink = ""
    var weatherInfo: WeatherInfo? = null

    init {
        setCity(weatherRepository.getLastCity())
        loadWeather()
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun loadWeather() = viewModelScope.launch(Dispatchers.IO){
        mode.postValue(ActivityMode.Load)
        weatherInfo = weatherRepository.load(cityLink)
        if(weatherInfo == null)
            mode.postValue(ActivityMode.Error)
        else
            mode.postValue(ActivityMode.MainInfo)
    }

    fun setNewCity(){
        val city = ChangeCity.city?: City("error", "")
        setCity(city)
        loadWeather()
        weatherRepository.setLastCity(city)
    }

    private fun setCity(city: City){
        cityLabel.value = if(Locale.getDefault().language == "uk")
            city.nameUa
        else city.nameEn
        cityLink = city.nameEn
    }
}