package net.zackzhang.code.haze

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import net.zackzhang.code.haze.common.Constant
import net.zackzhang.code.haze.common.util.ResourceUtils
import net.zackzhang.code.haze.event.EventBusIndex
import net.zackzhang.code.haze.model.DataManager
import org.greenrobot.eventbus.EventBus

class HazeApplication : Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set

//        val eventBus
//            get() = EventBus.getDefault()!!
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
//        EventBus.builder().addIndex(EventBusIndex()).installDefaultEventBus()
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
//        ResourceUtils.copyRawFile(R.raw.db_he_weather_condition, getDatabasePath(Constant.HE_WEATHER_CONDITION_DB_FN))
//        DataManager.loadData()
    }
}
