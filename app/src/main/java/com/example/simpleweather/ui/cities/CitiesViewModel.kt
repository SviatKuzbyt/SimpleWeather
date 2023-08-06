package com.example.simpleweather.ui.cities

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleweather.R
import com.example.simpleweather.data.City
import com.example.simpleweather.data.database.CitiesDBRepository
import com.example.simpleweather.data.repositories.LoadCity
import com.example.simpleweather.ui.elements.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CitiesViewModel(application: Application): AndroidViewModel(application) {
    val citiesList = MutableLiveData<MutableList<City>>()
    val toastMessage = SingleLiveEvent<Int>()
    private val _citiesList = mutableListOf<City>()
    private val loadCity = LoadCity()
    val test = CitiesDBRepository(application)

    init {
        viewModelScope.launch(Dispatchers.IO){
            test.addCity(City("sadad", "dsada"))
            Log.v("cityyyy", test.getCities().toString())
        }
    }

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