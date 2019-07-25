package kt.module

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import android.support.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.dopool.common.util.AppUtil
import com.facebook.drawee.backends.pipeline.Fresco
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
        if (isMainProcess()){
            initARouter()
            Fresco.initialize(this)
        }
    }

    private fun initARouter() {
        if (AppUtil.isApkDebug(this)) {
            ARouter.openLog()
            ARouter.openDebug()
            ARouter.printStackTrace()
        }
        ARouter.init(this)
    }

    /**
     * 避免因为多个进程，重复初始化，
     * 导致启动速度减慢
     */
    fun isMainProcess(): Boolean {
        val myPid = Process.myPid()
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = manager.runningAppProcesses
        for (appProcess in appProcesses) {
            if (myPid == appProcess.pid && appProcess.processName == packageName) {
                return true
            }
        }
        return false
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}