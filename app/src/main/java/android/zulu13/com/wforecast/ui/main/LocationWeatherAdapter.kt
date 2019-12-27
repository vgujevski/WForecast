package android.zulu13.com.wforecast.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.zulu13.com.wforecast.data.models.LocationWeather
import android.zulu13.com.wforecast.databinding.PlaceListItemBinding
import android.zulu13.com.wforecast.utils.Utils
import androidx.recyclerview.widget.RecyclerView


class LocationWeatherAdapter(val clickListener: LocationWeatherListener) : RecyclerView.Adapter<LocationWeatherAdapter.ViewHolder>(){
    var data = listOf<LocationWeather>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(clickListener, item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder private constructor(val binding: PlaceListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: LocationWeatherListener, item: LocationWeather){

            binding.location = item
            binding.textPlaceName.text = item.name
            binding.textPlacePhenomenon.text = item.dayPhenomenon
            binding.textPlaceTemp.text =
                Utils.formatForecastTempStringCelsius(item.nightMinTemp, item.dayMaxTemp)

            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PlaceListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class LocationWeatherListener(val clickListener: (location : LocationWeather) -> Unit){

    fun onClick(location : LocationWeather) = clickListener(location)
}