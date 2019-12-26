package android.zulu13.com.wforecast.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class Forecast(
    val forecasts : List<FullDayForecast>
)

@Serializable
@JsonClass(generateAdapter = true)
data class FullDayForecast(
    val date: String,
    @Json(name = "day")
    val dayForecast : HalfDayForecast,
    @Json(name = "night")
    val nightForecast: HalfDayForecast
)

fun FullDayForecast.getLocationWeather() : ArrayList<LocationWeather>{
    var  list = ArrayList<LocationWeather>()

    if(dayForecast.places != null){
        for (place in dayForecast.places){
            list.add(
                LocationWeather(
                    name = place.name,
                    dayPhenomenon = place.phenomenon,
                    dayMinTemp = place.tempmin?.toInt(),
                    dayMaxTemp = place.tempmax?.toInt()
                )
            )
        }
    }
    if (nightForecast.places != null){
        nightForecast.places.forEachIndexed { index, place ->
            list[index].nightPhenomenon = place.phenomenon
            list[index].nightMinTemp = place.tempmin?.toInt()
            list[index].nightMaxTemp = place.tempmax?.toInt()
        }
    }

    return list
}

data class LocationWeather(
    val name: String? = "",
    val dayPhenomenon: String?  = "",
    var nightPhenomenon: String?  = "",
    val dayMinTemp: Int?  = 0,
    var nightMinTemp: Int?  = 0,
    val dayMaxTemp: Int? = 0,
    var nightMaxTemp: Int? = 0
)