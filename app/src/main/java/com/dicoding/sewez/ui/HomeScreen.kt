package com.dicoding.sewez.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import com.dicoding.sewez.R

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Section Search Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            BasicTextField(
                value = "",
                onValueChange = {},
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.1f)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        innerTextField()
                        IconButton(onClick = { /* Search Action */ }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }

        // Section Category
        Text(text = "Category", fontSize = 20.sp, modifier = Modifier.padding(vertical = 8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            CategoryItem(imageRes = R.drawable.ic_tshirt, text = "T-Shirt", navController = navController)
            CategoryItem(imageRes = R.drawable.ic_jacket, text = "Jacket", navController = navController)
            CategoryItem(imageRes = R.drawable.ic_totebag, text = "Tote-bag", navController = navController)
            CategoryItem(imageRes = R.drawable.ic_cap, text = "Cap", navController = navController)
        }

        // Call the FoundYourDesignSection function
        FoundYourDesignSection()

        Spacer(modifier = Modifier.weight(1f))

        // Section Bottom Nav Bar
        BottomNavBar(navController)
    }
}

//Section Found Your Design
@Composable
fun FoundYourDesignSection() {

    // Create a vertical scrollable column
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray) // Warna background untuk seluruh kolom
            .verticalScroll(rememberScrollState()) // Enable vertical scrolling
            .padding(2.dp)
    ) {
        Text(
            text = "Found your design",
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        // List of items to be displayed in vertical scrolling
        listOf(
            R.drawable.item1,
            R.drawable.item2,
            R.drawable.item3,
            R.drawable.item4,
            R.drawable.item5
        ).forEach { item ->
            Image(
                painter = painterResource(id = item),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(vertical = 1.dp, horizontal = 1.dp) // Padding vertical antara item
                    .aspectRatio(8f / 10f)
                    .size(180.dp)
            )
        }
    }
}


@Composable
fun CategoryItem(imageRes: Int, text: String, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            navController.navigate("category/$text")
        }
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .background(Color.LightGray, shape = CircleShape)
                .padding(8.dp)
        )
        Text(text = text, fontSize = 14.sp)
    }
}

