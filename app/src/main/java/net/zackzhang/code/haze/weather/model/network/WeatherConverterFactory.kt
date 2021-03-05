package net.zackzhang.code.haze.weather.model.network

import net.zackzhang.code.haze.weather.model.entity.WeatherDailyEntity
import net.zackzhang.code.haze.weather.model.entity.WeatherHourlyEntity
import net.zackzhang.code.haze.weather.model.network.converter.WeatherDailyConverter
import net.zackzhang.code.haze.weather.model.network.converter.WeatherHourlyConverter
import net.zackzhang.code.haze.weather.model.network.converter.WeatherNowConverter
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class WeatherConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return when (type) {
            WeatherHourlyEntity::class.java -> WeatherNowConverter()
            is ParameterizedType -> when (type.actualTypeArguments[0]) {
                WeatherHourlyEntity::class.java -> WeatherHourlyConverter()
                WeatherDailyEntity::class.java -> WeatherDailyConverter()
                else -> null
            }
            else -> null
        }
    }
}