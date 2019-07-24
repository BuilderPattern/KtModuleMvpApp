package kt.module.main_module.mvp

import io.reactivex.Observable
import kt.module.base_module.base.entity.BaseResponseData
import kt.module.base_module.data.db.table.ObjectEntity
import kt.module.base_module.http.ParamsBuilder
import kt.module.common_module.base.view.IBaseView

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