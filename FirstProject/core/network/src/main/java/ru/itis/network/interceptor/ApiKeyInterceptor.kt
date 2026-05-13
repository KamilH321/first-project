package ru.itis.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.itis.buildconfig.api.BuildConfigProvider

class ApiKeyInterceptor(
    private val buildConfigProvider: BuildConfigProvider
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val url = request.url

        val newUrl = url.newBuilder()
            .addQueryParameter("apikey", buildConfigProvider.getOMDbApiKey())
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}