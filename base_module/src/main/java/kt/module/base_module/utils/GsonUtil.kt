package kt.module.base_module.utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

import java.util.ArrayList

object GsonUtil {
    private var gson: Gson? = null

    init {
        if (gson == null) {
            gson = Gson()
        }
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    fun gsonString(`object`: Any): String? {
        var gsonString: String? = null
        if (gson != null) {
            gsonString = gson!!.toJson(`object`)
        }
        return gsonString
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> gsonToBean(gsonString: String, cls: Class<T>): T? {
        var t: T? = null
        if (gson != null) {
            t = gson!!.fromJson(gsonString, cls)
        }
        return t
    }

    /**
     * 转成list
     * 解决泛型问题
     * @param json
     * @param cls
     * @param <T>
     * @return
    </T> */
    fun <T> jsonToList(json: String, cls: Class<T>): List<T> {
        val list = ArrayList<T>()
        try {
            val array = JsonParser().parse(json).asJsonArray
            for (elem in array) {
                list.add(gson!!.fromJson(elem, cls))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return list
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    fun <T> gsonToListMaps(gsonString: String): List<Map<String, T>>? {
        var list: List<Map<String, T>>? = null
        if (gson != null) {
            list = gson!!.fromJson<List<Map<String, T>>>(gsonString,
                object : TypeToken<List<Map<String, T>>>() {

                }.type
            )
        }
        return list
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    fun <T> gsonToMaps(gsonString: String): Map<String, T>? {
        var map: Map<String, T>? = null
        if (gson != null) {
            map = gson!!.fromJson<Map<String, T>>(gsonString, object : TypeToken<Map<String, T>>() {

            }.type)
        }
        return map
    }
}
