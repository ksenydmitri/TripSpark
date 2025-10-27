package com.ksenia.tripspark.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.ksenia.tripspark.R
import com.ksenia.tripspark.ui.components.AuthCard
import com.ksenia.tripspark.ui.components.ErrorWindow
import com.ksenia.tripspark.ui.components.ProgressCircular
import com.ksenia.tripspark.ui.components.RegistrationCard
import com.ksenia.tripspark.ui.viewmodel.AuthViewModel

@Composable
fun AuthScreen(navController: NavController, authViewModel: AuthViewModel = hiltViewModel()) {
    val user = authViewModel.currentUser.collectAsState()
    val isLoading = authViewModel.isLoading.collectAsState().value
    val isLogged = user.value?.id?.isNotBlank()
    val isRegistration =authViewModel.isRegistration.collectAsState().value
    val errorMessage = authViewModel.errorMessage.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.back_2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {

            if (isLoading) {
                ProgressCircular()
                return
            }

            if (isLogged!!){
                navController.navigate("profile")
            }

            if (isRegistration){
                RegistrationCard(
                    onRegister = { email, password, name ->
                        authViewModel.registerUser(email,password,name)
                    },
                    onSwitchToLogin = {authViewModel.onSwitchToLogin()}
                )
            } else {
                AuthCard(
                    onLogin = { email, password ->
                        authViewModel.authUserWithEmailAndPassword(email, password)
                    },
                    onSwitchToRegistration = { authViewModel.onSwitchToRegistration()}
                )
            }

            if (errorMessage != null){
                ErrorWindow(modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 16.dp),
                    eMessage = errorMessage)
            }
        }
    }
}