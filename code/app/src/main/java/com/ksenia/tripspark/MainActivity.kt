package com.ksenia.tripspark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ksenia.tripspark.ui.screen.AuthScreen
import com.ksenia.tripspark.ui.screen.InterestSelectionScreen
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
                NavHost(navController = navController, startDestination = "interests") {
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
                        WishlistScreen(navController) }
                }
            }
        }
    }
}