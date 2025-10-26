package com.ksenia.tripspark.ui.screen

import android.R.attr.password
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ksenia.tripspark.R
import com.ksenia.tripspark.ui.components.AuthCard
import com.ksenia.tripspark.ui.components.ProgressCircular
import com.ksenia.tripspark.ui.viewmodel.AuthViewModel

@Composable
fun AuthScreen(navController: NavController, authViewModel: AuthViewModel = hiltViewModel()) {
    val user = authViewModel.currentUser.collectAsState()
    val isLoading = authViewModel.isLoading.collectAsState().value
    val isLogged = user.value != null

    if (isLoading) {
        ProgressCircular()
        return
    }

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

        AuthScreenContent(
            navController = navController,
            onLogin = { email, password ->
                authViewModel.authUserWithEmailAndPassword(email, password)
                navController.navigate("interests")
            },
            isLogged
        )

    }
}


@Composable
fun AuthScreenContent(
    navController: NavController,
    onLogin: (String, String) -> Unit,
    isLogged: Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth(),
            contentAlignment = Alignment.TopStart) {
            if (isLogged){
                IconButton(
                    onClick = { navController.navigate("interests")},
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Next",
                        tint = colorResource(R.color.nature_mountain),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        AuthCard(
            onLogin = onLogin,
            onReturnToInterests = {
                navController.navigate("interests")
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    val navController = rememberNavController()
    AuthScreenContent(
        navController = navController,
        onLogin = { _, _ -> /* no-op for preview */ },
        isLogged = true
    )
}
