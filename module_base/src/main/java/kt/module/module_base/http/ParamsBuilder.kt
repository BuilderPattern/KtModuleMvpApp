package kt.module.module_base.http

import com.google.gson.Gson
import kt.module.module_common.constant.Constant
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.ArrayList

class ParamsBuilder {
    private val params = LinkedHashMap<String, Any>()

    //构造方法放公共参数
    constructor(){
        params[Constant.Key.OS_VERSION] = "1"
        params[Constant.Key.DEVICE_ID] = "adTaac2632dxzb"
    }

    fun add(key: String, value: String): ParamsBuilder {
        params[key] = value
        return this
    }

    fun add(key: String, value: Int): ParamsBuilder {
        params[key] = value
        return this
    }

    fun add(key: String, value: Long): ParamsBuilder {
        params[key] = value
        return this
    }

    fun add(key: String, value: Double): ParamsBuilder {
        params[key] = value
        return this
    }

    fun add(key: String, value: Boolean): ParamsBuilder {
        params[key] = value
        return this
    }

    fun addAll(key: String, values: List<*>): ParamsBuilder {
        params[key] = values
        return this
    }

    fun addAll(key: String, values: Array<Any>): ParamsBuilder {
        params[key] = values
        return this
    }

    fun addAll(map: Map<String, Any>): ParamsBuilder {
        params.putAll(map)
        return this
    }

    /**
     * GET请求参数
     */
    fun build(): IdentityHashMap<String, Any> {
        val map = IdentityHashMap<String, Any>()
        for ((key, value) in params) {
            if (value is List<*>) {
                for (valueItem in value) {
                    map.put(String(key.toByteArray()), valueItem)
                }
            } else {
                map[key] = value
            }
        }
        return map
    }

    /**
     * POST请求的时候
     * 把请求参数转成：RequestBody
     */
    fun getRequestBody(): RequestBody {
        val jsonString = Gson().toJson(build())
        return RequestBody.create(MediaType.parse("application/json"), jsonString)
    }

    /**
     * 拼接一个用map元素拼接一个str用于加密
     *
     * @param jsonMap 装参数的map
     * @return
     */
    fun getJsonString(jsonMap: Map<String, Any>): String {
        val sb = StringBuilder()
        val keys = ArrayList<String>()
        keys.addAll(jsonMap.keys)
        keys.sort()
        for (key in keys) {
            val value = jsonMap[key]
            if (value is List<Any?>) {
                @Suppress("UNCHECKED_CAST")
                (value as MutableList<Comparable<Any?>>?)?.sort()
                for (v in value) {
                    sb.append("&").append(key).append("=").append(v)
                }
            } else {
                sb.append("&").append(key).append("=").append(jsonMap[key])
            }
        }
        if (sb.length > 1) {
            sb.deleteCharAt(0)
        }
        return sb.toString()
    }
}