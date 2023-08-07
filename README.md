# SimpleWeather
Application to display **current weather** conditions. Displays both general data and detailed data, such as pressure. The data is displayed **by city**, which you can add and switch between them.

## Features
- The program consists of two activities: `WeatherActivity` and `CitiesActivity`
- **WeatherActivty** - initial activity where **data is displayed**. Consists of `ToolBar`, where the city is written; a `fragment` that displays basic information, download status or errors; `recyclerView` that displays details
- **CitiesActivity** - an activity where you can **choose a city** to search for data. Consists of `ToolBar`, `EditText` and `RecyclerView`. Before adding a city, it is checked whether it is in the API database and added to the database using `Room`
- The name of the current city is **saved to a file** to display data about it the next time you open the application
- Uses API [openweathermap.org](https://openweathermap.org/current) to get data
- Used recommended architecture, MVVM principle, coroutines, Room

## Screenshots
| ![WeatherActivty](https://github.com/SviatKuzbyt/SimpleWeather/blob/main/screenshots/weather.jpg) | ![CitiesActivity](https://github.com/SviatKuzbyt/SimpleWeather/blob/main/screenshots/cities.jpg) |
|---|---|

## How to install?
Go to the latest [Release](https://github.com/SviatKuzbyt/SimpleWeather/releases/tag/1.0) and download and install the file `app-release.apk`
