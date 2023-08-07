package com.example.simpleweather.ui.cities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simpleweather.R
import com.example.simpleweather.data.elements.City
import com.example.simpleweather.data.cities.CitiesRepository
import com.example.simpleweather.ui.elements.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CitiesViewModel(application: Application): AndroidViewModel(application) {
    val citiesList = MutableLiveData<MutableList<City>>()
    val toastMessage = SingleLiveEvent<Int>()
    private val _citiesList = mutableListOf<City>()
    private val repository = CitiesRepository(application)

    init { loadCities() }

    private fun loadCities() = viewModelScope.launch(Dispatchers.IO){
        val cities = repository.getCities()
        if(cities.isNotEmpty()){
            _citiesList.addAll(cities)
            citiesList.postValue(_citiesList)
        }
    }

    fun addCity(name: String) = viewModelScope.launch(Dispatchers.IO){
        val result = repository.addCity(name)

        if(result.nameEn != "_"){
            _citiesList.add(result)
            citiesList.postValue(_citiesList)
        }
        else if(result.nameUa == "no found")
            toastMessage.postValue(R.string.no_city)
        else toastMessage.postValue(R.string.error)
    }

    fun deleteCity(city: City) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteCity(city)
    }
}