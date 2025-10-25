package com.ksenia.tripspark.ui.screen

import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ksenia.tripspark.domain.model.Destination
import com.ksenia.tripspark.domain.model.Recommendation
import com.ksenia.tripspark.ui.components.AvatarImage
import com.ksenia.tripspark.ui.components.SwipeableCard
import com.ksenia.tripspark.ui.viewmodel.RecommendationViewModel
import com.ksenia.tripspark.ui.viewmodel.WishlistViewModel

@Composable
fun RecommendationScreen(
    navController: NavController,
    viewModel: RecommendationViewModel = hiltViewModel()
) {
    val cards by viewModel.recommendations.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            cards.reversed().forEach { card ->
                SwipeableCard(
                    recommendation = card,
                    onSwiped = { viewModel.removeTopCard() },
                    onSwipedRight = {
                        val userId = viewModel.currentUser.value?.id ?: return@SwipeableCard
                        viewModel.addToWishlist(userId, card)
                        viewModel.removeTopCard()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Button(
                onClick = { navController.navigate("wishlist") }
            ) {
                androidx.compose.material3.Text(text = "Перейти к вишлисту")
            }
        }
    }
}
