package kt.module.module_main.mvp

import io.reactivex.Observable
import kt.module.module_base.data.bean.BaseResponseData
import kt.module.module_base.data.db.table.ObjectEntity
import kt.module.module_base.http.ParamsBuilder
import kt.module.module_common.base.view.IBaseView

class MainContract {
    interface IMainView : IBaseView {
        fun getPostTestSuccessed(data: MutableList<ObjectEntity>)
        fun getPostTestFailed(msg: Any)
        fun getGetTestSuccessed(data: Any)
        fun getGetTestCatFailed(msg: Any)
    }

    interface IMainModel {
        fun getPostTest(paramsBuilder: ParamsBuilder): Observable<BaseResponseData<MutableList<ObjectEntity>>>
        fun getGetTest(paramsBuilder: ParamsBuilder): Observable<Any>
    }
}