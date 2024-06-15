package com.dicoding.sewez.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): Response<AuthResponse>

    @GET("jenis-bahan")
    suspend fun getJenisBahan(): Response<List<String>>

    @GET("warna")
    suspend fun getWarna(): Response<List<String>>

    @Multipart
    @POST("submit-order")
    suspend fun submitOrder(
        @Part file: MultipartBody.Part,
        @Part("jenis_bahan") jenisBahan: RequestBody,
        @Part("warna") warna: RequestBody,
        @Part("jumlah_s") jumlahS: RequestBody,
        @Part("jumlah_m") jumlahM: RequestBody,
        @Part("jumlah_l") jumlahL: RequestBody,
        @Part("jumlah_xl") jumlahXL: RequestBody
    ): Response<OrderResponse>
}

data class RegisterRequest(val name: String, val email: String, val password: String)
data class LoginRequest(val email: String, val password: String)
data class AuthResponse(val error: Boolean, val message: String, val loginResult: LoginResult? = null)
data class LoginResult(val userId: String, val name: String, val token: String)

data class OrderResponse(val success: Boolean, val message: String, val orderDetails: OrderDetails)

data class OrderDetails(val jenisBahan: String, val warna: String, val imageUrl: String, val orderSummary: String)