package me.imzack.app.cold.view.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_weather_page.*
import me.imzack.app.cold.R
import me.imzack.app.cold.common.Constant
import me.imzack.app.cold.model.bean.FormattedWeather
import me.imzack.app.cold.presenter.WeatherPagePresenter
import me.imzack.app.cold.view.contract.WeatherPageViewContract

class WeatherPageFragment : BaseFragment(), WeatherPageViewContract {

    companion object {

        fun newInstance(weatherListPosition: Int): WeatherPageFragment {
            val fragment = WeatherPageFragment()
            val args = Bundle()
            args.putInt(Constant.WEATHER_LIST_POSITION, weatherListPosition)
            fragment.arguments = args
            return fragment
        }
    }

    private val weatherPagePresenter by lazy {
        WeatherPagePresenter(this, position)
    }

    /**
     * 将此属性置为 true 表示此 page 的位置已改变，为下次刷新 ViewPager 做准备。
     * 在下次刷新 ViewPager 时，此属性又会被置为 false
     */
    var isPositionChanged = false

    /** 将此属性置为 true 表示此 page 对应的城市已被删除，在下次刷新 ViewPager 时将被 destroy */
    var isCityDeleted = false

    /** 此 page 在 ViewPager 中的位置 */
    var position
        get() = arguments!!.getInt(Constant.WEATHER_LIST_POSITION, -1)
        set(value) = arguments!!.putInt(Constant.WEATHER_LIST_POSITION, value)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_weather_page, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherPagePresenter.attach()
    }

    override fun onDetach() {
        super.onDetach()
        weatherPagePresenter.detach()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isResumed) {
            // 当进入 resume 状态时，weatherPagePresenter 一定不为 null
            weatherPagePresenter.notifyVisibilityChanged(isVisibleToUser)
        }
    }

    override fun showInitialView(formattedWeather: FormattedWeather) {
        vSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        vSwipeRefreshLayout.setOnRefreshListener { weatherPagePresenter.notifyWeatherUpdating() }
        setWeatherOnView(formattedWeather)
    }

    override fun onDetectedNetworkNotAvailable() {
        vSwipeRefreshLayout.isRefreshing = false
        Snackbar.make(vSwipeRefreshLayout, R.string.text_network_not_available, Snackbar.LENGTH_LONG)
                .setAction(R.string.action_network_settings) { startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS)) }
                .show()
    }

    override fun onWeatherUpdatedSuccessfully(formattedWeather: FormattedWeather) {
        setWeatherOnView(formattedWeather)
        vSwipeRefreshLayout.isRefreshing = false
    }

    override fun onWeatherUpdatedAbortively() {
        vSwipeRefreshLayout.isRefreshing = false
    }

    override fun onChangeSwipeRefreshingStatus(isRefreshing: Boolean) {
        vSwipeRefreshLayout.isRefreshing = isRefreshing
    }

    override fun onCityDeleted() {
        isCityDeleted = true
    }

    override fun onPositionChanged(newPosition: Int) {
        position = newPosition
        isPositionChanged = true
    }

    private fun setWeatherOnView(formattedWeather: FormattedWeather) {
        vCityNameText.text = formattedWeather.cityName
        vTemperatureText.text = formattedWeather.temperature
        vConditionText.text = formattedWeather.condition
        vUpdateTimeText.text = formattedWeather.updateTime

        vFeelsLikeText.text = formattedWeather.feelsLike
        vTemperatureRangeText.text = formattedWeather.tempRange
        vAirQualityText.text = formattedWeather.airQuality

        vTemperatureTrendChart.setTemperatureData(formattedWeather.weeks, formattedWeather.conditions, formattedWeather.maxTemps, formattedWeather.minTemps)
    }
}
