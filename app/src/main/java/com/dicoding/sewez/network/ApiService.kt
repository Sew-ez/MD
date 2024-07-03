package com.dicoding.sewez.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/logout")
    suspend fun logoutUser(@Body request: LogoutRequest): Response<AuthResponse>

    @GET("order/jenis-bahan")
    suspend fun getJenisBahan(@Header("Authorization") token: String): Response<ResponseData<List<JenisBahan>>>

    @GET("order/warna")
    suspend fun getWarna(@Header("Authorization") token: String): Response<ResponseData<List<Warna>>>

    @GET("order/jenis-bahan-logo")
    suspend fun getJenisBahanLogo(@Header("Authorization") token: String): Response<ResponseData<List<JenisBahanLogo>>>

    @Multipart
    @POST("order/submit")
    suspend fun submitOrder(
        @Header("Authorization") token: String,
        @Part("jenisproduk") jenisproduk: RequestBody,
        @Part("jenisbahan") jenisbahan: RequestBody,
        @Part("warna") warna: RequestBody,
        @Part("jenislogo") jenislogo: RequestBody,
        @Part("s") s: RequestBody,
        @Part("m") m: RequestBody,
        @Part("l") l: RequestBody,
        @Part("xl") xl: RequestBody,
        @Part("xxl") xxl: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<OrderResponse>

    @POST("auth/refresh-token")
    fun refreshTokenSync(@Header("Authorization") token: String): Call<RefreshTokenResponse>
}

data class RegisterRequest(val name: String, val email: String, val password: String)
data class LoginRequest(val email: String, val password: String)
data class LogoutRequest(val token: String)
data class AuthResponse(val error: Boolean, val message: String, val loginResult: LoginResult? = null)
data class LoginResult(val userId: String, val name: String, val token: String)

// Order related data models
data class ResponseData<T>(val error: Boolean, val message: String, val data: T)
data class JenisBahan(val id: Int, val type: String)
data class Warna(val id: Int, val color: String, val hex: String)
data class JenisBahanLogo(val id: Int, val type: String)

@Parcelize
data class OrderDetails(
    val jenisbahan: String,
    val warna: String,
    val jenislogo: String,
    val xxl: Int,
    val xl: Int,
    val l: Int,
    val m: Int,
    val s: Int,
    val totalFabricPrice: Int,
    val totalLogoPrice: Int,
    val totalPrice: Int,
    val image: String
) : Parcelable

data class OrderResponse(val error: Boolean, val message: String, val data: OrderDetails)
data class RefreshTokenResponse(val newToken: String)