package android.zulu13.com.wforecast.ui.locationweather

import android.zulu13.com.wforecast.data.models.LocationWeather
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LocationWeatherModelFactory(
    private val location: LocationWeather) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationWeatherViewModel::class.java)) {
            return LocationWeatherViewModel(location) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}