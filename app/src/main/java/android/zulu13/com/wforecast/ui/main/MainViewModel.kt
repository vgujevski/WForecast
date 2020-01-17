package android.zulu13.com.wforecast.ui.main

import android.zulu13.com.wforecast.data.ForecastRepository
import android.zulu13.com.wforecast.data.models.LocationWeather
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.UnstableDefault

@UnstableDefault
class MainViewModel(val repository: ForecastRepository) : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _navigateToLocWeather = MutableLiveData<LocationWeather>()
    val navigateToLocWeather: LiveData<LocationWeather>
        get() = _navigateToLocWeather

    private val _refreshData = MutableLiveData<Boolean>()
    val refreshData: LiveData<Boolean>
        get() = _refreshData

    init {
        _navigateToLocWeather.value = null
        _refreshData.value = false

        //refreshData()

        coroutineScope.launch {
            //repository.refreshForecast()
            refreshData()
        }
    }

    val forecast = repository.forecast

    /**  if forecast is not null , layout in main fragment will be visible*/
    val layoutVisible = Transformations.map(forecast) {
        null != it
    }

    /** display error message if forecast data is not available*/
    val errorMessageVisible = Transformations.map(forecast) {
        null == it
    }

    fun onNavigateToLocationWeather(location: LocationWeather) {
        _navigateToLocWeather.value = location
    }

    fun onNavigateToLocationWeatherCompleted() {
        _navigateToLocWeather.value = null
    }

    fun onRefreshData(){
        _refreshData.value = true
    }

    fun onRefreshDataCompleted(){
        _refreshData.value = false
    }

    fun refreshData(){
        coroutineScope.launch {
            repository.refreshForecast()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
