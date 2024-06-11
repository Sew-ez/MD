package com.dicoding.sewez.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Ganti penggunaan Product dengan CategoryItem
@Composable
fun CategoryScreen(navController: NavController, category: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "$category Products",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Gunakan data Dummy dari CategoryItem
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(DummyData.categories) { categoryItem ->
                // Filter item berdasarkan kategori
                if (categoryItem.name == category) {
                    CategoryItemRow(categoryItem)
                }
            }
        }
    }
}

// Sesuaikan fungsi CategoryItemRow sesuai dengan yang Anda buat sebelumnya
@Composable
fun CategoryItemRow(categoryItem: CategoryItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable { /* Navigasi atau tindakan lainnya */ },
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = categoryItem.imageResId),
                contentDescription = categoryItem.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = categoryItem.name,
                    style = MaterialTheme.typography.h6
                )
                // Tambahkan deskripsi jika diperlukan
            }
        }
    }
}
