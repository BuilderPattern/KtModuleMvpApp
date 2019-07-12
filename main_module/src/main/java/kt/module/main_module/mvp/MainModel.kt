package kt.module.main_module.mvp

import io.reactivex.Observable
import kt.module.base_module.data.BaseResponseData
import kt.module.base_module.data.RvData
import kt.module.base_module.http.ApiService
import kt.module.base_module.http.BaseRetrofit
import okhttp3.RequestBody
import java.util.*

class MainModel :MainContract.IMainModel {
    override fun getConfig(requestBody: RequestBody): Observable<BaseResponseData<Objects>> {
        return BaseRetrofit().get().create(ApiService::class.java).getConfig(requestBody)
    }
}