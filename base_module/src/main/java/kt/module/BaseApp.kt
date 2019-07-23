package kt.module

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.dopool.common.util.AppUtil
import kt.module.base_module.data.db.dao.DaoMaster
import kt.module.base_module.data.db.dao.DaoSession
import kt.module.base_module.data.db.dao.utils.CustomOpenHelper

open class BaseApp : Application() {

    companion object {
        lateinit var application: BaseApp

        private val DB_NAME = "kt_module"
        private var mDaoMaster: DaoMaster? = null
        private var mDaoSession: DaoSession? = null

        /**
         * 取得DaoSession
         * @param context
         * @return
         */
        fun getDaoSession(): DaoSession {
            if (mDaoSession == null) {
                if (mDaoMaster == null) {
                    mDaoMaster = DaoMaster(CustomOpenHelper(application, DB_NAME).writableDatabase)
                }
                if (mDaoSession == null) {
                    mDaoSession = mDaoMaster!!.newSession()
                }
            }
            return mDaoSession!!
        }
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