package com.dicoding.sewez

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dicoding.sewez.ui.*
import com.dicoding.sewez.ui.auth.IntroScreen
import com.dicoding.sewez.ui.auth.LoginScreen
import com.dicoding.sewez.ui.auth.RegisterScreen
import com.dicoding.sewez.ui.detail_pesanan_page.DetailPesananScreen
import com.dicoding.sewez.utils.PreferencesHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SEWEZTheme {
                val navController = rememberNavController()
                val isLoggedIn = remember { mutableStateOf(PreferencesHelper.getLoginSession(this) != null) }

                Scaffold(
                    bottomBar = {
                        if (isLoggedIn.value) {
                            val route = navController.currentBackStackEntry?.destination?.route
                            if (route != "login" && route != "register" && route != "intro") {
                                BottomNavBar(navController = navController)
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (isLoggedIn.value) "home" else "intro",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("intro") { IntroScreen(navController = navController) }
                        composable("login") {
                            LoginScreen(navController = navController) {
                                isLoggedIn.value = true
                                navController.navigate("home") {
                                    popUpTo("intro") { inclusive = true }
                                }
                            }
                        }
                        composable("register") { RegisterScreen(navController = navController) }
                        composable("home") { HomeScreen(navController = navController) }
                        composable("category/{category}") { backStackEntry ->
                            CategoryScreen(navController = navController, category = backStackEntry.arguments?.getString("category") ?: "")
                        }
                        composable("cart") { CartScreen(navController = navController) }
                        composable("user") { UserScreen(navController = navController) }
                        composable("jenis_paket") { JenisPaketScreen(navController = navController) }
                        composable("detail_pesanan") { DetailPesananScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun CartScreen(navController: NavHostController) {
    Text(text = "Keranjang Belanja")
}