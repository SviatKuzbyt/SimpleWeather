package com.example.simpleweather.data.load

import com.example.simpleweather.R
import com.example.simpleweather.data.InfoDetail
import com.example.simpleweather.data.InfoMain
import com.example.simpleweather.data.WeatherInfo
import org.json.JSONObject
import java.net.URL
import java.util.Locale

class LoadWeather {
    fun getWeather(city: String): WeatherInfo? {
        return try {
            formatInfo(loadInfo(city))
        } catch (e: Exception) {
            null
        }
    }

    private fun loadInfo(city: String): String {
        val lang = Locale.getDefault().language
        val link = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY&lang=$lang&units=metric")
        return link.readText()
    }

    private fun formatInfo(text: String): WeatherInfo {
        val main = InfoMain()
        val info = mutableListOf<InfoDetail>()

        val jsonObject = JSONObject(text)

        val weatherArray = jsonObject.getJSONArray("weather")
        if (weatherArray.length() > 0) {
            val weatherObject = weatherArray.getJSONObject(0)
            main.icon = getIcon(weatherObject.getString("icon"))
            main.clearBackground = getBackground(weatherObject.getString("icon"))
            main.label = weatherObject.getString("description")
        }

        val mainObject = jsonObject.getJSONObject("main")
        main.temp = mainObject.getString("temp")
        info.add(InfoDetail(R.string.tempFeel, mainObject.getString("feels_like") + " ℃"))
        info.add(InfoDetail(R.string.pressure, mainObject.getString("pressure") + " hPa"))
        info.add(InfoDetail(R.string.humidity, mainObject.getString("humidity") + " %"))

        info.add(InfoDetail(R.string.visibility ,jsonObject.getString("visibility") + " m"))

        val windObject = jsonObject.getJSONObject("wind")
        info.add(InfoDetail(R.string.windSpeed, windObject.getString("speed") + " m/s"))

        val cloudsObject = jsonObject.getJSONObject("clouds")
        info.add(InfoDetail(R.string.clouds, cloudsObject.getString("all") + " %"))

        val rainObject = jsonObject.optJSONObject("rain")
        info.add(InfoDetail(R.string.rain, rainObject?.optString("1h", "-" + " %") ?: "-"))

        val snowObject = jsonObject.optJSONObject("snow")
        info.add(InfoDetail(R.string.snow, snowObject?.optString("1h", "-" + " %") ?: "-"))

        return WeatherInfo(main, info)
    }

    private fun getIcon(text: String): Int {
        return when (text.substring(0, 2)) {
            "01" -> R.drawable.sun
            "02" -> R.drawable.sun_cloud
            "03", "04", "50" -> R.drawable.cloud
            "09", "10" -> R.drawable.rain
            "11" -> R.drawable.thunder
            "13" -> R.drawable.snow
            else -> R.drawable.unknow
        }
    }

    private fun getBackground(text: String): Boolean {
        return text.startsWith("01") || text.startsWith("02")
    }
}
