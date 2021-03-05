package net.zackzhang.code.haze.weather.viewmodel.data

import net.zackzhang.code.haze.common.Constants

data class HeadCardData(
    var temperatureNow: Int,
    var condition: String,
    var airQuality: String,
    var temperatureRange: IntRange
) : BaseCardData(Constants.CARD_TYPE_WEATHER_HEAD)