package kt.module.common_module.base.presenter

import kt.module.module_base.base.presenter.IBasePresenter

open class BasePresenter<T>(mView: T) : IBasePresenter {
    var mView: T? = mView

    override fun destroy() {
        mView = null
    }
}