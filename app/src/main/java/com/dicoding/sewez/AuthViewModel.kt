package com.dicoding.sewez.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.sewez.network.*
import com.dicoding.sewez.utils.PreferencesHelper
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    var isSuccess: Boolean by mutableStateOf(false)
    var responseMessage: String by mutableStateOf("")
    var userToken: String? by mutableStateOf(null)

    fun registerUser(context: Context, name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.registerUser(RegisterRequest(name, email, password))
                if (response.isSuccessful) {
                    isSuccess = response.body()?.error == false
                    responseMessage = response.body()?.message ?: ""
                } else {
                    isSuccess = false
                    responseMessage = response.errorBody()?.string() ?: "Unknown error"
                }
                Toast.makeText(context, responseMessage, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                isSuccess = false
                responseMessage = e.message ?: "Error occurred"
                Toast.makeText(context, responseMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loginUser(context: Context, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.loginUser(LoginRequest(email, password))
                if (response.isSuccessful) {
                    isSuccess = response.body()?.error == false
                    responseMessage = response.body()?.message ?: ""
                    userToken = response.body()?.loginResult?.token
                    if (isSuccess && userToken != null) {
                        PreferencesHelper.saveLoginSession(context, userToken!!)
                    }
                } else {
                    isSuccess = false
                    responseMessage = response.errorBody()?.string() ?: "Unknown error"
                }
                Toast.makeText(context, responseMessage, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                isSuccess = false
                responseMessage = e.message ?: "Error occurred"
                Toast.makeText(context, responseMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun logoutUser(context: Context) {
        PreferencesHelper.clearLoginSession(context)
        isSuccess = false
        userToken = null
    }
}