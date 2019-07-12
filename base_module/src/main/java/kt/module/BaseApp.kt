package kt.module

import android.app.Application
import android.support.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.dopool.common.util.AppUtil

open class BaseApp : Application() {

    companion object {
        lateinit var application: BaseApp
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        MultiDex.install(this)
        initARouter()
    }

    private fun initARouter() {
        if (AppUtil.isApkDebug(this)) {
            ARouter.openLog()
            ARouter.openDebug()
            ARouter.printStackTrace()
        }
        ARouter.init(this)
    }

}