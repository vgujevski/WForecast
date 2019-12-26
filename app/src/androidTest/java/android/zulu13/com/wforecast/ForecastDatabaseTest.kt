package android.zulu13.com.wforecast

import android.zulu13.com.wforecast.data.database.DatabaseForecast
import android.zulu13.com.wforecast.data.database.ForecastDao
import android.zulu13.com.wforecast.data.database.ForecastsDatabase
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var forecastDao: ForecastDao
    private lateinit var db: ForecastsDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ForecastsDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        forecastDao = db.forecastDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetForecast() {
        val newForecast = DatabaseForecast(1L,"forecast string")
        forecastDao.insert(newForecast)
        val forecast = forecastDao.getForecast(1L)
        assertEquals(forecast.value?.serializedForecast, "forecast string")
    }

}
