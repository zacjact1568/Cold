package net.zackzhang.code.haze.common.util

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import net.zackzhang.code.haze.common.exception.PlaceholderException
import net.zackzhang.code.haze.common.model.network.GsonTypeAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.KClass

object NetworkUtils {

    /**
     * 获取一个 OkHttpClient 对象
     * @param tag Logcat 打印的 tag
     */
    fun getOkHttpClient(tag: String) = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor { LogUtils.i(it, tag) }
            .setLevel(HttpLoggingInterceptor.Level.BODY))
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    private val GSON by lazy { GsonBuilder().registerTypeAdapterFactory(GsonTypeAdapterFactory()).create()!! }

    @Throws(JsonSyntaxException::class, PlaceholderException::class)
    fun <T : Any> fromJsonObject(obj: JSONObject, cls: KClass<T>): T {
        return GSON.fromJson(obj.toString(), cls.java) ?: throw PlaceholderException(0, "null")
    }

    @Throws(JsonParseException::class, JsonSyntaxException::class, PlaceholderException::class)
    fun <T : Any> fromJsonArray(arr: JSONArray, elmCls: KClass<T>): List<T> {
        val type = TypeToken.getParameterized(List::class.java, elmCls.java).type
        return GSON.fromJson(arr.toString(), type) ?: throw PlaceholderException(0, "null")
    }

    fun responseBodyToJsonObject(body: ResponseBody) = JSONObject(body.string()).apply {
        if (getString("code") != "200") throw PlaceholderException(200, "code is not")
    }

    fun getUpdateTime(obj: JSONObject) = DateTimeUtils.parseDateTime(obj.getString("updateTime"))
}