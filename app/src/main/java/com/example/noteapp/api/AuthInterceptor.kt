package com.example.noteapp.api

import com.example.noteapp.Utlils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {
    @Inject
    lateinit var tokenManager: TokenManager
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getToken()
        val request = chain.request().newBuilder()
        request.addHeader("Authoeization","Bearer  $token")
        return chain.proceed(request.build())
    }
}