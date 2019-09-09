package kt.module.module_base.http

import io.reactivex.Observable
import kt.module.module_base.data.bean.BaseResponseData
import kt.module.module_base.data.db.table.ObjectEntity
import okhttp3.RequestBody
import retrofit2.http.*

open interface ApiService {

    @POST("search_key")
    fun getPostTest(@Body jsonBody: RequestBody): Observable<BaseResponseData<MutableList<ObjectEntity>>>

    @GET("get_top_cat")
    fun getGetTest(@QueryMap params: Map<String, @JvmSuppressWildcards Any>): Observable<Any>

}