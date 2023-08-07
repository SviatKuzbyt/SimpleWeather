package com.example.simpleweather.ui.cities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleweather.R
import com.example.simpleweather.ui.elements.CitiesAdapter

class CitiesActivity : AppCompatActivity() {

    private val toolbarCities: Toolbar by lazy { findViewById(R.id.toolbarCities) }
    private val editTextCity: EditText by lazy { findViewById(R.id.editTextCity) }
    private val recyclerCities: RecyclerView by lazy { findViewById(R.id.recyclerCities) }
    private lateinit var viewModel: CitiesViewModel
    private lateinit var recyclerAdapter: CitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)

        viewModel = ViewModelProvider(this)[CitiesViewModel::class.java]
        setViews()
        setData()
    }

    private fun setViews(){
        toolbarCities.setNavigationOnClickListener { finish() }

        editTextCity.setOnEditorActionListener{_, _, _ ->
            hideKeyboardFrom(editTextCity)
            viewModel.addCity(editTextCity.text.toString())
            editTextCity.setText("")
            editTextCity.clearFocus()
            true
        }

        recyclerAdapter = CitiesAdapter(mutableListOf(), viewModel, this)
        recyclerCities.layoutManager = LinearLayoutManager(this)
        recyclerCities.adapter = recyclerAdapter
    }

    private fun setData(){
        viewModel.citiesList.observe(this){
            if(recyclerAdapter.itemCount == 0)
                recyclerAdapter.addAll(it)
            else
                recyclerAdapter.notifyItemInserted(recyclerAdapter.itemCount-1)
        }

        viewModel.toastMessage.observe(this){
            Toast.makeText(this, getString(it), Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideKeyboardFrom(view: View) {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}