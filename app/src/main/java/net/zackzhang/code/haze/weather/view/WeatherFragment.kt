package net.zackzhang.code.haze.weather.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import net.zackzhang.code.haze.R
import net.zackzhang.code.haze.common.Constants
import net.zackzhang.code.haze.common.view.CardAdapter
import net.zackzhang.code.haze.databinding.FragmentWeather1Binding
import net.zackzhang.code.haze.weather.view.card.HeadCard
import net.zackzhang.code.haze.weather.viewmodel.WeatherViewModel

class WeatherFragment : Fragment(R.layout.fragment_weather_1) {

    private val adapter = CardAdapter { type, parent ->
        when (type) {
            Constants.CARD_TYPE_WEATHER_HEAD -> HeadCard(parent)
            // Other cards
            else -> null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by viewModels<WeatherViewModel>()
        val binding = FragmentWeather1Binding.bind(view)
        binding.vCardList.adapter = adapter
        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            if (view is SwipeRefreshLayout) {
                view.isRefreshing = false
            }
            adapter.setCardData(it)
        }
        if (view is SwipeRefreshLayout) {
            view.setOnRefreshListener {
                viewModel.getNow("101010100")
            }
        }
    }
}