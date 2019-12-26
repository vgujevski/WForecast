package android.zulu13.com.wforecast.data.models

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class HalfDayForecast(
    val phenomenon: String?,
    val tempmin: Int?,
    val tempmax: Int?,
    val text: String?,
    val sea: String?,
    val peipsi: String?,
    val places: List<Place>?,
    val winds: List<Wind>?
)