package kt.module.module_video.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.pm.ActivityInfo
import android.database.ContentObserver
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.OrientationEventListener
import android.view.View
import android.view.WindowManager
import kt.module.module_base.constant.ConstantEvent
import kt.module.module_base.data.bean.BaseEvent
import org.greenrobot.eventbus.EventBus

class OrientationUtil(private val mActivity: Activity) {
    private var mIsPortrait = true
    private var mIsLandSpace = false
    private var mIsReverseLandSpace = false
    private var mIsFirstLandscape = true //第一次切横屏，解决初始横屏进入时，布局异常。
    private var mForceLandscapePlay = false //是否强制横屏播放

    private var mAccRotationStatus = 0
    private val mRotationObserver: RotationObserver

    private var mLock = false

    private lateinit var mOrientationListener: OrientationEventListener

    private var mCloseG_Sensor = false
    private var mIsBack: Boolean = false //是否back鍵

    //得到屏幕旋转的状态
    private val rotationStatus: Int
        get() = Settings.System.getInt(mActivity.contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0)

    init {
        init()
        mAccRotationStatus = rotationStatus
        mRotationObserver = RotationObserver(Handler())
    }

    fun setForceLandscapePlay(forceLandscapePlay: Boolean) {
        mForceLandscapePlay = forceLandscapePlay
        if (mForceLandscapePlay) {
            clickToLandscape()
        }
        EventBus.getDefault().post(BaseEvent(ConstantEvent.EVENT_FULL_SCREEN, mForceLandscapePlay))
    }

    private fun init() {
        mOrientationListener = object : OrientationEventListener(mActivity) {

            override fun onOrientationChanged(orientation: Int) {
                Log.e("-----orientation：", orientation.toString())
                if (orientation in 226..314) {
                    if (!mIsLandSpace && !mCloseG_Sensor) {
                        landscape(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)//横
                        //                        Log.i("orientation", "横屏 : " + orientation);
                    }
                    if (!mIsPortrait && mCloseG_Sensor && !mLock && !mForceLandscapePlay) {
                        mCloseG_Sensor = false
                    }
                } else if (orientation in 46..134) {
                    if (!mIsReverseLandSpace && !mCloseG_Sensor) {//反向横屏
                        landscape(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE)
                        //                        Log.i("orientation", "横屏 : " + orientation);
                    }
                    if (!mIsPortrait && mCloseG_Sensor && !mLock && !mForceLandscapePlay) {
                        mCloseG_Sensor = false
                    }
                } else if (orientation in 316..359 || orientation in 1..44) {
                    mIsFirstLandscape = false
                    if (!mIsPortrait && !mCloseG_Sensor) {
                        portrait()
                        //                        Log.i("orientation", "竖屏 : " + orientation);
                    }
                    if (mIsPortrait && mCloseG_Sensor && !mLock && !mForceLandscapePlay) {
                        mCloseG_Sensor = false
                    }
                }
            }
        }
    }


    /**
     * 播放器全屏
     */
    private fun landscape(orientation: Int) {
        mIsPortrait = false
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mIsLandSpace = true
            mIsReverseLandSpace = false
        } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
            mIsLandSpace = false
            mIsReverseLandSpace = true
        }
        full(true, mActivity)
        mActivity.requestedOrientation = orientation
        //        横屏之后设置detailfragment不接受返回事件
        //        EventBus.getDefault().post(new BaseEvent(KEY_LANDSPACE));
        if (mIsFirstLandscape) {
            //            mPresenter.setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            mActivity.requestedOrientation = orientation //有时刚进入时，布局变横屏，方向未变，这里强制再改变方向为横屏
//            mIsFirstLandscape = false
        }
        EventBus.getDefault().post(BaseEvent(ConstantEvent.EVENT_FULL_SCREEN, true))

    }

    /**
     * 竖屏
     */
    private fun portrait() {
        mIsPortrait = true
        mIsLandSpace = false
        mIsReverseLandSpace = false
        full(false, mActivity)
        mActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (mIsBack) {
            mIsBack = false
        }
        EventBus.getDefault().post(BaseEvent(ConstantEvent.EVENT_FULL_SCREEN, false))
    }

    fun clickToLandscape() {
        landscape(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        mCloseG_Sensor = true
    }

    fun clickToPortrait() {
        portrait()
        mCloseG_Sensor = true
    }

    fun isPortrait(): Boolean {
        return mIsPortrait
    }

    fun onBackPressed(): Boolean {
        if (mLock) {
            return true
        } else if (!mIsPortrait) {
            if (mForceLandscapePlay) {
                return false
            }
            portrait()
            mCloseG_Sensor = true
            return true
        } else {
            return false
        }
    }

    fun lock(locked: Boolean) {
        mLock = locked
        mCloseG_Sensor = locked
    }


    fun start() {
        mRotationObserver.startObserver()
        if (mAccRotationStatus == 1) {
            mOrientationListener.enable()
        }
    }

    fun stop() {
        mRotationObserver.stopObserver()
        mOrientationListener.disable()
    }

    //观察屏幕旋转设置变化，类似于注册动态广播监听变化机制
    private inner class RotationObserver(handler: Handler) : ContentObserver(handler) {
        internal var mResolver: ContentResolver = mActivity.contentResolver

        //屏幕旋转设置改变时调用
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            mAccRotationStatus = rotationStatus
            Log.d(TAG, "ro mAccRotationStatus:" + mAccRotationStatus)
            if (mAccRotationStatus == 1) {
                mOrientationListener.enable()
            } else {
                mOrientationListener.disable()
            }
        }

        fun startObserver() {
            mResolver.registerContentObserver(
                Settings.System.getUriFor(Settings.System.ACCELEROMETER_ROTATION),
                false,
                this
            )
        }

        fun stopObserver() {
            mResolver.unregisterContentObserver(this)
        }
    }

    companion object {
        val TAG = "OrientationUtil"

        /**
         * 设置是否显示状态栏
         */
        fun full(enable: Boolean, activity: Activity) {
            val decorView = activity.window.decorView
            var para = decorView.systemUiVisibility
            if (enable) {
                para = para or (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    para = para or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                }
                decorView.systemUiVisibility = para
            } else {
                para =
                    para and (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION.inv() and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv())
                decorView.systemUiVisibility = para
            }

            if (enable) {
                val lp = activity.window.attributes
                lp.flags = lp.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
                activity.window.attributes = lp
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            } else {
                val attr = activity.window.attributes
                attr.flags = attr.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
                activity.window.attributes = attr
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }

        }
    }
}
