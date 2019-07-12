package kt.module.base_module.http

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*
import java.util.*

interface ApiService {

    //获取配置
    @POST("/get/config")
    fun getConfig(@Body jsonBody: RequestBody): Observable<Objects>

    @GET("/get/data")
    fun getInfo(@QueryMap params: Map<String, @JvmSuppressWildcards Any>): Observable<Objects>

}