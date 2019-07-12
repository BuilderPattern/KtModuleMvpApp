package kt.module.base_module.http

import io.reactivex.Observable
import kt.module.base_module.data.BaseResponseData
import kt.module.base_module.data.RvData
import okhttp3.RequestBody
import retrofit2.http.*
import java.util.*

open interface ApiService {

    @POST("test")
    fun getConfig(@Body jsonBody: RequestBody): Observable<BaseResponseData<Objects>>

    @GET("/get/data")
    fun getInfo(@QueryMap params: Map<String, @JvmSuppressWildcards Any>): Observable<BaseResponseData<RvData>>

}