package net.zackzhang.code.haze.common.model.entity

import androidx.room.ColumnInfo
import net.zackzhang.code.haze.common.Constants
import java.time.ZonedDateTime

abstract class BaseEntity(
    // TODO 设定默认值和非空没用，gson 解析后还是会置为 null
    @ColumnInfo(name = Constants.CITY_ID)
    var cityId: String = "",
    @ColumnInfo(name = Constants.UPDATED_AT)
    var updatedAt: ZonedDateTime? = null,
)