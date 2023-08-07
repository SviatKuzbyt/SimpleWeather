package com.example.simpleweather.ui.weather

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
    object NoCity: ActivityMode()
}

class WeatherViewModel(application: Application): AndroidViewModel(application) {
    val mode = MutableLiveData<ActivityMode>()
    val cityLabel = MutableLiveData<String>()
    private val weatherRepository = WeatherRepository(application)
    private var cityLink = ""
    var weatherInfo: WeatherInfo? = null

    init {
        val city = weatherRepository.getLastCity()
        if (city != null){
            setCity(city)
            loadWeather()
        } else mode.value = ActivityMode.NoCity
    }

    fun loadWeather() = viewModelScope.launch(Dispatchers.IO){
        mode.postValue(ActivityMode.Load)
        weatherInfo = weatherRepository.load(cityLink)

        if(weatherInfo == null)
            mode.postValue(ActivityMode.Error)
        else
            mode.postValue(ActivityMode.MainInfo)
    }

    fun setLastCityAndLoad(){
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