package android.zulu13.com.wforecast.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.zulu13.com.wforecast.data.models.Forecast
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData

private const val DAY_ONE = 0
private const val DAY_TWO = 1
private const val DAY_THREE = 2
private const val DAY_FOUR = 3

@BindingAdapter("imageResource")
fun setImageViewResource(view: ImageView, resId: Int) {
    view.setImageResource(resId)
}

@BindingAdapter("layoutVisible")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("weatherIcon", "dayNumber", "isDay")
fun setWeatherIcon(view: ImageView, forecast: LiveData<Forecast>, dayNumber: Int, isDay: Boolean){
    if(isDay){
        view.setImageResource(Utils.assignIcons(forecast.value?.forecasts?.get(getDayIndex(dayNumber))?.dayForecast?.phenomenon))
    }else{
        view.setImageResource(Utils.assignIcons(forecast.value?.forecasts?.get(getDayIndex(dayNumber))?.nightForecast?.phenomenon))
    }

}

@BindingAdapter("tempText", "dayNumber", "isDay")
fun setTempText(view: TextView, forecast: LiveData<Forecast>, dayNumber: Int, isDay: Boolean){
    if(isDay){
        view.text = Utils.formatForecastTempStringCelsius(
            forecast.value?.forecasts?.get(getDayIndex(dayNumber))?.dayForecast?.tempmin,
            forecast.value?.forecasts?.get(getDayIndex(dayNumber))?.dayForecast?.tempmax
        )
    }else{
        view.text = Utils.formatForecastTempStringCelsius(
            forecast.value?.forecasts?.get(getDayIndex(dayNumber))?.nightForecast?.tempmin,
            forecast.value?.forecasts?.get(getDayIndex(dayNumber))?.nightForecast?.tempmax
        )
    }
}

@BindingAdapter("phenomenonText", "dayNumber", "isDay")
fun setPhenomenonText(view: TextView, forecast: LiveData<Forecast>, dayNumber: Int, isDay: Boolean){
    if(isDay){
        view.text =
            forecast.value?.forecasts?.get(getDayIndex(dayNumber))?.dayForecast?.phenomenon
    }else{
        view.text =
            forecast.value?.forecasts?.get(getDayIndex(dayNumber))?.nightForecast?.phenomenon
    }
}

@BindingAdapter("date", "dayNumber")
fun setDate(view: TextView, forecast: LiveData<Forecast>, dayNumber: Int){
    view.text = Utils.formatForecastDateString(forecast.value?.forecasts?.get(getDayIndex(dayNumber))?.date)
}
/**
 *  Returns day index assuming it is a 4 day forecast
 * */
private fun getDayIndex(dayNumber: Int) : Int{
    return when(dayNumber) {
        1 -> DAY_ONE
        2 -> DAY_TWO
        3 -> DAY_THREE
        4 -> DAY_FOUR
        else -> {
            DAY_ONE
            Log.e("getDayIndex", "list out of bounds, returned 0 as default")
        }
    }
}