package net.zackzhang.code.haze.weather.model.local.dao

import net.zackzhang.code.haze.weather.model.local.WeatherData

//@Dao
interface WeatherDao {

//    @Transaction
//    @Query("SELECT * FROM ${Constants.CITY} ORDER BY ${Constants.CREATED_AT}")
    fun loadAll(): List<WeatherData>
}