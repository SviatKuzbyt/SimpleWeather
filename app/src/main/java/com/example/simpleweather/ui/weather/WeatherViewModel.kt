package com.example.simpleweather.ui.weather

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simpleweather.data.WeatherInfo
import com.example.simpleweather.data.repositories.LoadWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class ActivityMode(){
    object MainInfo: ActivityMode()
    object Load: ActivityMode()
    object Error: ActivityMode()
}

class WeatherViewModel(application: Application): AndroidViewModel(application) {
    private val loadWeather = LoadWeather()
    val mode = MutableLiveData<ActivityMode>()
    val city = MutableLiveData<String>()
    val weatherInfo = MutableLiveData<WeatherInfo>()

    init {
        setCity("Sambir")
        loadWeather()
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun loadWeather() = viewModelScope.launch(Dispatchers.IO){
        mode.postValue(ActivityMode.Load)
        val result = loadWeather.getWeather(city.value ?: "")
        if(result == null)
            mode.postValue(ActivityMode.Error)
        else{
            weatherInfo.postValue(result)
            mode.postValue(ActivityMode.MainInfo)
        }
    }

    fun setCity(name: String){
        city.value = name
    }
}