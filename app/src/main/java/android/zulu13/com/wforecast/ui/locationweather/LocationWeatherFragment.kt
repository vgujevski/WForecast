package android.zulu13.com.wforecast.ui.locationweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.zulu13.com.wforecast.R
import android.zulu13.com.wforecast.databinding.LocationWeatherFragmentBinding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class LocationWeatherFragment : Fragment() {
    private lateinit var locationViewModel: LocationWeatherViewModel
    private lateinit var binding: LocationWeatherFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // TODO fix overlapping views on small screen sizes
        binding =
            DataBindingUtil.inflate(inflater, R.layout.location_weather_fragment, container, false)
        val arguments = LocationWeatherFragmentArgs.fromBundle(arguments!!)
        val viewModelFactory = LocationWeatherModelFactory(arguments.location)
        locationViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(LocationWeatherViewModel::class.java)

        binding.locationWeatherViewModel = locationViewModel

        return binding.root
    }
}