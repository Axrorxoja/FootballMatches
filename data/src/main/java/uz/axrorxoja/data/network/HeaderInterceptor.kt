package uz.axrorxoja.data.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

private const val HEADER_KEY = "X-Auth-Token"
private const val HEADER_VALUE = "953ae1c495f545f0ae374408b65317ee"

class HeaderInterceptor: Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(HEADER_KEY, HEADER_VALUE)
            .build()
        return chain.proceed(newRequest)
    }
}