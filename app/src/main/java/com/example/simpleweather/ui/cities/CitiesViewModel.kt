package com.example.simpleweather.ui.cities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleweather.R
import com.example.simpleweather.data.City
import com.example.simpleweather.data.repositories.LoadCity
import com.example.simpleweather.ui.elements.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CitiesViewModel: ViewModel() {
    val citiesList = MutableLiveData<MutableList<City>>()
    val toastMessage = SingleLiveEvent<Int>()
    private val _citiesList = mutableListOf<City>()
    private val loadCity = LoadCity()

    fun addCity(name: String) = viewModelScope.launch(Dispatchers.IO){
        val result = loadCity.checkCity(name)

        if(result.nameEn != "_"){
            _citiesList.add(result)
            citiesList.postValue(_citiesList)
        }
        else if(result.nameUa == "no found")
            toastMessage.postValue(R.string.no_city)
        else toastMessage.postValue(R.string.error)
    }
}