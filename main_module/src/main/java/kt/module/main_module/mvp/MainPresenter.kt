package kt.module.main_module.mvp

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kt.module.common_module.constant.Constant
import kt.module.base_module.http.ParamsBuilder
import kt.module.common_module.base.presenter.BasePresenter

class MainPresenter(view: MainContract.IMainView, var model: MainModel) : BasePresenter<MainContract.IMainView>(view) {

    fun getPostTest(activity: MainActivity) {
        var paramsBuilder = ParamsBuilder()
        paramsBuilder.add(Constant.Key.KEY_WORD, "b")

        model.run {
            getPostTest(paramsBuilder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(activity.bindToLifecycle())
                .subscribe({
                    if (it.code == 200) {
                        mView?.getPostTestSuccessed(it.result)
                    } else {
                        mView?.getPostTestFailed("发生错误！")
                    }
                }, {
                    mView?.getPostTestFailed("发生错误！")
                    it.printStackTrace()
                })
        }
    }

    fun getGetTest(activity: MainActivity) {
        var paramsBuilder = ParamsBuilder()
        paramsBuilder.add(Constant.Key.OS_TYPE, "get")

        model.let { model ->
            model.getGetTest(paramsBuilder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(activity.bindToLifecycle())
                .subscribe({
                    mView?.getGetTestSuccessed(it)
                }, {
                    mView?.getGetTestCatFailed("发生错误！")
                    it.printStackTrace()
                })
        }
    }

}