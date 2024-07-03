package com.dicoding.sewez.ui.detail_pesanan_page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.dicoding.sewez.network.OrderDetails

@Composable
fun DetailPesananScreen(navController: NavController, orderDetails: OrderDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Detail Pesanan", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

        Image(
            painter = rememberImagePainter(orderDetails.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(vertical = 8.dp)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Detail Pesanan :", fontSize = 18.sp, style = MaterialTheme.typography.h6)

        TextSection(label = "Jenis Bahan Baju", value = orderDetails.jenisbahan)
        TextSection(label = "Jenis Bahan Logo", value = orderDetails.jenislogo)
        TextSection(label = "Warna", value = orderDetails.warna)
        TextSection(label = "Ukuran S", value = orderDetails.s.toString())
        TextSection(label = "Ukuran M", value = orderDetails.m.toString())
        TextSection(label = "Ukuran L", value = orderDetails.l.toString())
        TextSection(label = "Ukuran XL", value = orderDetails.xl.toString())
        TextSection(label = "Ukuran XXL", value = orderDetails.xxl.toString())
        TextSection(label = "Total", value = "Rp. ${orderDetails.totalPrice},00")

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(modifier = Modifier.weight(1f).padding(8.dp), onClick = { /* Add to Cart */ }) {
                Text("Add to Cart")
            }
            Button(modifier = Modifier.weight(1f).padding(8.dp), onClick = { /* Buy Now */ }) {
                Text("Buy")
            }
        }
    }
}

@Composable
fun TextSection(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp)
        Text(text = value, fontSize = 16.sp, modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(4.dp))
            .border(1.dp, Color.Blue, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp))
    }
}