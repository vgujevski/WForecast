package android.zulu13.com.wforecast.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.zulu13.com.wforecast.R
import android.zulu13.com.wforecast.data.models.getLocationWeather
import android.zulu13.com.wforecast.databinding.MainFragmentBinding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.serialization.UnstableDefault

@UnstableDefault
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = SleepTrackerViewModelFactory(application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        val listAdapter = LocationWeatherAdapter(LocationWeatherListener {location ->
            viewModel.onNavigateToLocationWeather(location)
        })

        binding.listLocationWeather?.adapter = listAdapter

        viewModel.navigateToLocWeather.observe(this, Observer {
            it?.let {
                this.findNavController().navigate(MainFragmentDirections.actionMainFragmentToLocationWeatherFragment(it))
                viewModel.onNavigateToLocationWeatherCompleted()
            }
        })

        viewModel.forecast.observe(this, Observer {
            it?.let {
                viewModel.updateUi()
                listAdapter.data = it.forecasts[0].getLocationWeather()
            }
        })

        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}
