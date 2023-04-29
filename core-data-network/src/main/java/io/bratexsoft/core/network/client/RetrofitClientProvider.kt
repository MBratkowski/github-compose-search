package io.bratexsoft.core.network.client

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientProvider {

    fun <T> provideClient(host: String, api: Class<T>): T {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HeaderInterceptor())
                    .build()
            )
            .baseUrl(host)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create()
                )
            )
            .build()
            .create(api)
    }
}
