package kt.module.base_module.http

import android.os.Build
import android.webkit.WebSettings
import kt.module.BaseApp
import kt.module.base_module.config.BuildConfig.BASE_URL
import kt.module.base_module.config.BuildConfig.DEBUG
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

open class BaseRetrofit {

    fun get(): Retrofit {

        var builder = OkHttpClient.Builder()
        builder.addInterceptor(Interceptor() {
            var request = it.request().newBuilder().addHeader("User-Agent", getUserAgent()).removeHeader("User-Agent").build()
            return@Interceptor it.proceed(request)
        })
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
            .hostnameVerifier(HostnameVerifier { hostname, session ->
                return@HostnameVerifier true
            })
//
//        val keystore = KeyStore.getInstance(KeyStore.getDefaultType())
//        keystore.load(null)
//        keystore.setCertificateEntry("ca", cer)
//
//        // Create a TrustManager that trusts the CAs in our KeyStore
//        val algorithm = TrustManagerFactory.getDefaultAlgorithm()
//        val trustManagerFactory = TrustManagerFactory.getInstance(algorithm)
//        trustManagerFactory.init(keystore)
//
//
//        val sslContext = SSLContext.getInstance("TLS")
//        sslContext.init(null, trustManagerFactory.getTrustManagers(), null)

        if (DEBUG) {
            builder.addInterceptor(LogInterceptor())
        }
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(builder.build())
                .build()

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

}