package com.dicoding.sewez.ui.detail_pesanan_page

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.sewez.ui.jenis_paket_page.JenisPaketViewModel

@Composable
fun DetailPesananScreen(viewModel: JenisPaketViewModel = viewModel()) {
    val orderResponse = viewModel.orderResponse

    orderResponse?.let { response ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Detail Pesanan", fontSize = 24.sp, modifier = Modifier.padding(vertical = 8.dp))
            Text(text = "Jenis Bahan: ${response.orderDetails.jenisBahan}", fontSize = 18.sp, modifier = Modifier.padding(vertical = 4.dp))
            Text(text = "Warna: ${response.orderDetails.warna}", fontSize = 18.sp, modifier = Modifier.padding(vertical = 4.dp))
            // Add more fields as needed to display the order details

            Button(onClick = { /* Handle Add to Cart */ }, modifier = Modifier.fillMaxWidth()) {
                Text("Add to Cart")
            }

            Button(onClick = { /* Handle Buy */ }, modifier = Modifier.fillMaxWidth()) {
                Text("Buy")
            }
        }
    } ?: run {
        Text(text = "No order details available", fontSize = 18.sp, modifier = Modifier.padding(16.dp))
    }
}