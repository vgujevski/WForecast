package android.zulu13.com.wforecast.ui.main

import android.zulu13.com.wforecast.R
import android.zulu13.com.wforecast.data.ForecastRepository
import android.zulu13.com.wforecast.data.models.LocationWeather
import android.zulu13.com.wforecast.utils.Utils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.UnstableDefault

private const val DAY_ONE = 0
private const val DAY_TWO = 1
private const val DAY_THREE = 2
private const val DAY_FOUR = 3

@UnstableDefault
class MainViewModel(repository: ForecastRepository) : ViewModel() {
    //<editor-fold desc="LiveData UI fields">
    // day one
    // TODO move formatting of values to binding adapters
    private var _dayOneDayIcon = MutableLiveData<Int>()
    val dayOneDayIcon: LiveData<Int>
        get() = _dayOneDayIcon

    private var _dayOneDayPhenomenon = MutableLiveData<String>()
    val dayOneDayPhenomenon: LiveData<String>
        get() = _dayOneDayPhenomenon

    private var _dayOneDayTemp = MutableLiveData<String>()
    val dayOneDayTemp: LiveData<String>
        get() = _dayOneDayTemp

    private var _dayOneNightIcon = MutableLiveData<Int>()
    val dayOneNightIcon: LiveData<Int>
        get() = _dayOneNightIcon

    private var _dayOneNightPhenomenon = MutableLiveData<String>()
    val dayOneNightPhenomenon: LiveData<String>
        get() = _dayOneNightPhenomenon

    private var _dayOneNightTemp = MutableLiveData<String>()
    val dayOneNightTemp: LiveData<String>
        get() = _dayOneNightTemp

    // day two
    private var _dayTwoDate = MutableLiveData<String>()
    val dayTwoDate: LiveData<String>
        get() = _dayTwoDate

    private var _dayTwoIcon = MutableLiveData<Int>()
    val dayTwoIcon: LiveData<Int>
        get() = _dayTwoIcon

    private var _dayTwoTemp = MutableLiveData<String>()
    val dayTwoTemp: LiveData<String>
        get() = _dayTwoTemp

    // day three
    private var _dayThreeDate = MutableLiveData<String>()
    val dayThreeDate: LiveData<String>
        get() = _dayThreeDate

    private var _dayThreeIcon = MutableLiveData<Int>()
    val dayThreeIcon: LiveData<Int>
        get() = _dayThreeIcon

    private var _dayThreeTemp = MutableLiveData<String>()
    val dayThreeTemp: LiveData<String>
        get() = _dayThreeTemp

    // day four
    private var _dayFourDate = MutableLiveData<String>()
    val dayFourDate: LiveData<String>
        get() = _dayFourDate

    private var _dayFourIcon = MutableLiveData<Int>()
    val dayFourIcon: LiveData<Int>
        get() = _dayFourIcon

    private var _dayFourTemp = MutableLiveData<String>()
    val dayFourTemp: LiveData<String>
        get() = _dayFourTemp
    //</editor-fold>

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _navigateToLocWeather = MutableLiveData<LocationWeather>()
    val navigateToLocWeather: LiveData<LocationWeather>
        get() = _navigateToLocWeather

    init {
        _navigateToLocWeather.value = null

        _dayOneDayIcon.value = R.drawable.loading
        _dayOneDayPhenomenon.value = ""
        _dayOneDayTemp.value = ""

        _dayOneNightIcon.value = R.drawable.loading
        _dayOneNightPhenomenon.value = ""
        _dayOneNightTemp.value = ""

        _dayTwoDate.value = ""
        _dayTwoIcon.value = R.drawable.loading
        _dayTwoTemp.value = ""

        _dayThreeDate.value = ""
        _dayThreeIcon.value = R.drawable.loading
        _dayThreeTemp.value = ""

        _dayFourDate.value = ""
        _dayFourIcon.value = R.drawable.loading
        _dayFourTemp.value = ""

        coroutineScope.launch {
            repository.refreshForecast()
        }
    }

    val forecast = repository.forecast

    /**  if forecast is not null , layout in main fragment will be visible*/
    val layoutVisible = Transformations.map(forecast) {
        null != it
    }

    /** display error message if forecast data is not available*/
    val errorMessageVisible = Transformations.map(forecast){
        null == it
    }

    fun updateUi() {
        _dayOneDayIcon.value =
            Utils.assignIcons(forecast.value?.forecasts?.get(DAY_ONE)?.dayForecast?.phenomenon)
        _dayOneDayPhenomenon.value =
            forecast.value?.forecasts?.get(DAY_ONE)?.dayForecast?.phenomenon
        _dayOneDayTemp.value = Utils.formatForecastTempStringCelsius(
            forecast.value?.forecasts?.get(DAY_ONE)?.dayForecast?.tempmin,
            forecast.value?.forecasts?.get(DAY_ONE)?.dayForecast?.tempmax
        )

        _dayOneNightIcon.value =
            Utils.assignIcons(forecast.value?.forecasts?.get(DAY_ONE)?.nightForecast?.phenomenon)
        _dayOneNightPhenomenon.value =
            forecast.value?.forecasts?.get(DAY_ONE)?.nightForecast?.phenomenon
        _dayOneNightTemp.value = Utils.formatForecastTempStringCelsius(
            forecast.value?.forecasts?.get(DAY_ONE)?.nightForecast?.tempmin,
            forecast.value?.forecasts?.get(DAY_ONE)?.nightForecast?.tempmax
        )

        _dayTwoDate.value =
            Utils.formatForecastDateString(forecast.value?.forecasts?.get(DAY_TWO)?.date)
        _dayTwoIcon.value =
            Utils.assignIcons(forecast.value?.forecasts?.get(DAY_TWO)?.dayForecast?.phenomenon)
        _dayTwoTemp.value = Utils.formatForecastTempStringCelsius(
            forecast.value?.forecasts?.get(DAY_TWO)?.dayForecast?.tempmin,
            forecast.value?.forecasts?.get(DAY_TWO)?.dayForecast?.tempmax
        )

        _dayThreeDate.value =
            Utils.formatForecastDateString(forecast.value?.forecasts?.get(DAY_THREE)?.date)
        _dayThreeIcon.value =
            Utils.assignIcons(forecast.value?.forecasts?.get(DAY_THREE)?.dayForecast?.phenomenon)
        _dayThreeTemp.value = Utils.formatForecastTempStringCelsius(
            forecast.value?.forecasts?.get(DAY_THREE)?.dayForecast?.tempmin,
            forecast.value?.forecasts?.get(DAY_THREE)?.dayForecast?.tempmax
        )

        _dayFourDate.value =
            Utils.formatForecastDateString(forecast.value?.forecasts?.get(DAY_FOUR)?.date)
        _dayFourIcon.value =
            Utils.assignIcons(forecast.value?.forecasts?.get(DAY_FOUR)?.dayForecast?.phenomenon)
        _dayFourTemp.value = Utils.formatForecastTempStringCelsius(
            forecast.value?.forecasts?.get(DAY_FOUR)?.dayForecast?.tempmin,
            forecast.value?.forecasts?.get(DAY_FOUR)?.dayForecast?.tempmax
        )
    }

    fun onNavigateToLocationWeather(location: LocationWeather) {
        _navigateToLocWeather.value = location
    }

    fun onNavigateToLocationWeatherCompleted() {
        _navigateToLocWeather.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
