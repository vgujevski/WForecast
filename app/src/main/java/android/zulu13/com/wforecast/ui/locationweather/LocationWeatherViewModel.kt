package android.zulu13.com.wforecast.ui.locationweather

import android.zulu13.com.wforecast.data.models.LocationWeather
import android.zulu13.com.wforecast.utils.Utils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationWeatherViewModel (location: LocationWeather) : ViewModel(){

    private var _locationName = MutableLiveData<String>()
    val locationName : LiveData<String>
        get() = _locationName

    private var _avgTempWord = MutableLiveData<String>()
    val avgTempWord : LiveData<String>
        get() = _avgTempWord

    private var _dayIcon = MutableLiveData<Int>()
    val dayIcon : LiveData<Int>
        get() = _dayIcon

    private var _dayPhenomenon = MutableLiveData<String>()
    val dayPhenomenon : LiveData<String>
        get() = _dayPhenomenon


    private var _nightIcon = MutableLiveData<Int>()
    val nightIcon : LiveData<Int>
        get() = _nightIcon

    private var _nightPhenomenon = MutableLiveData<String>()
    val nightPhenomenon : LiveData<String>
        get() = _nightPhenomenon


    init {
        _locationName.value = location.name
        _avgTempWord.value = Utils.avgCelsiusTempToWords(location.nightMinTemp ?: 0, location.dayMaxTemp ?: 0)

        _dayIcon.value = Utils.assignIcons(location.dayPhenomenon)
        _dayPhenomenon.value = location.dayPhenomenon

        _nightIcon.value = Utils.assignIcons(location.nightPhenomenon)
        _nightPhenomenon.value = location.nightPhenomenon
    }

}