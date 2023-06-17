package com.folklore.app.data.remote

import com.folklore.app.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("X-Parse-Application-Id", BuildConfig.appId)
        requestBuilder.addHeader("X-Parse-REST-API-Key", BuildConfig.ApiRestKey)
        return chain.proceed(requestBuilder.build())
    }
}
