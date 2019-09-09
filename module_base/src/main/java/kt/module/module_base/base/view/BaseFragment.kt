package kt.module.module_base.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.alibaba.android.arouter.launcher.ARouter
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxFragment
import kt.module.module_base.base.presenter.IBasePresenter
import me.jessyan.autosize.internal.CustomAdapt

open abstract class BaseFragment<T : IBasePresenter> : RxFragment(),CustomAdapt,
    LifecycleProvider<FragmentEvent> {
    private var mRoot: View? = null

    protected abstract val contentLayoutId: Int

    open val presenter: T? = null
    private var mPresenter: T? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter = presenter
        ARouter.getInstance().inject(this)
        if (mRoot == null) {
            mRoot = inflater.inflate(contentLayoutId, container, false)
        } else {
            if (mRoot!!.parent != null) {
                (mRoot!!.parent as ViewGroup).removeView(mRoot)
            }
        }
        return mRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initEvents()
    }

    override fun onDestroy() {
        mPresenter?.destroy()
        super.onDestroy()
    }

    open fun initViews() {

    }

    open fun initEvents() {

    }

    override fun isBaseOnWidth(): Boolean {
        return true
    }

    override fun getSizeInDp(): Float {
        return 667f
    }
}