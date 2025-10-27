package com.ksenia.tripspark.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
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
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ksenia.tripspark.domain.model.Continent

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

    val pageSize = 10
    var currentPage by remember { mutableStateOf(0) }

    val pagedInterests = interests.chunked(pageSize)
    val currentInterests = pagedInterests.getOrNull(currentPage) ?: emptyList()
    val hasNextPage = currentPage < pagedInterests.lastIndex

    if (!isLoading && (user == null || user!!.id.isBlank())) {
        navController.navigate("auth") {
            popUpTo("interest_selection") { inclusive = true }
        }
        return
    }

    if (isLoading) {
        ProgressCircular()
        return
    }

    InterestSelectionContent(
        user = user,
        interests = interests,
        continents = continents,
        currentPage = currentPage,
        onPageChange = { newPage -> currentPage = newPage },
        onInterestToggle = { interest -> viewModel.toggleInterest(interest.id) },
        onAvatarClick = { navController.navigate("profile") },
        onGetRecommendations = { navController.navigate("recommendations") },
        onContinentToggle = { id -> viewModel.toggleContinent(id) }
    )
}

@Composable
fun InterestSelectionContent(
    user: com.ksenia.tripspark.domain.model.User?,
    interests: List<InterestViewModel.SelectableInterest>,
    continents: List<InterestViewModel.SelectableContinent>,
    currentPage: Int,
    onPageChange: (Int) -> Unit,
    onInterestToggle: (InterestViewModel.SelectableInterest) -> Unit,
    onAvatarClick: () -> Unit,
    onGetRecommendations: () -> Unit,
    onContinentToggle: (String) -> Unit
) {
    val pageSize = 10
    val pagedInterests = interests.chunked(pageSize)
    val currentInterests = pagedInterests.getOrNull(currentPage) ?: emptyList()
    val hasNextPage = currentPage < pagedInterests.lastIndex

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

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

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
                    .clickable { onAvatarClick() }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        OptimizedSvgWithMarkers(
            continents = continents,
            onToggle = onContinentToggle
        )

        InterestsList(
            interests = currentInterests,
            onInterestToggle = onInterestToggle
        )

        if (pagedInterests.size > 1) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentPage > 0) {
                    androidx.compose.material3.Button(onClick = {
                        onPageChange(currentPage - 1)
                    },
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = colorResource(R.color.white),
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = colorResource(R.color.white),
                        )) {
                        Text(text = "←",
                            fontSize = 38.sp)
                    }
                } else {
                    Spacer(modifier = Modifier.size(48.dp))
                }

                if (hasNextPage) {
                    androidx.compose.material3.Button(onClick = {
                        onPageChange(currentPage + 1)
                    },
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = colorResource(R.color.white),
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = colorResource(R.color.white),
                        )) {
                        Text(text = "→", fontSize = 38.sp)
                    }
                } else {
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        GetRecommendationsButton(
            isEnabled = interests.any { it.isChosen },
            onClick = onGetRecommendations
        )
    }
}