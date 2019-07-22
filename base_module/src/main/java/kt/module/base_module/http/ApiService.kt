package kt.module.base_module.http

import io.reactivex.Observable
import kt.module.base_module.base.entity.BaseResponseData
import kt.module.base_module.data.db.table.ObjectEntity
import okhttp3.RequestBody
import retrofit2.http.*

open interface ApiService {

    @POST("search_key")
    fun getPostTest(@Body jsonBody: RequestBody): Observable<BaseResponseData<MutableList<ObjectEntity>>>

    @GET("get_top_cat")
    fun getGetTest(@QueryMap params: Map<String, @JvmSuppressWildcards Any>): Observable<Any>

}