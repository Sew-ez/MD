package com.dicoding.sewez.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dicoding.sewez.R
import com.dicoding.sewez.ui.AuthViewModel

@Composable
fun UserScreen(navController: NavHostController, authViewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current

    // Debugging: Snackbar untuk memastikan masuk ke UserScreen
    Snackbar(
        modifier = Modifier.padding(8.dp),
        content = { Text(text = "Navigated to UserScreen") }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "User Profile", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        // Gambar Profil
        Image(
            painter = painterResource(R.drawable.ic_profile),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .padding(8.dp)
                .background(Color.Gray, shape = CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Username
        Text(text = "Username", fontSize = 20.sp, modifier = Modifier.padding(vertical = 8.dp))

        Spacer(modifier = Modifier.height(32.dp))

        // Tombol Logout
        Button(
            onClick = {
                authViewModel.logoutUser(context)
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA726)),
            shape = CircleShape,
            modifier = Modifier
                .height(48.dp)
                .width(200.dp)
        ) {
            Text(text = "Logout", color = Color.White)
        }
    }
}