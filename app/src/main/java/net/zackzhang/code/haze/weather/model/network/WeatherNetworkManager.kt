package net.zackzhang.code.haze.weather.model.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import net.zackzhang.code.haze.air.model.network.AirNetworkManager
import net.zackzhang.code.haze.common.Constant
import net.zackzhang.code.haze.weather.model.entity.WeatherEntity
import retrofit2.Retrofit

object WeatherNetworkManager {

    private const val BASE_URL = "https://devapi.qweather.com/v7/weather/"

    private val SERVICES = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(WeatherConverterFactory())
        .build()
        .create(WeatherServices::class.java)

    suspend fun getWeather(cityId: String) = withContext(Dispatchers.IO) {
        val now = async { SERVICES.getNow(cityId, Constant.HE_WEATHER_API_KEY) }
        val hourly = async { SERVICES.getHourly(cityId, Constant.HE_WEATHER_API_KEY) }
        val daily = async { SERVICES.getDaily(cityId, Constant.HE_WEATHER_API_KEY) }
        val air = async { AirNetworkManager.getAir(cityId) }
        WeatherEntity(now.await(), hourly.await(), daily.await(), air.await()).apply {
            attachCityId(cityId)
        }
    }
}