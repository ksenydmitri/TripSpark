package com.ksenia.tripspark.ui.screen

import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ksenia.tripspark.domain.model.Destination
import com.ksenia.tripspark.domain.model.Recommendation
import com.ksenia.tripspark.domain.usecase.wishlist.WishlistUseCases
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
    val user by viewModel.currentUser.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize())  {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                AvatarImage(
                avatarUrl = user?.imageId,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate("profile")
                    }.border(width = 2.dp, color = Color.Gray)
            )}
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
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
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { navController.navigate("wishlist") }
                ) {
                    Text(text = "Перейти к вишлисту")
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun RecommendationScreenMock() {
    val mockRecommendation = Recommendation(
        destination = Destination(
            id = "porto",
            name = "Porto",
            description = "City with an atmosphere like your favorite Lisbon",
            location = Location("mock").apply {
                latitude = 41.1579
                longitude = -8.6291
            },
            imageUrl = "https://r2-worker.ksenydmitri.workers.dev/?name=moscow.jpg&bucket=SITIES"
        ),
        relevance = 4.7f
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                Text("👤",
                    fontSize = 24.sp,
                    modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Gray))
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                SwipeableCard(
                    recommendation = mockRecommendation,
                    onSwiped = {},
                    onSwipedRight = {},
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Button(onClick = {}) {
                    androidx.compose.material3.Text(text = "Перейти к вишлисту")
                }
            }
        }
    }
}
