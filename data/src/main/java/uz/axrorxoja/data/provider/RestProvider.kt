package uz.axrorxoja.data.provider

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uz.axrorxoja.data.network.Api
import uz.axrorxoja.data.network.HeaderInterceptor
import uz.axrorxoja.data.util.Config

internal class RestProvider {
    val api: Api by lazy {
        val okhttp = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
        
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okhttp)
            .baseUrl(Config.BASE_URL)
            .build()
            .create(Api::class.java)
    }

}