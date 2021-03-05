package net.zackzhang.code.haze.weather.model.entity

import net.zackzhang.code.haze.air.model.entity.AirEntity

data class WeatherEntity(
    val now: WeatherHourlyEntity,
    val hourly: List<WeatherHourlyEntity>,
    val daily: List<WeatherDailyEntity>,
    val air: AirEntity,
) {

    fun attachCityId(cityId: String) {
        now.cityId = cityId
        hourly.forEach { it.cityId = cityId }
        daily.forEach { it.cityId = cityId }
        air.attachCityId(cityId)
    }
}
