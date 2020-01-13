package android.zulu13.com.wforecast.data

import android.util.Log
import android.zulu13.com.wforecast.data.database.DatabaseForecast
import android.zulu13.com.wforecast.data.database.ForecastsDatabase
import android.zulu13.com.wforecast.data.database.asDomainModel
import android.zulu13.com.wforecast.data.models.Forecast
import android.zulu13.com.wforecast.data.network.WeatherApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

private const val KEY = 1L

@UnstableDefault
class ForecastRepository(private val database: ForecastsDatabase) {

    val forecast: LiveData<Forecast> = Transformations
        .map(database.forecastDao.getForecast(KEY)) {
            it?.asDomainModel()
        }

    @UnstableDefault
    suspend fun refreshForecast() {
        withContext(Dispatchers.IO) {
            Log.i("ForecastRepository", "refreshForecast called")
            try {
                val forecast = WeatherApi.retrofitService.getPropertiesAsync().await()
                val string = Json.stringify(Forecast.serializer(), forecast)
                val freshForecast = DatabaseForecast(KEY, string)
                database.forecastDao.insert(freshForecast)
            } catch (e: Exception) {
                //_status.value = "Failure: ${e.message}"
                Log.i("ForecastRepository", "Error: $e")
            }
        }
    }
}