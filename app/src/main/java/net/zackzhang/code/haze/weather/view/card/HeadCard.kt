package net.zackzhang.code.haze.weather.view.card

import android.view.ViewGroup
import net.zackzhang.code.haze.R
import net.zackzhang.code.haze.common.view.card.BaseCard
import net.zackzhang.code.haze.databinding.CardHeadBinding
import net.zackzhang.code.haze.weather.viewmodel.data.BaseCardData
import net.zackzhang.code.haze.weather.viewmodel.data.HeadCardData

class HeadCard(parent: ViewGroup) : BaseCard(parent, R.layout.card_head) {

    private val binding = CardHeadBinding.bind(itemView)

    override fun updateViews(cardData: BaseCardData) {
        if (cardData !is HeadCardData) return
        binding.apply {
            vTemperatureNow.text = cardData.temperatureNow.toString()
            vCondition.text = cardData.condition
            vAirQuality.text = cardData.airQuality
        }
    }
}