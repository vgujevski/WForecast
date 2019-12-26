package android.zulu13.com.wforecast.utils

import android.zulu13.com.wforecast.R
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    /**
     *  Calculating average and returning temperature in words
     *  i.e. The average temperature today is "minus twenty five degrees"  celsius.
     * */

    fun avgCelsiusTempToWords(minTemp: Int, maxTemp: Int) : String{
        val prefix = "The average temperature today is "
        val postfix = " celsius."
        var avg = (minTemp + maxTemp) / 2
        var minus = ""
        var degrees = " degrees"
        if(avg == 0){
            return "Zero degrees"
        }
        if(avg < 0){
            minus = "minus"
            avg = -avg
        }
        if(avg == 1){
            degrees = " degree"
        }
        if(avg < 0){
            minus = "minus"
            avg = -avg
        }
        val tensNames = listOf(
            "",
            " ten",
            " twenty",
            " thirty",
            " forty",
            " fifty",
            " sixty",
            " seventy",
            " eighty",
            " ninety"
        )
        val numNames = listOf(
            "",
            " one",
            " two",
            " three",
            " four",
            " five",
            " six",
            " seven",
            " eight",
            " nine",
            " ten",
            " eleven",
            " twelve",
            " thirteen",
            " fourteen",
            " fifteen",
            " sixteen",
            " seventeen",
            " eighteen",
            " nineteen"
        )

        var soFar: String

        if (avg % 100 < 20) {
            soFar = numNames[avg % 100]
            avg /= 100
        } else {
            soFar = numNames[avg % 10]
            avg /= 10
            soFar = tensNames[avg % 10] + soFar
            avg /= 10
        }

        return if (avg == 0) "$prefix $minus $soFar $degrees $postfix" else prefix + minus + numNames[avg] + " hundred" + soFar + degrees + postfix
    }
    /**
     *  Formats 2019-12-23 to Mon, Dec 23
     * */
    @JvmStatic
    fun formatForecastDateString(fullDayForecast: String?) : String{
        if(fullDayForecast == null){
            return ""
        }
        val sdf = SimpleDateFormat("EEE, MMM d", Locale.ENGLISH) // Mon, Dec 23
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(fullDayForecast)

        return sdf.format(date)
    }

    /**
     *  Returns -5/2 (Celsius sign) format
     * */
    @JvmStatic
    fun formatForecastTempStringCelsius(minTemp: Int?, maxTemp: Int?) : String{
        //TODO handle null parameters
        return if(minTemp == null && maxTemp == null){
            ""
        }else if(minTemp == null && maxTemp != null){
            "$maxTemp\u2103"
        }else if(minTemp != null && maxTemp == null){
            "$minTemp\u2103"
        }else if(minTemp != null && maxTemp != null && minTemp < maxTemp){
            "$minTemp/$maxTemp\u2103"
        }else if(minTemp != null && maxTemp != null && minTemp == maxTemp){
            "$minTemp\u2103"
        } else{
            "$maxTemp/$minTemp\u2103"
        }
    }


    /** Returns resource id depending on phenomenon */
    @JvmStatic
    fun assignIcons(phenomenon : String?) : Int{
        return when(phenomenon){
            "Clear" -> R.drawable.very_sunny
            "Few clouds" -> R.drawable.sunny
            "Variable clouds" -> R.drawable.clouds
            "Cloudy with clear spells" -> R.drawable.sunny
            "Cloudy" -> R.drawable.cloudy
            "Light snow shower" -> R.drawable.snowing
            "Moderate snow shower" -> R.drawable.snowing
            "Heavy snow shower" -> R.drawable.snowing
            "Light shower" -> R.drawable.shower
            "Moderate shower" -> R.drawable.shower
            "Heavy shower" -> R.drawable.shower
            "Light rain" -> R.drawable.rain
            "Moderate rain" -> R.drawable.rain
            "Heavy rain" -> R.drawable.rain
            "Risk of glaze" -> R.drawable.glaze
            "Light sleet" -> R.drawable.sleet
            "Moderate sleet" -> R.drawable.sleet
            "Light snowfall" -> R.drawable.snowing
            "Moderate snowfall" -> R.drawable.snowing
            "Heavy snowfall" -> R.drawable.snowing
            "Snowstorm" -> R.drawable.snow_storm
            "Drifting snow" -> R.drawable.snow_storm
            "Hail" -> R.drawable.snowing
            "Mist" -> R.drawable.fog_mist
            "Fog" -> R.drawable.fog_mist
            "Thunder" -> R.drawable.storms
            "Thunderstorm" -> R.drawable.storms
            else -> R.drawable.loading
        }
    }
}