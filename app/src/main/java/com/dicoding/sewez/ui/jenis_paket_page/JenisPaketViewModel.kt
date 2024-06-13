package com.dicoding.sewez.ui.jenis_paket_page

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.sewez.network.RetrofitInstance
import com.dicoding.sewez.network.ApiService
import com.dicoding.sewez.network.OrderResponse
import com.dicoding.sewez.utils.createCustomTempFile
import com.dicoding.sewez.utils.reduceFileImage
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class JenisPaketViewModel : ViewModel() {

    var bahanList: List<String> by mutableStateOf(emptyList())
    var warnaList: List<String> by mutableStateOf(emptyList())
    var orderResponse: OrderResponse? by mutableStateOf(null)
    private var selectedFile: File? = null

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val bahanResponse = RetrofitInstance.api.getJenisBahan()
                if (bahanResponse.isSuccessful) {
                    bahanList = bahanResponse.body() ?: emptyList()
                }

                val warnaResponse = RetrofitInstance.api.getWarna()
                if (warnaResponse.isSuccessful) {
                    warnaList = warnaResponse.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun uploadDesignToServer(uri: Uri, context: Context) {
        viewModelScope.launch {
            try {
                val imageFile = createCustomTempFile(context)
                val inputStream = context.contentResolver.openInputStream(uri)
                val outputStream = imageFile.outputStream()
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()

                val reducedFile = reduceFileImage(imageFile)
                selectedFile = reducedFile

                Toast.makeText(context, "Design uploaded successfully", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun submitOrder(
        context: Context,
        bahan: String,
        warna: String,
        jumlahS: String,
        jumlahM: String,
        jumlahL: String,
        jumlahXL: String
    ) {
        viewModelScope.launch {
            selectedFile?.let { file ->
                val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
                val jenisBahan = bahan.toRequestBody("text/plain".toMediaTypeOrNull())
                val warnaBahan = warna.toRequestBody("text/plain".toMediaTypeOrNull())
                val jumlahS = jumlahS.toRequestBody("text/plain".toMediaTypeOrNull())
                val jumlahM = jumlahM.toRequestBody("text/plain".toMediaTypeOrNull())
                val jumlahL = jumlahL.toRequestBody("text/plain".toMediaTypeOrNull())
                val jumlahXL = jumlahXL.toRequestBody("text/plain".toMediaTypeOrNull())

                try {
                    val response = RetrofitInstance.api.submitOrder(
                        multipartBody,
                        jenisBahan,
                        warnaBahan,
                        jumlahS,
                        jumlahM,
                        jumlahL,
                        jumlahXL
                    )

                    if (response.isSuccessful) {
                        orderResponse = response.body()
                        Toast.makeText(context, "Order submitted successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to submit order", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}