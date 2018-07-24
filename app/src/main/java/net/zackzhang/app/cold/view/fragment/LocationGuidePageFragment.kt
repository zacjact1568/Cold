package net.zackzhang.app.cold.view.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import net.zackzhang.app.cold.App
import net.zackzhang.app.cold.R
import net.zackzhang.app.cold.common.Constant
import net.zackzhang.app.cold.event.CityAddedEvent
import net.zackzhang.app.cold.model.DataManager
import net.zackzhang.app.cold.model.bean.Weather
import net.zackzhang.app.cold.util.ResourceUtil
import net.zackzhang.app.cold.util.StringUtil
import me.imzack.lib.baseguideactivity.SimpleGuidePageFragment
import net.zackzhang.app.cold.view.dialog.MessageDialogFragment

class LocationGuidePageFragment : SimpleGuidePageFragment() {

    companion object {

        private const val TAG_PRE_ENABLE_LOCATION_SERVICE = "pre_enable_location_service"

        fun newInstance(): LocationGuidePageFragment {
            val fragment = LocationGuidePageFragment()
            fragment.arguments = Bundle()
            return fragment
        }
    }

    private val preferenceHelper = DataManager.preferenceHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isLocationServiceEnabled = preferenceHelper.locationServiceValue

        imageResId = R.drawable.ic_place_black_24dp
        imageTint = Color.WHITE
        titleText = StringUtil.addWhiteColorSpan(ResourceUtil.getString(R.string.title_page_location))
        descriptionText = StringUtil.addWhiteColorSpan(ResourceUtil.getString(if (isLocationServiceEnabled) R.string.description_page_location_enabled else R.string.description_page_location_disabled))
        buttonText = if (isLocationServiceEnabled) null else StringUtil.addWhiteColorSpan(ResourceUtil.getString(R.string.btn_page_location))
        buttonBackgroundColor = ResourceUtil.getColor(R.color.colorAccent)
        buttonClickListener = {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                // Android 6.0 以下，直接开启位置服务，因为不需要授权
                enableLocationService()
            } else {
                MessageDialogFragment.Builder()
                        .setTitle(R.string.title_dialog_pre_enable_location_service)
                        .setMessage(R.string.msg_dialog_pre_enable_location_service)
                        .setOkButtonText(R.string.pos_btn_dialog_pre_enable_location_service)
                        .showCancelButton()
                        .show(childFragmentManager, TAG_PRE_ENABLE_LOCATION_SERVICE)
            }
        }
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)

        if (childFragment.tag == TAG_PRE_ENABLE_LOCATION_SERVICE) {
            (childFragment as MessageDialogFragment).okButtonClickListener = { enableLocationService() }
        }
    }

    private fun enableLocationService() {
        // 开启位置服务
        preferenceHelper.locationServiceValue = true
        // 更新界面
        descriptionText = StringUtil.addWhiteColorSpan(getString(R.string.description_page_location_enabled))
        buttonText = null
        // 先添加“当前位置”占位，待后续获取到位置再更新
        // 在引导页添加的城市位置肯定是 0，因此不需要指定插入位置（notifyCityAdded 默认在尾部插入）
        // TODO 定位到的城市都是县级？
        DataManager.notifyCityAdded(Weather(Constant.CITY_ID_CURRENT_LOCATION, getString(R.string.text_current_location), isLocationCity = true))
        App.eventBus.post(CityAddedEvent(
                javaClass.simpleName,
                Constant.CITY_ID_CURRENT_LOCATION,
                0
        ))
    }
}