package kt.module.module_base.utils

import android.net.Uri
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

object RouteUtils {

    object RouterMap {
        object Main {
            const val MainAc = "/main/activity_main"
        }

        object HomePage {
            const val Home = "/home/fragment_home"
        }

        object MessagePage {
            const val Message = "/message/fragment_message"
        }

        object FurtherPage {
            const val Further = "/further/fragment_further"
        }

        object MinePage {
            const val Mine = "/mine/fragment_mine"
        }

        object First {
            const val FirstAc = "/first/first_activity"
        }

        object Second {
            const val SecondAc = "/second/second_activity"
        }

        object Third {
            const val ThirdAc = "/third/third_activity"
        }
    }

    fun go(path: String): Postcard {
        return ARouter.getInstance().build(path)
    }

    fun go(uri: Uri): Postcard {
        return ARouter.getInstance().build(uri)
    }
}