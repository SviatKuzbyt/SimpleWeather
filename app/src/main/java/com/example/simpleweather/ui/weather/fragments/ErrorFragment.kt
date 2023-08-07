package com.example.simpleweather.ui.weather.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.simpleweather.R

const val STANDARD_ERROR = 0
const val NO_CITY_ERROR = 1
class ErrorFragment : Fragment(R.layout.fragment_error) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val errorCode = arguments?.getInt("code") ?: STANDARD_ERROR

        val errorLinkText =
            if(errorCode == NO_CITY_ERROR) R.string.select_city
            else R.string.error_content

        val errorContent = view.findViewById<TextView>(R.id.errorContent)
        errorContent.text = getString(errorLinkText)
    }
}