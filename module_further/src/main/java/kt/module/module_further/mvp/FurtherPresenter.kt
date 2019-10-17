package kt.module.module_further.mvp

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kt.module.module_common.constant.Constant.Key.KEY_WORD
import kt.module.module_base.http.ParamsBuilder
import kt.module.module_common.base.presenter.BasePresenter

class FurtherPresenter(mView: FurtherContract.IFurtherView, var model: FurtherModel) :
    BasePresenter<FurtherContract.IFurtherView>(mView) {

    fun getPostTest(furtherFragment: FurtherFragment) {
        var paramsBuilder = ParamsBuilder()
        paramsBuilder.add(KEY_WORD, "天")

        model?.let { model ->
            model.getOD(paramsBuilder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(furtherFragment.bindToLifecycle())
                .subscribe({
                    if (it.code == 200) {
                        mView?.getODSuccessed(it.result)
                    }
                }, {
                    it.printStackTrace()
                    mView?.getODFailed("发生错误！")
                })
        }
    }
}