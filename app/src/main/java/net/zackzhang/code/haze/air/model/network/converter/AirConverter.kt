package net.zackzhang.code.haze.air.model.network.converter

import net.zackzhang.code.haze.air.model.entity.AirEntity
import net.zackzhang.code.haze.air.model.entity.AirNowEntity
import net.zackzhang.code.haze.common.util.NetworkUtils
import okhttp3.ResponseBody
import retrofit2.Converter

class AirConverter : Converter<ResponseBody, AirEntity> {

    override fun convert(value: ResponseBody): AirEntity {
        val response = NetworkUtils.responseBodyToJsonObject(value)
        val updateTime = NetworkUtils.getUpdateTime(response)
        val now = NetworkUtils.fromJsonObject(response.getJSONObject("now"), AirNowEntity::class).apply {
            updatedAt = updateTime
        }
        val stations = NetworkUtils.fromJsonArray(response.getJSONArray("station"), AirNowEntity::class)
        stations.forEach { it.updatedAt = updateTime }
        return AirEntity(now, stations)
    }
}