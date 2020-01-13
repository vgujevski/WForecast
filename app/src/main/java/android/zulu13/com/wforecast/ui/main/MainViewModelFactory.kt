package android.zulu13.com.wforecast.ui.main

import android.zulu13.com.wforecast.data.ForecastRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SleepTrackerViewModelFactory(
    private val repository: ForecastRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}