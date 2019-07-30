package kt.module.further_module.mvp

import io.reactivex.Observable
import kt.module.base_module.data.bean.BaseResponseData
import kt.module.base_module.data.db.table.ObjectEntity
import kt.module.base_module.http.ParamsBuilder
import kt.module.common_module.base.view.IBaseView

class FurtherContract {
    interface IFurtherView :IBaseView{
        fun getODSuccessed(data: MutableList<ObjectEntity>?)
        fun getODFailed(msg: Any)
    }
    interface IFurtherModel{
        fun getOD(paramsBuilder: ParamsBuilder) :Observable<BaseResponseData<MutableList<ObjectEntity>>>?
    }
}