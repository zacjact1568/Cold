package me.imzack.app.cold.presenter

import me.imzack.app.cold.App
import me.imzack.app.cold.R
import me.imzack.app.cold.common.Constant
import me.imzack.app.cold.event.CityDeletedEvent
import me.imzack.app.cold.event.WeatherUpdateStatusChangedEvent
import me.imzack.app.cold.model.DataManager
import me.imzack.app.cold.model.bean.FormattedWeather
import me.imzack.app.cold.model.bean.Weather
import me.imzack.app.cold.util.ResourceUtil
import me.imzack.app.cold.util.SystemUtil
import me.imzack.app.cold.util.TimeUtil
import me.imzack.app.cold.util.WeatherUtil
import me.imzack.app.cold.view.contract.WeatherPageViewContract
import org.greenrobot.eventbus.Subscribe

class WeatherPagePresenter(private var weatherPageViewContract: WeatherPageViewContract?, weatherListPosition: Int) : BasePresenter() {

    private val weather = DataManager.getWeather(weatherListPosition)
    private val eventBus = App.eventBus
    
    //private var isVisible = false

    override fun attach() {
        eventBus.register(this)
        weatherPageViewContract!!.showInitialView(formattedWeather)
    }

    override fun detach() {
        weatherPageViewContract = null
        eventBus.unregister(this)
    }

    fun notifyWeatherUpdating() {
        if (SystemUtil.isNetworkAvailable) {
            DataManager.getWeatherDataFromInternet(weather.basic.cityId)
        } else {
            //不会和HomeActivity、MyCitiesFragment中的SnackBar同时出现
            weatherPageViewContract!!.onDetectedNetworkNotAvailable()
        }
    }

    fun notifyVisibilityChanged(isVisible: Boolean) {
        //this.isVisible = isVisible;
        //如果当前fragment变为不可见，直接隐藏下拉刷新图标；如果当前fragment变为可见，且又是在刷新状态时，显示下拉刷新图标
        weatherPageViewContract!!.onChangeSwipeRefreshingStatus(isVisible && weather.status == Weather.STATUS_ON_UPDATING)
    }

    private val formattedWeather: FormattedWeather
        get() {
            return if (weather.basic.updateTime == 0L) {
                //说明数据为空
                FormattedWeather(weather.basic.cityName)
            } else {
                FormattedWeather(
                        weather.basic.cityName,
                        DataManager.getConditionByCode(weather.current.conditionCode),
                        weather.current.temperature.toString(),
                        String.format(ResourceUtil.getString(R.string.text_update_time), TimeUtil.formatTime(weather.basic.updateTime)),
                        weather.current.feelsLike.toString(),
                        // 今天的最高温和最低温
                        "${weather.dailyForecasts[0].temperatureMin} | ${weather.dailyForecasts[0].temperatureMax}",
                        if (weather.current.airQualityIndex == 0) Constant.UNKNOWN_DATA else WeatherUtil.parseAqi(weather.current.airQualityIndex),
                        TimeUtil.getWeeks(weather.dailyForecasts[0].date, Weather.DAILY_FORECAST_LENGTH_DISPLAY),
                        Array(Weather.DAILY_FORECAST_LENGTH_DISPLAY) { DataManager.getConditionByCode(weather.dailyForecasts[it].conditionCodeDay) },
                        IntArray(Weather.DAILY_FORECAST_LENGTH_DISPLAY) { weather.dailyForecasts[it].temperatureMax },
                        IntArray(Weather.DAILY_FORECAST_LENGTH_DISPLAY) { weather.dailyForecasts[it].temperatureMin }
                )
            }
        }

    /** 判断是否不是当前城市 */
    private fun isNotThisCity(cityId: String) = weather.basic.cityId != cityId

    @Subscribe
    fun onWeatherUpdateStatusChanged(event: WeatherUpdateStatusChangedEvent) {
        // 当 WeatherUpdated 事件发生时，这个方法总会被调用，但 MyCitiesPresenter 中的订阅方法不一定被执行
        // 如果更新事件不是针对当前城市的，返回
        if (isNotThisCity(event.cityId)) return
        when (event.status) {
            WeatherUpdateStatusChangedEvent.STATUS_ON_UPDATING -> { }
            WeatherUpdateStatusChangedEvent.STATUS_UPDATED_SUCCESSFUL -> weatherPageViewContract!!.onWeatherUpdatedSuccessfully(formattedWeather)
            WeatherUpdateStatusChangedEvent.STATUS_UPDATED_FAILED -> weatherPageViewContract!!.onWeatherUpdatedAbortively()
        }
        //weatherPageViewContract!!.onChangeSwipeRefreshingStatus(isVisible) TODO 未触发，isVisible为false
    }

    @Subscribe
    fun onCityDeleted(event: CityDeletedEvent) {
        // 如果删除事件不是针对当前城市的，返回
        if (isNotThisCity(event.cityId)) return
        weatherPageViewContract!!.onCityDeleted()
    }
}