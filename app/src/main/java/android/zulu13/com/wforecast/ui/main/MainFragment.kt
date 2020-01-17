package android.zulu13.com.wforecast.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.zulu13.com.wforecast.R
import android.zulu13.com.wforecast.data.ForecastRepository
import android.zulu13.com.wforecast.data.database.getDatabase
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
        //TODO fix overlapping view on small screen sizes
        //TODO add error message to landscape layout

        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val database = getDatabase(application)
        val repository = ForecastRepository(database)

        val viewModelFactory = SleepTrackerViewModelFactory(repository)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        val listAdapter = LocationWeatherAdapter(LocationWeatherListener { location ->
            viewModel.onNavigateToLocationWeather(location)
        })

        binding.listLocationWeather?.adapter = listAdapter

        viewModel.navigateToLocWeather.observe(this, Observer {
            it?.let {
                this.findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToLocationWeatherFragment(it))
                viewModel.onNavigateToLocationWeatherCompleted()
            }
        })

        viewModel.forecast.observe(this, Observer {
            it?.let {
                listAdapter.data = it.forecasts[0].getLocationWeather()
            }
        })

        viewModel.refreshData.observe(this, Observer { shouldRefresh ->
            if (shouldRefresh) {
                viewModel.refreshData()
                viewModel.onRefreshDataCompleted()
            }
        })

        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_main_refresh -> {
                Log.i("main_menu", "refresh clicked")
                viewModel.onRefreshData()
                //viewModel.refreshData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
