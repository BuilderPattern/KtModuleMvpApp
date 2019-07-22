package kt.module.main_module.mvp

import io.reactivex.Observable
import kt.module.base_module.base.entity.BaseResponseData
import kt.module.base_module.data.db.table.ObjectEntity
import kt.module.base_module.http.ApiService
import kt.module.base_module.http.ParamsBuilder
import kt.module.base_module.utils.BaseRetrofitUtil

class MainModel :MainContract.IMainModel {
    override fun getGetTest(paramsBuilder: ParamsBuilder): Observable<Any>? {
        return BaseRetrofitUtil.get()?.create(ApiService::class.java)?.getGetTest(paramsBuilder.build())
    }

    override fun getPostTest(paramsBuilder: ParamsBuilder): Observable<BaseResponseData<MutableList<ObjectEntity>>>? {
        return BaseRetrofitUtil.get()?.create(ApiService::class.java)?.getPostTest(paramsBuilder.getRequestBody())
    }
}