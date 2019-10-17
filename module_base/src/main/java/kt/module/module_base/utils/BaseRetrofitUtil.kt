package kt.module.module_base.utils

import android.os.Build
import android.webkit.WebSettings
import kt.module.BaseApp
import kt.module.module_base.R
import kt.module.module_common.config.BuildConfig
import kt.module.module_base.http.LogInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object BaseRetrofitUtil {

    private var retrofit: Retrofit? = null

    fun get(): Retrofit? {

        if (retrofit == null) {

            var sslContext: SSLContext? = null
            var builder = OkHttpClient.Builder()
            builder.addInterceptor(Interceptor {
                var request =
                    it.request().newBuilder().removeHeader("User-Agent").addHeader(
                        "User-Agent",
                        getUserAgent()
                    ).build()
                return@Interceptor it.proceed(request)
            })
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .hostnameVerifier(HostnameVerifier { hostname, session ->
                    return@HostnameVerifier true
                })
            var trustManager = MyX509TrustManager()
            var trustAllCerts = arrayOf<TrustManager>(trustManager)
            try {
                sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: KeyManagementException) {
                e.printStackTrace()
            }

            var sslSocketFactory = sslContext?.socketFactory
            builder.sslSocketFactory(sslSocketFactory, trustManager)

            if (BuildConfig.IS_DEBUG) {
                builder.addInterceptor(LogInterceptor())
            }

            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BaseApp.application.getString(R.string.base_url))
                .client(builder.build())
                .build()
        }
        return retrofit
    }

    private fun getUserAgent(): String {
        var userAgent = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(BaseApp.application)
            } catch (e: Exception) {
                userAgent = System.getProperty("http.agent")
            }
        } else {
            userAgent = System.getProperty("http.agent")
        }
        var sb = StringBuilder()


        userAgent.forEach { c ->
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", c))
            } else {
                sb.append(c)
            }
        }
        return sb.toString()
    }

    class MyX509TrustManager : X509TrustManager {
        override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}

        override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}

        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
            return arrayOf()
        }
    }
}