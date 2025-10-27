package com.ksenia.tripspark.ui.screen

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.ksenia.tripspark.R
import com.ksenia.tripspark.ui.components.AvatarWithChangeButton
import com.ksenia.tripspark.ui.components.ProgressCircular
import com.ksenia.tripspark.ui.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val user by authViewModel.currentUser.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    val context = LocalContext.current

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                authViewModel.uploadUserAvatar(context, uri)
            }
        }
    )

    if (isLoading) {
        ProgressCircular()
        return
    }

    if (user == null || user!!.id.isBlank()) {
        navController.navigate("auth")
        return
    }

    ProfileScreenContent(
        user = user!!,
        onAvatarClick = {
            pickImageLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onBackClick = { navController.navigate("interests") },
        onLogoutClick = {
            authViewModel.logout()
            navController.navigate("auth")
        }
    )
}

@Composable
fun ProfileScreenContent(
    user: com.ksenia.tripspark.domain.model.User,
    onAvatarClick: () -> Unit,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
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

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBackClick,
                colors = ButtonColors(
                    contentColor = colorResource(R.color.white),
                    containerColor = Color.Transparent,
                    disabledContentColor = colorResource(R.color.primary_blue),
                    disabledContainerColor = Color.Transparent),
                ) {
                Text(text = "←",
                   fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold)
            }
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = onLogoutClick,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.error_red))
            ) {
                Text("Log Out", color = Color.White)
            }

        }
        Spacer(modifier = Modifier.height(36.dp))
        AvatarWithChangeButton(
            avatarUrl = user.imageId,
            onAddAvatarClick = onAvatarClick,
            modifier = Modifier.size(150.dp)
                .border(width = 2.dp,
                color = colorResource(R.color.white),
                shape = CircleShape)
        )
        Spacer(modifier = Modifier.height(100.dp))
        Card(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = user.name, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = user.email, fontSize = 16.sp, color = Color.Gray)
                user.interests.map { interest ->
                    Text(text = interest.name,
                        fontSize = 16.sp,
                        color = Color.Gray)
                }
            }
        }
    }
}