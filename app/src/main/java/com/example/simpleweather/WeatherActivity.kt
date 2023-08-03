package com.example.simpleweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.google.android.material.appbar.AppBarLayout

class WeatherActivity : AppCompatActivity() {

    private val appBarLayout: AppBarLayout by lazy { findViewById(R.id.appBarLayout) }
    private val toolBarWeather: Toolbar by lazy { findViewById(R.id.toolBarWeather) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setViewsOverlaps(toolBarWeather)
    }

    private fun setViewsOverlaps(view: View){
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