package com.ksenia.tripspark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Recommend
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ksenia.tripspark.ui.screen.AuthScreen
import com.ksenia.tripspark.ui.screen.InterestSelectionScreen
import com.ksenia.tripspark.ui.screen.MapScreen
import com.ksenia.tripspark.ui.screen.ProfileScreen
import com.ksenia.tripspark.ui.screen.RecommendationScreen
import com.ksenia.tripspark.ui.screen.WishlistScreen
import com.ksenia.tripspark.ui.theme.TripSparkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripSparkTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val items = listOf(
                    NavigationItem("interests", Icons.Default.Settings, "Интересы"),
                    NavigationItem("recommendations", Icons.Default.Recommend, "Идеи"),
                    NavigationItem("map", Icons.Default.Map, "Карта"),
                    NavigationItem("wishlist", Icons.Default.Favorite, "Желания"),
                    NavigationItem("profile", Icons.Default.Person, "Профиль")
                )

                val showBottomBar = currentDestination?.route in items.map { it.route }

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar {
                                items.forEach { item ->
                                    NavigationBarItem(
                                        icon = { Icon(item.icon, contentDescription = item.label) },
                                        label = { Text(item.label) },
                                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                                        onClick = {
                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "auth",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("auth") {
                            AuthScreen(navController)
                        }
                        composable("profile") {
                            ProfileScreen(navController)
                        }
                        composable("interests") {
                            InterestSelectionScreen(navController)
                        }
                        composable("recommendations") {
                            RecommendationScreen(navController)
                        }
                        composable("wishlist") {
                            WishlistScreen(navController)
                        }
                        composable("map") {
                            MapScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

data class NavigationItem(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String)
