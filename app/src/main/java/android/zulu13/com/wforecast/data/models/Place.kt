package android.zulu13.com.wforecast.data.models

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class Place(
    val name: String?,
    val phenomenon: String?,
    val tempmin: Double?,
    val tempmax: Double?
)