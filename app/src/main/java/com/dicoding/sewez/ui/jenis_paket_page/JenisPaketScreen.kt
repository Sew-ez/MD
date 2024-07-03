package com.dicoding.sewez.ui

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dicoding.sewez.ui.jenis_paket_page.JenisPaketViewModel

@Composable
fun JenisPaketScreen(navController: NavController, viewModel: JenisPaketViewModel = viewModel()) {
    var selectedBahan by remember { mutableStateOf("") }
    var selectedLogo by remember { mutableStateOf("") }
    var selectedWarna by remember { mutableStateOf("") }
    var jumlahS by remember { mutableStateOf("0") }
    var jumlahM by remember { mutableStateOf("0") }
    var jumlahL by remember { mutableStateOf("0") }
    var jumlahXL by remember { mutableStateOf("0") }
    var jumlahXXL by remember { mutableStateOf("0") }

    val context = LocalContext.current

    // Fetch data when the Composable is composed
    LaunchedEffect(Unit) {
        viewModel.fetchData(context)
    }

    // Logging to verify data fetching
    LaunchedEffect(viewModel.bahanList, viewModel.warnaList, viewModel.logoList) {
        viewModel.bahanList.forEach { Log.d("JenisBahan", it.toString()) }
        viewModel.warnaList.forEach { Log.d("Warna", it.toString()) }
        viewModel.logoList.forEach { Log.d("JenisBahanLogo", it.toString()) }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            Log.d("ImageUpload", "Image URI: $it")
            viewModel.uploadDesignToServer(it, context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Jenis Paket Kaos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 7.dp)
        ) {
            Text(text = "Jenis Bahan Baju", fontSize = 16.sp, modifier = Modifier.weight(1f))
            DropdownMenuSection(
                items = viewModel.bahanList.map { it.type },
                selectedItem = selectedBahan,
                onItemSelected = { item ->
                    Log.d("SelectedBahanBaju", "Selected: $item")
                    selectedBahan = item
                },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 7.dp)
        ) {
            Text(text = "Jenis Bahan Logo", fontSize = 16.sp, modifier = Modifier.weight(1f))
            DropdownMenuSection(
                items = viewModel.logoList.map { it.type },
                selectedItem = selectedLogo,
                onItemSelected = { item ->
                    Log.d("SelectedBahanLogo", "Selected: $item")
                    selectedLogo = item
                },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 7.dp)
        ) {
            Text(text = "Warna", fontSize = 16.sp, modifier = Modifier.weight(1f))
            DropdownMenuSection(
                items = viewModel.warnaList.map { it.color },
                selectedItem = selectedWarna,
                onItemSelected = { item ->
                    Log.d("SelectedWarna", "Selected: $item")
                    selectedWarna = item
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Ukuran:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 7.dp)
        ) {
            Text(text = "S", fontSize = 16.sp, modifier = Modifier.weight(1f))
            QuantityInputSection(
                value = jumlahS,
                onValueChange = { jumlahS = it },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 7.dp)
        ) {
            Text(text = "M", fontSize = 16.sp, modifier = Modifier.weight(1f))
            QuantityInputSection(
                value = jumlahM,
                onValueChange = { jumlahM = it },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 7.dp)
        ) {
            Text(text = "L", fontSize = 16.sp, modifier = Modifier.weight(1f))
            QuantityInputSection(
                value = jumlahL,
                onValueChange = { jumlahL = it },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 7.dp)
        ) {
            Text(text = "XL", fontSize = 16.sp, modifier = Modifier.weight(1f))
            QuantityInputSection(
                value = jumlahXL,
                onValueChange = { jumlahXL = it },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 7.dp)
        ) {
            Text(text = "XXL", fontSize = 16.sp, modifier = Modifier.weight(1f))
            QuantityInputSection(
                value = jumlahXXL,
                onValueChange = { jumlahXXL = it },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { galleryLauncher.launch("image/*") },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF80D8FF), contentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(50.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Upload Design", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (selectedBahan.isEmpty() || selectedWarna.isEmpty() || selectedLogo.isEmpty()) {
                    Toast.makeText(context, "Please select all fields", Toast.LENGTH_SHORT).show()
                } else {
                    val bahanId = viewModel.bahanList.find { it.type == selectedBahan }?.id
                    val warnaId = viewModel.warnaList.find { it.color == selectedWarna }?.id
                    val logoId = viewModel.logoList.find { it.type == selectedLogo }?.id

                    if (bahanId != null && warnaId != null && logoId != null) {
                        try {
                            viewModel.submitOrder(
                                context,
                                jenisproduk = 1,  // Hardcoded for now, can use dropdown later
                                jenisbahan = bahanId,
                                warna = warnaId,
                                jenislogo = logoId,
                                jumlahS = jumlahS.toIntOrNull() ?: 0,
                                jumlahM = jumlahM.toIntOrNull() ?: 0,
                                jumlahL = jumlahL.toIntOrNull() ?: 0,
                                jumlahXL = jumlahXL.toIntOrNull() ?: 0,
                                jumlahXXL = jumlahXXL.toIntOrNull() ?: 0,
                            )
                        } catch (e: Exception) {
                            Toast.makeText(context, "Failed to submit the order: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Invalid fields", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF80D8FF), contentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(45.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Submit", fontSize = 16.sp)
        }
    }

    // Listen to orderResponse changes to navigate to the detail page
    LaunchedEffect(viewModel.orderResponse) {
        viewModel.orderResponse?.let { orderResponse ->
            orderResponse.data?.let { orderDetails ->
                navController.currentBackStackEntry?.arguments?.putParcelable("orderDetails", orderDetails)
                navController.navigate("detailPesanan")
            }
        }
    }
}


    @Composable
    fun DropdownMenuSection(
        items: List<String>,
        selectedItem: String,
        onItemSelected: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = modifier
                .clip(RoundedCornerShape(15.dp))  // Rounded corners
                .border(1.dp, Color(0xFF86B6F6), RoundedCornerShape(15.dp))  // Border with the specified color and rounded corners
                .clickable(onClick = { expanded = true })  // Toggle dropdown expanded state
                .padding(12.dp)
        ) {
            Text(
                text = if (selectedItem.isEmpty()) "Select" else selectedItem,
                fontSize = 16.sp,
                color = if (selectedItem.isEmpty()) Color.Gray else Color.Black
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        }
                    ) {
                        Text(text = item, fontSize = 16.sp)
                    }
                }
            }
        }
    }

@Composable
fun QuantityInputSection(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .background(Color(0xFFE1F5FE), RoundedCornerShape(15.dp))  // Light Blue Background with rounded corners
            .border(1.dp, Color(0xFF86B6F6), RoundedCornerShape(15.dp))  // Border with the specified color and rounded corners
            .padding(12.dp),
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color.Black
        )
    )
}
