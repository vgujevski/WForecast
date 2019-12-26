package android.zulu13.com.wforecast.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ForecastDao {

    @Query("SELECT * FROM databaseforecast")
    fun getAllForecasts(): LiveData<List<DatabaseForecast>>

    @Query("SELECT * FROM databaseforecast WHERE forecastId  = :key")
    fun getForecast(key: Long): LiveData<DatabaseForecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecast: DatabaseForecast)

    @Query("DELETE FROM databaseforecast")
    fun clear()
}

@Database(entities = [DatabaseForecast::class], version = 2, exportSchema = false)
abstract class ForecastsDatabase: RoomDatabase(){
    abstract val forecastDao: ForecastDao
}

private lateinit var INSTANCE: ForecastsDatabase

fun getDatabase(context: Context) : ForecastsDatabase{
    synchronized(ForecastsDatabase::class.java){
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ForecastsDatabase::class.java,
                "forecasts").build()
        }
    }
    return INSTANCE
}
