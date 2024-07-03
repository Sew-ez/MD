package com.dicoding.sewez.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dicoding.sewez.R

@Composable
fun IntroScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_intro_image),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Selamat datang di Dicoding Story!",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Mau berbagi ilmu tentang Android, iOS, Machine Learning, Cloud, Front-End, Back-End, dan bidang IT lainnya? Di Dicoding Story aja!",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.navigate("login") }) {
                Text("Masuk")
            }
            Button(onClick = { navController.navigate("register") }) {
                Text("Daftar")
            }
        }
    }
}