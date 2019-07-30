package kt.module.further_module.mvp

import io.reactivex.Observable
import kt.module.base_module.data.bean.BaseResponseData
import kt.module.base_module.data.db.table.ObjectEntity
import kt.module.base_module.http.ApiService
import kt.module.base_module.http.ParamsBuilder
import kt.module.base_module.utils.BaseRetrofitUtil

class FurtherModel:FurtherContract.IFurtherModel {
    override fun getOD(paramsBuilder: ParamsBuilder): Observable<BaseResponseData<MutableList<ObjectEntity>>> {
        return BaseRetrofitUtil.get()!!.create(ApiService::class.java).getPostTest(paramsBuilder.getRequestBody())
    }
}