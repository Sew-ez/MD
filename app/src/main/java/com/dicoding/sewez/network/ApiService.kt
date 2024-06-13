package com.dicoding.sewez.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("/api/jenis-bahan")
    suspend fun getJenisBahan(): Response<List<String>>

    @GET("/api/warna")
    suspend fun getWarna(): Response<List<String>>

    @Multipart
    @POST("/api/submit-order")
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

data class OrderResponse(val success: Boolean, val message: String, val orderDetails: OrderDetails)

data class OrderDetails(val jenisBahan: String, val warna: String, val imageUrl: String, val orderSummary: String)