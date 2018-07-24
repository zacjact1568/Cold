package net.zackzhang.app.cold.model.database.dao

import android.arch.persistence.room.*
import io.reactivex.Single
import net.zackzhang.app.cold.common.Constant
import net.zackzhang.app.cold.model.database.entity.DailyForecastEntity

@Dao
interface DailyForecastDao {

    @Insert
    fun insert(dailyForecastEntities: Array<DailyForecastEntity>)

    @Update
    fun update(dailyForecastEntities: Array<DailyForecastEntity>)

    @Query("DELETE FROM ${Constant.DAILY_FORECAST} WHERE ${Constant.CITY_ID} = :cityId")
    fun delete(cityId: String)

    @Query("SELECT * FROM ${Constant.DAILY_FORECAST} ORDER BY ${Constant.ADD_TIME}, ${Constant.DATE}")
    fun loadAll(): Single<Array<DailyForecastEntity>>
}