package net.zackzhang.code.haze.weather.model.network

import net.zackzhang.code.haze.weather.model.entity.WeatherDailyEntity
import net.zackzhang.code.haze.weather.model.entity.WeatherHourlyEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServices {

    // TODO 改为签名
    @GET("now")
    suspend fun getNow(@Query("location") location: String, @Query("key") key: String): WeatherHourlyEntity

    // TODO 改成 array？
    @GET("24h")
    suspend fun getHourly(@Query("location") location: String, @Query("key") key: String): List<WeatherHourlyEntity>

    @GET("7d")
    suspend fun getDaily(@Query("location") location: String, @Query("key") key: String): List<WeatherDailyEntity>
}