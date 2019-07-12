package kt.module.main_module.mvp

import io.reactivex.Observable
import kt.module.base_module.data.BaseResponseData
import kt.module.base_module.data.RvData
import kt.module.base_module.http.ParamsBuilder
import kt.module.common_module.base.view.IBaseView
import okhttp3.RequestBody
import java.util.*

class MainContract {
    interface IMainView : IBaseView {
        fun getConfigSuccessed(data: Objects)
        fun getConfigFailed(msg: String)
    }

    interface IMainModel {
        fun getConfig(requestBody: RequestBody): Observable<BaseResponseData<Objects>>
    }
}