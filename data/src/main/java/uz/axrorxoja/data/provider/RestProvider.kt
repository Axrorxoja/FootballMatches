package uz.axrorxoja.data.provider

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uz.axrorxoja.data.network.Api
import uz.axrorxoja.data.util.Config

internal class RestProvider {
    val api: Api by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(Config.BASE_URL)
            .build()
            .create(Api::class.java)
    }

}