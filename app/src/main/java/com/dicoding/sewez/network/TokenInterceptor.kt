package com.dicoding.sewez.network

import android.content.Context
import com.dicoding.sewez.utils.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = PreferencesHelper.getLoginSession(context)

        // Add the authorization header to the request
        var authenticatedRequest = token?.let {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $it")
                .build()
        } ?: originalRequest

        var response = chain.proceed(authenticatedRequest)

        // If the token has expired, refresh it
        if (response.code == 401) {
            val newToken = refreshTokenSynchronously(context)
            if (newToken != null) {
                // Retry the request with the new token
                authenticatedRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()
                response = chain.proceed(authenticatedRequest)
            }
        }
        return response
    }

    private fun refreshTokenSynchronously(context: Context): String? {
        return try {
            val currentToken = PreferencesHelper.getLoginSession(context)
            if (currentToken != null) {
                val response = RetrofitInstance.create(context)
                    .create(ApiService::class.java)
                    .refreshTokenSync("Bearer $currentToken")
                    .execute()
                if (response.isSuccessful) {
                    val newToken = response.body()?.newToken
                    if (newToken != null) {
                        PreferencesHelper.saveLoginSession(context, newToken)
                        return newToken
                    }
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}