package com.dicoding.sewez

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dicoding.sewez.ui.BottomNavBar
import com.dicoding.sewez.ui.CategoryScreen
import com.dicoding.sewez.ui.HomeScreen
import com.dicoding.sewez.ui.JenisPaketScreen
import com.dicoding.sewez.ui.SEWEZTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SEWEZTheme {
                val navController: NavHostController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavBar(navController) }
                ) { innerPadding ->
                    // Menyesuaikan innerPadding
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding), // Menggunakan contentPadding dari Scaffold
                        color = MaterialTheme.colors.background
                    ) {
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") { HomeScreen(navController) }
                            composable("category/{category}") { backStackEntry ->
                                val category = backStackEntry.arguments?.getString("category") ?: "T-Shirt"
                                CategoryScreen(navController, category)
                            }
                            composable("cart") { CartScreen() }
                            composable("user") { UserScreen() }
                            composable("jenis_paket") {
                                JenisPaketScreen(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartScreen() {
    Text(text = "Cart Screen")
}

@Composable
fun UserScreen() {
    Text(text = "User Screen")
}
