package net.zackzhang.code.haze.air.model.network

import net.zackzhang.code.haze.air.model.entity.AirEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface AirServices {

    // TODO 改为签名
    @GET("now")
    suspend fun getNow(@Query("location") location: String, @Query("key") key: String): AirEntity
}