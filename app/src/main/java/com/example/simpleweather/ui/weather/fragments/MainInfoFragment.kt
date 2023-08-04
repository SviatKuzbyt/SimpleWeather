package com.example.simpleweather.ui.weather.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.simpleweather.R

class MainInfoFragment : Fragment(R.layout.fragment_main_info) {
    private lateinit var weatherIcon: ImageView
    private lateinit var textTemp: TextView
    private lateinit var textDescription: TextView

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherIcon = view.findViewById(R.id.weatherIcon)
        textTemp = view.findViewById(R.id.textTemp)
        textDescription = view.findViewById(R.id.textDescription)

        arguments?.let { weatherIcon.setImageResource(it.getInt("icon")) }
        textTemp.text = "${arguments?.getString("temp")} ℃"
        textDescription.text = arguments?.getString("description") ?: "none"
    }
}