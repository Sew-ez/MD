package com.dicoding.sewez

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.sewez.ui.*
import com.dicoding.sewez.ui.auth.IntroScreen
import com.dicoding.sewez.ui.auth.LoginScreen
import com.dicoding.sewez.ui.auth.RegisterScreen
import com.dicoding.sewez.ui.detail_pesanan_page.DetailPesananScreen
import com.dicoding.sewez.network.OrderDetails
import com.dicoding.sewez.network.RetrofitInstance
import com.dicoding.sewez.utils.PreferencesHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitInstance.initialize(this) // Initialize Retrofit with context

        setContent {
            val navController = rememberNavController()
            val isLoggedIn = remember { mutableStateOf(PreferencesHelper.getLoginSession(this) != null) }

            Scaffold(
                bottomBar = {
                    if (isLoggedIn.value) {
                        val route = navController.currentBackStackEntry?.destination?.route
                        if (route != "login" && route != "register" && route != "intro" && route != "detail_pesanan" && route != "jenis_paket") {
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
                    composable(
                        "detail_pesanan/{orderDetails}",
                        arguments = listOf(navArgument("orderDetails") { type = NavType.ParcelableType(OrderDetails::class.java) })
                    ) { backStackEntry ->
                        val orderDetails = backStackEntry.arguments?.getParcelable<OrderDetails>("orderDetails")
                        orderDetails?.let { DetailPesananScreen(navController = navController, orderDetails = it) }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("home", R.drawable.ic_home, "Home"),
        BottomNavItem("cart", R.drawable.ic_cart, "Cart"),
        BottomNavItem("user", R.drawable.ic_user, "User")
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp,
                        color = if (currentRoute == item.route) Color.Blue else Color.Gray
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { startRoute ->
                                popUpTo(startRoute) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                alwaysShowLabel = true,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Gray
            )
        }
    }
}

data class BottomNavItem(val route: String, val icon: Int, val label: String)

@Composable
fun CartScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Keranjang Belanja", fontSize = 24.sp)
    }
}