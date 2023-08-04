package com.example.simpleweather.ui.weather

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.simpleweather.R
import com.example.simpleweather.ui.elements.DetailAdapter
import com.example.simpleweather.ui.weather.fragments.ErrorFragment
import com.example.simpleweather.ui.weather.fragments.LoadFragment
import com.example.simpleweather.ui.weather.fragments.MainInfoFragment


class WeatherActivity : AppCompatActivity() {

    private val toolBarWeather: Toolbar by lazy { findViewById(R.id.toolBarWeather) }
    private val infoWeatherList: RecyclerView by lazy { findViewById(R.id.infoWeatherList) }
    private lateinit var viewModel: WeatherViewModel
    private val head: View by lazy { findViewById(R.id.head) }
    private val refresh: SwipeRefreshLayout by lazy { findViewById(R.id.refresh) }

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
            when(it){
                ActivityMode.Error -> postFragment(ErrorFragment())
                ActivityMode.Load -> postFragment(LoadFragment())
                ActivityMode.MainInfo ->{
                    val fragment = MainInfoFragment()

                    val res = viewModel.weatherInfo!!
                    val bundle = Bundle()
                    bundle.putInt("icon", res.main.icon)
                    bundle.putString("temp", res.main.temp)
                    bundle.putString("description", res.main.label)

                    fragment.arguments = bundle
                    postFragment(fragment)

                    val background =
                        if(res.main.clearBackground) R.drawable.background_clear
                        else R.drawable.background_cloudy
                    head.setBackgroundResource(background)

                    infoWeatherList.layoutManager = LinearLayoutManager(this)
                    infoWeatherList.adapter = DetailAdapter(res.detail, this)
                    infoWeatherList.visibility = View.VISIBLE
                }
            }
        }

        refresh.setOnRefreshListener {
            viewModel.loadWeather()
            infoWeatherList.visibility = View.INVISIBLE
            refresh.isRefreshing = false
        }

    }

    private fun postFragment(fragment: Fragment){
        val support = supportFragmentManager.beginTransaction()
        support.replace(R.id.fragmentWeather, fragment)
        support.setTransition(TRANSIT_FRAGMENT_FADE)
        support.commit()
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