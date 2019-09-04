package kt.module.module_main.mvp

import io.reactivex.Observable
import kt.module.module_base.data.bean.BaseResponseData
import kt.module.module_base.data.db.table.ObjectEntity
import kt.module.module_base.http.ApiService
import kt.module.module_base.http.ParamsBuilder
import kt.module.module_base.utils.BaseRetrofitUtil

class MainModel :MainContract.IMainModel {
    override fun getGetTest(paramsBuilder: ParamsBuilder): Observable<Any> {
        return BaseRetrofitUtil.get()!!.create(ApiService::class.java).getGetTest(paramsBuilder.build())
    }

    override fun getPostTest(paramsBuilder: ParamsBuilder): Observable<BaseResponseData<MutableList<ObjectEntity>>> {
        return BaseRetrofitUtil.get()!!.create(ApiService::class.java).getPostTest(paramsBuilder.getRequestBody())
    }
}