package com.ksenia.tripspark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ksenia.tripspark.ui.screen.AuthScreen
import com.ksenia.tripspark.ui.screen.InterestSelectionScreen
import com.ksenia.tripspark.ui.screen.ProfileScreen
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
                }
            }
        }
    }
}