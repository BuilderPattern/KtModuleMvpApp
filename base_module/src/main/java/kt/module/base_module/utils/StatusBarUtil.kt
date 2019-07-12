package kt.module.base_module.utils

import android.os.Build
import android.os.Build.VERSION_CODES.KITKAT
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import kt.module.base_module.R

object StatusBarUtil {

    fun setBrightImmersion(activity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= KITKAT) {//Android 4.4以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Android 5.0以上
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                activity.window.statusBarColor = activity.resources.getColor(R.color.color_00000000)
            } else {
                val window = activity.window
                val attributes = window.attributes
                attributes.flags = attributes.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                window.attributes = attributes
            }
        }

    }

    fun setDarkImmersion(activity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= KITKAT) {//Android 4.4以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Android 5.0以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android 6.0以上有效，状态栏/标签的深/浅色
                    activity.window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//                    activity.window.statusBarColor = activity.getColor(R.color.color_33000000)
                } else {
                    activity.window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    activity.window.statusBarColor = activity.resources.getColor(R.color.color_33000000)
                }
            } else {
                val window = activity.window
                val attributes = window.attributes
                attributes.flags = attributes.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                window.attributes = attributes
            }
        }
    }
}