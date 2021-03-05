package net.zackzhang.code.haze.city.data

import androidx.room.Embedded
import androidx.room.Relation
import net.zackzhang.code.haze.city.data.entity.CityEntity
import net.zackzhang.code.haze.common.Constants
import net.zackzhang.code.haze.weather.model.entity.WeatherHourlyEntity

data class CityData(
    @Embedded
    val city: CityEntity,
    @Relation(
        parentColumn = Constants.ID,
        // TODO 不行，是联合主键
        entityColumn = Constants.CITY_ID,
    )
    val now: WeatherHourlyEntity,
)
