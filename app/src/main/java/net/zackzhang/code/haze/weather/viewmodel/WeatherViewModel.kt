package net.zackzhang.code.haze.weather.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import net.zackzhang.code.haze.weather.model.network.WeatherNetworkManager
import net.zackzhang.code.haze.weather.viewmodel.data.BaseCardData
import net.zackzhang.code.haze.weather.viewmodel.data.HeadCardData

class WeatherViewModel : ViewModel() {

    val weatherLiveData by lazy {
        MutableLiveData<MutableList<BaseCardData>>().apply {
            value = mutableListOf()
        }
    }

//    fun getNow(cityId: String) = liveData(Dispatchers.IO) {
//        val data = WeatherNetworkManager.getNow(cityId)
//        emit(data)
//    }

    fun getNow(cityId: String) {
        viewModelScope.launch {
            // TODO 可以考虑缓存该数据
            val weather = WeatherNetworkManager.getWeather(cityId)
            weatherLiveData.update(listOf(HeadCardData(
                weather.now.temperature ?: -1,
                weather.now.conditionName ?: "-1",
                weather.air.now.index.toString(),
                0..1
            )))
//            try {
//                val data = WeatherNetworkManager.getNow(cityId).runCatching { this }
//                data.fold({
//
//                }, {
//
//                })
//                now.value = data
//            } catch (e: RuntimeException) {
//                LogUtil.d("**********", "exception")
//                now.value = null
//            }
        }
    }

    private fun MutableLiveData<MutableList<BaseCardData>>.update(cardDataList: List<BaseCardData>) {
        value = value?.apply {
            clear()
            addAll(cardDataList)
        }
    }
}