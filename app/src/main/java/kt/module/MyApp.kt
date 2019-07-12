package kt.module

import com.alibaba.android.arouter.launcher.ARouter

class MyApp: BaseApp() {
    override fun onCreate() {
        super.onCreate()
        ARouter.openDebug()
        ARouter.openLog()
        ARouter.init(this)
    }
}