package com.folklore.data.remote

import com.folklore.data.BuildConfig
import com.folklore.data.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(Constants.APP_ID_HEADER, BuildConfig.appId)
        requestBuilder.addHeader(Constants.API_KEY_HEADER, BuildConfig.ApiRestKey)
        return chain.proceed(requestBuilder.build())
    }
}
