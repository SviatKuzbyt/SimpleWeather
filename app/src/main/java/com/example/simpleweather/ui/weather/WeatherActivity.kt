package com.example.simpleweather.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.lifecycle.ViewModelProvider
import com.example.simpleweather.R
import com.example.simpleweather.ui.weather.fragments.ErrorFragment
import com.example.simpleweather.ui.weather.fragments.LoadFragment
import com.example.simpleweather.ui.weather.fragments.MainInfoFragment

class WeatherActivity : AppCompatActivity() {

    private val toolBarWeather: Toolbar by lazy { findViewById(R.id.toolBarWeather) }
    private lateinit var viewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setViewOverlaps(toolBarWeather)

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        viewModel.city.observe(this){
            toolBarWeather.title = it
        }

        viewModel.mode.observe(this){
            val fragment = when(it){
                ActivityMode.Error -> ErrorFragment()
                ActivityMode.Load -> LoadFragment()
                ActivityMode.MainInfo -> MainInfoFragment()
            }

            val support = supportFragmentManager.beginTransaction()
            support.replace(R.id.fragmentWeather, fragment)
            support.setTransition(TRANSIT_FRAGMENT_FADE)
            support.commit()
        }
    }

    private fun setViewOverlaps(view: View){
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.updateLayoutParams<MarginLayoutParams> {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }
    }
}