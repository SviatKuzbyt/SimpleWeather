package com.example.simpleweather.ui.weather

import android.content.Intent
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
import com.example.simpleweather.ui.cities.CitiesActivity
import com.example.simpleweather.ui.elements.ChangeCity
import com.example.simpleweather.ui.elements.DetailAdapter
import com.example.simpleweather.ui.weather.fragments.ErrorFragment
import com.example.simpleweather.ui.weather.fragments.LoadFragment
import com.example.simpleweather.ui.weather.fragments.MainInfoFragment
import com.example.simpleweather.ui.weather.fragments.NO_CITY_ERROR
import com.example.simpleweather.ui.weather.fragments.STANDARD_ERROR

class WeatherActivity : AppCompatActivity() {
    private val toolBarWeather: Toolbar by lazy { findViewById(R.id.toolBarWeather) }
    private val infoWeatherList: RecyclerView by lazy { findViewById(R.id.infoWeatherList) }
    private lateinit var viewModel: WeatherViewModel
    private val head: View by lazy { findViewById(R.id.head) }
    private val refresh: SwipeRefreshLayout by lazy { findViewById(R.id.refresh) }
    private lateinit var recyclerAdapter: DetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        setViews()
        setData()
    }

    private fun setViews(){
        setViewOverlaps(toolBarWeather)
        toolBarWeather.setNavigationOnClickListener {
            startActivity(Intent(this, CitiesActivity::class.java))
        }

        refresh.setColorSchemeResources(R.color.blue)
        refresh.setOnRefreshListener {
            viewModel.loadWeather()
            refresh.isRefreshing = false
        }

        infoWeatherList.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = DetailAdapter(mutableListOf(), this)
        infoWeatherList.adapter = recyclerAdapter
    }

    private fun setData(){
        viewModel.mode.observe(this){
            when(it){
                ActivityMode.Load ->{
                    infoWeatherList.visibility = View.INVISIBLE
                    postFragment(LoadFragment())
                }
                ActivityMode.Error -> makeErrorFragment(STANDARD_ERROR)
                ActivityMode.NoCity -> makeErrorFragment(NO_CITY_ERROR)

                ActivityMode.MainInfo ->{
                    val fragment = MainInfoFragment()
                    val res = viewModel.weatherInfo!!

                    val bundle = Bundle().apply {
                        putInt("icon", res.main.icon)
                        putString("temp", res.main.temp)
                        putString("description", res.main.label)
                    }

                    fragment.arguments = bundle
                    postFragment(fragment)

                    val background =
                        if(res.main.clearBackground) R.drawable.background_clear
                        else R.drawable.background_cloudy
                    head.setBackgroundResource(background)

                    recyclerAdapter.addAll(res.detail)
                    infoWeatherList.visibility = View.VISIBLE
                }
            }
        }

        viewModel.cityLabel.observe(this){
            toolBarWeather.title = it
        }
    }

    private fun makeErrorFragment(code: Int){
        val fragment = ErrorFragment()
        val bundle = Bundle()
        bundle.putInt("code", code)
        fragment.arguments = bundle
        postFragment(fragment)
    }

    override fun onRestart() {
        super.onRestart()
        if (ChangeCity.isChanging){
            ChangeCity.isChanging = false
            viewModel.setLastCityAndLoad()
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