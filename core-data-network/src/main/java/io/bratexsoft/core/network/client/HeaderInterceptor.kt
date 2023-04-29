package io.bratexsoft.core.network.client

import io.bratexsoft.core.data.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request()
            .newBuilder()
            .apply {
                header("Authorization", "Bearer ${BuildConfig.apiKey}")
            }.also {
                return chain.proceed(it.build())
            }
    }
}
