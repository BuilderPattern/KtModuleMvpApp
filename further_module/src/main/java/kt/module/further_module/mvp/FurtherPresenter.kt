package kt.module.further_module.mvp

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kt.module.base_module.constant.Constant.Key.KEY_WORD
import kt.module.base_module.http.ParamsBuilder
import kt.module.common_module.base.presenter.BasePresenter

class FurtherPresenter(mView: FurtherContract.IFurtherView, var model: FurtherModel) :
    BasePresenter<FurtherContract.IFurtherView>(mView) {

    fun getPostTest(furtherFragment: FurtherFragment) {
        var paramsBuilder = ParamsBuilder()
        paramsBuilder.add(KEY_WORD, "天")

        model?.getOD(paramsBuilder)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.compose(furtherFragment.bindToLifecycle())
            ?.subscribe({
                if (it.code == 200){
                    mView?.getODSuccessed(it.result)
                }
            },{
                it.printStackTrace()
                mView?.getODFailed("发生错误！")
            })


    }
}