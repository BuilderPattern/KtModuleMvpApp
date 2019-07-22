package kt.module

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.dopool.common.util.AppUtil
import org.greenrobot.greendao.AbstractDaoMaster

open class BaseApp : Application() {

    companion object {
        lateinit var application: BaseApp

        val DB_NAME = "kt_module"
//        private var mDaoMaster: DaoMaster? = null
//        private var mDaoSession: DaoSession? = null
//
//        /**
//         * 取得DaoSession
//         * @param context
//         * @return
//         */
//        fun getDaoSession(): DaoSession {
//            if (mDaoSession == null) {
//                AbstractDaoMaster()
//                if (mDaoMaster == null) {
//                    mDaoMaster = DaoMaster(CustomOpenHelper(BaseApp.context, DB_NAME).writableDatabase)
//                }
//                if (mDaoSession == null) {
//                    mDaoSession = mDaoMaster!!.newSession()
//                }
//            }
//            return mDaoSession!!
//        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
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
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}