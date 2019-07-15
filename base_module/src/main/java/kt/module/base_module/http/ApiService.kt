package kt.module.base_module.http

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

open interface ApiService {

    @POST("get_post_test")
    fun getPostTest(@Body jsonBody: RequestBody): Observable<Any>

    @GET("get_top_cat")
    fun getGetTest(@QueryMap params: Map<String, @JvmSuppressWildcards Any>): Observable<Any>

}