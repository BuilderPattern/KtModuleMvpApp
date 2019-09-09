package kt.module.module_base.utils

import android.content.Context
import android.net.ConnectivityManager

object NetWorkUtil {

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            // 获取NetworkInfo对象
            val networkInfo = manager.activeNetworkInfo
            //判断NetworkInfo对象是否为空
            if (networkInfo != null)
                return networkInfo.isAvailable
        }
        return false
    }


}