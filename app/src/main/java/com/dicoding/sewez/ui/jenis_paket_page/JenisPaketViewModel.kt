package com.dicoding.sewez.ui.jenis_paket_page

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.sewez.network.*
import com.dicoding.sewez.utils.PreferencesHelper
import kotlinx.coroutines.launch

class JenisPaketViewModel : ViewModel() {
    var bahanList: List<JenisBahan> by mutableStateOf(emptyList())
    var warnaList: List<Warna> by mutableStateOf(emptyList())
    var logoList: List<JenisBahanLogo> by mutableStateOf(emptyList())
    var orderResponse: OrderResponse? by mutableStateOf(null)

    fun fetchData(context: Context) {
        viewModelScope.launch {
            try {
                val token = PreferencesHelper.getLoginSession(context) ?: return@launch
                Log.d("Token", "Token: $token")

                val jenisBahanResponse = RetrofitInstance.api.getJenisBahan("Bearer $token")
                if (jenisBahanResponse.isSuccessful) {
                    Log.d("JenisBahan", "Fetched successfully: ${jenisBahanResponse.body()?.data}")
                    bahanList = jenisBahanResponse.body()?.data ?: emptyList()
                } else {
                    Log.e("JenisBahan", "Failed to fetch data: ${jenisBahanResponse.errorBody()?.string()}")
                }

                val warnaResponse = RetrofitInstance.api.getWarna("Bearer $token")
                if (warnaResponse.isSuccessful) {
                    Log.d("Warna", "Fetched successfully: ${warnaResponse.body()?.data}")
                    warnaList = warnaResponse.body()?.data ?: emptyList()
                } else {
                    Log.e("Warna", "Failed to fetch data: ${warnaResponse.errorBody()?.string()}")
                }

                val logoResponse = RetrofitInstance.api.getJenisBahanLogo("Bearer $token")
                if (logoResponse.isSuccessful) {
                    Log.d("JenisBahanLogo", "Fetched successfully: ${logoResponse.body()?.data}")
                    logoList = logoResponse.body()?.data ?: emptyList()
                } else {
                    Log.e("JenisBahanLogo", "Failed to fetch data: ${logoResponse.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}