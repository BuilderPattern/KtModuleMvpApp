@file:Suppress("DEPRECATION")

package com.dopool.common.util

import android.content.Context
import android.content.pm.*

object AppUtil {
    //判断是否是Debug版本
    fun isApkDebug(context: Context): Boolean {
        try {
            val info = context.applicationInfo
            return info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}