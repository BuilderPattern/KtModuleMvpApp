package kt.module.base_module.base.view

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kt.module.base_module.utils.StatusBarUtil
import me.jessyan.autosize.internal.CustomAdapt
import java.lang.Exception

open abstract class BaseActivity : RxAppCompatActivity(), CustomAdapt, LifecycleProvider<ActivityEvent> {

    protected open val view: View? = null
    protected open val contentLayoutId: Int = -1

    protected open val isDarkStatus: Boolean = true//沉浸式：标签亮/深色

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isDarkStatus) {
            StatusBarUtil.setDarkImmersion(this)
        } else {
            StatusBarUtil.setBrightImmersion(this)
        }

        try {
            if (contentLayoutId > 0) {
                setContentView(contentLayoutId)
            } else {
                if (view != null) {
                    setContentView(view)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        initViews()
        initEvents()
        ARouter.getInstance().inject(this)
    }

    open fun initViews() {}
    open fun initEvents() {}

    override fun isBaseOnWidth(): Boolean {
        return true
    }

    override fun getSizeInDp(): Float {
        return 667f
    }
}