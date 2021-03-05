package net.zackzhang.code.haze.air.model.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.zackzhang.code.haze.common.Constant
import retrofit2.Retrofit

object AirNetworkManager {

    private const val BASE_URL = "https://devapi.qweather.com/v7/air/"

    private val SERVICES = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(AirConverterFactory())
        .build()
        .create(AirServices::class.java)

    suspend fun getAir(cityId: String) = withContext(Dispatchers.IO) {
        SERVICES.getNow(cityId, Constant.HE_WEATHER_API_KEY)
    }
}