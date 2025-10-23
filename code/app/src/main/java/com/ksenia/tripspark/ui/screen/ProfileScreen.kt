package com.ksenia.tripspark.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ksenia.tripspark.ui.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val user by authViewModel.currentUser.collectAsState()
    val isLoaded by authViewModel.isLoading.collectAsState()

    LaunchedEffect(isLoaded) {
        if (isLoaded && user == null) {
            navController.navigate("auth")
        }
    }

    user?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { navController.navigate("interests") }) {
                    Text("←")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AvatarImage(
                    avatarUrl = user!!.imageId,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = user!!.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = user!!.email, fontSize = 16.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    authViewModel.logout()
                    navController.navigate("auth")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Выйти", color = Color.White)
            }
        }
    }
}
