package android.zulu13.com.wforecast.data.database

import android.zulu13.com.wforecast.data.models.Forecast
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.json.Json

@Entity
data class DatabaseForecast constructor(
    @PrimaryKey(autoGenerate = false)
    val forecastId: Long = 0L,
    val serializedForecast: String
)

fun DatabaseForecast.asDomainModel(): Forecast {
    return Json.parse(Forecast.serializer(), serializedForecast)
}