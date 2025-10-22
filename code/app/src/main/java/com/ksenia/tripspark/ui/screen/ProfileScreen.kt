package com.ksenia.tripspark.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ksenia.tripspark.ui.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(navController: NavController,
                  authViewModel: AuthViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        if (!authViewModel.isUserLoggedIn()) {
            navController.navigate("auth")
        }
    }

}