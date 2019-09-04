@file:Suppress("DEPRECATION")

package com.dopool.common.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.*
import android.location.Criteria
import android.location.Location
import android.location.LocationManager

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

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context): Location? {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        criteria.isAltitudeRequired = false
        criteria.isBearingRequired = false
        criteria.isCostAllowed = true
        criteria.powerRequirement = Criteria.POWER_LOW

        try {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                var location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location != null) {
                    return location
                } else {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    if (location != null) {
                        return location
                    }
                }
            }
        } catch (e: Exception) {
            //ignore
        }
        return null
    }
}