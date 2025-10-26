package com.ksenia.tripspark.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import com.ksenia.tripspark.R
import com.ksenia.tripspark.ui.components.AvatarImage
import com.ksenia.tripspark.ui.components.GetRecommendationsButton
import com.ksenia.tripspark.ui.components.InterestsList
import com.ksenia.tripspark.ui.components.OptimizedSvgWithMarkers
import com.ksenia.tripspark.ui.components.ProgressCircular
import com.ksenia.tripspark.ui.viewmodel.InterestViewModel


@Composable
fun InterestSelectionScreen(
    navController: NavController,
    viewModel: InterestViewModel = hiltViewModel(
        checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        },
        null
    )
) {
    val interests by viewModel.interests.collectAsState()
    val continents by viewModel.continents.collectAsState()
    val user by viewModel.currentUser.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (!isLoading && user == null){
        navController.navigate("auth")
    }

    if (isLoading) {
        ProgressCircular()
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, top = 16.dp)
        ) {
            AvatarImage(
                avatarUrl = user?.imageId,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(3.dp, colorResource(R.color.primary_blue), CircleShape)
                    .clickable { navController.navigate("profile") }
            )
        }

        OptimizedSvgWithMarkers(
            continents = continents,
            onToggle = { id -> viewModel.toggleContinent(id) }
        )

        InterestsList(
            interests = interests,
            onInterestToggle = { interest -> viewModel.toggleInterest(interest.id) }
        )

        GetRecommendationsButton(
            isEnabled = interests.any { it.isChosen },
            onClick = {
                navController.navigate("recommendations")
            }
        )
    }
}