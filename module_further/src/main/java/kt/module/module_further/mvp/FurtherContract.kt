package kt.module.module_further.mvp

import io.reactivex.Observable
import kt.module.module_base.data.bean.BaseResponseData
import kt.module.module_base.data.db.table.ObjectEntity
import kt.module.module_base.http.ParamsBuilder
import kt.module.module_common.base.view.IBaseView

class FurtherContract {
    interface IFurtherView :IBaseView{
        fun getODSuccessed(data: MutableList<ObjectEntity>)
        fun getODFailed(msg: Any)
    }
    interface IFurtherModel{
        fun getOD(paramsBuilder: ParamsBuilder) :Observable<BaseResponseData<MutableList<ObjectEntity>>>
    }
}