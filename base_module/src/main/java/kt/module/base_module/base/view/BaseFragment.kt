package kt.module.base_module.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.trello.rxlifecycle2.components.support.RxFragment

abstract class BaseFragment : RxFragment() {
    private var mRoot: View? = null

    protected abstract val contentLayoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

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

    open fun initViews() {

    }

    open fun initEvents() {

    }
}