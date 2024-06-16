package com.example.mevi.core

import com.example.mevi.core.Constants.Companion.AUTH_TOKEN
import com.example.mevi.core.Constants.Companion.NEEDS_AUTH_HEADER_KEY
import okhttp3.Interceptor
import okhttp3.Response

object ApiInterceptor : Interceptor {

    private var token: String? = null
    fun setToken(userToken: String) {
        this.token = userToken
    }
    fun clearToken(){
        this.token = null
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        if (request.header(NEEDS_AUTH_HEADER_KEY) != null) {
            if (token != null) {
                requestBuilder.addHeader(AUTH_TOKEN, token!!)
            }
        }
        return chain.proceed(requestBuilder.build())
    }
}