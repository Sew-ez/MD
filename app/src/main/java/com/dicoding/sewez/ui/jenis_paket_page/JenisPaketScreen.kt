package com.dicoding.sewez.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dicoding.sewez.ui.jenis_paket_page.JenisPaketViewModel
import kotlin.Exception

@Composable
fun JenisPaketScreen(navController: NavController, viewModel: JenisPaketViewModel = viewModel()) {
    var selectedBahan by remember { mutableStateOf("") }
    var selectedWarna by remember { mutableStateOf("") }
    var jumlahS by remember { mutableStateOf("0") }
    var jumlahM by remember { mutableStateOf("0") }
    var jumlahL by remember { mutableStateOf("0") }
    var jumlahXL by remember { mutableStateOf("0") }

    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.uploadDesignToServer(it, context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Kaos :", fontSize = 24.sp, modifier = Modifier.padding(vertical = 8.dp))
        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenuSection(
            title = "Jenis Bahan",
            items = viewModel.bahanList,
            selectedItem = selectedBahan,
            onItemSelected = { selectedBahan = it }
        )

        DropdownMenuSection(
            title = "Warna",
            items = viewModel.warnaList,
            selectedItem = selectedWarna,
            onItemSelected = { selectedWarna = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        QuantityInputSection(
            title = "Ukuran S",
            value = jumlahS,
            onValueChange = { jumlahS = it }
        )
        QuantityInputSection(
            title = "Ukuran M",
            value = jumlahM,
            onValueChange = { jumlahM = it }
        )
        QuantityInputSection(
            title = "Ukuran L",
            value = jumlahL,
            onValueChange = { jumlahL = it }
        )
        QuantityInputSection(
            title = "Ukuran XL",
            value = jumlahXL,
            onValueChange = { jumlahXL = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Upload Design")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (selectedBahan.isEmpty() || selectedWarna.isEmpty()) {
                    Toast.makeText(context, "Please select both Jenis Bahan and Warna", Toast.LENGTH_SHORT).show()
                } else {
                    try {
                        viewModel.submitOrder(
                            context,
                            selectedBahan,
                            selectedWarna,
                            jumlahS,
                            jumlahM,
                            jumlahL,
                            jumlahXL
                        )
                    } catch (e: Exception) {
                        Toast.makeText(context, "Failed to submit the order: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }

    // Navigate to Detail Pesanan Page when order is successfully submitted
    viewModel.orderResponse?.let {
        LaunchedEffect(Unit) {
            navController.navigate("detail_pesanan")
        }
    }
}

@Composable
fun DropdownMenuSection(
    title: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = title, fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .clickable { expanded = true }
                .padding(16.dp)
        ) {
            Text(text = if (selectedItem.isEmpty()) "Select $title" else selectedItem)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { label ->
                DropdownMenuItem(onClick = {
                    onItemSelected(label)
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}

@Composable
fun QuantityInputSection(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(text = title, fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        )
    }
}