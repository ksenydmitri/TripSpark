package com.ksenia.tripspark.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ksenia.tripspark.ui.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(navController: NavController,
                  authViewModel: AuthViewModel = hiltViewModel()
) {
    val user by authViewModel.currentUser.collectAsState()
    val isLoaded by authViewModel.isLoading.collectAsState()

    LaunchedEffect(isLoaded) {
        if (isLoaded && user == null) {
            navController.navigate("auth")
        }
    }
}