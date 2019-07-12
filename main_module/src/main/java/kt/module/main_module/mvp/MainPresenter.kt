package kt.module.main_module.mvp

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kt.module.base_module.http.ParamsBuilder
import kt.module.common_module.base.presenter.BasePresenter

class MainPresenter(view: MainContract.IMainView) : BasePresenter<MainContract.IMainView>(view) {

    fun getConfig(activity: MainActivity) {
        var paramsBuilder = ParamsBuilder()
        paramsBuilder.add("keyword", "1")

        MainModel()?.getConfig(paramsBuilder.getRequestBody())
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.compose(activity?.bindToLifecycle())
            ?.subscribe({
                mView?.getConfigSuccessed(it.result!!)
            },{
                mView?.getConfigFailed("发生错误！")
                it.printStackTrace()
            })
    }

}