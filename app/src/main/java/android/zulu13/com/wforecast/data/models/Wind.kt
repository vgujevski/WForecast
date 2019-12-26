package android.zulu13.com.wforecast.data.models

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class Wind(
    val name: String?,
    val direction: String?,
    val speedmin: Double?,
    val speedmax: Double?
)