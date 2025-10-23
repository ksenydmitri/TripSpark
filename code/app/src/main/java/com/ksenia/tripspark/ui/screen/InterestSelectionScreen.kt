package com.ksenia.tripspark.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ksenia.tripspark.R
import com.ksenia.tripspark.domain.model.Interest
import com.ksenia.tripspark.ui.viewmodel.InterestViewModel


@Composable
fun InterestSelectionScreen(navController: NavController, viewModel: InterestViewModel = hiltViewModel(
    checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        },
    null
)
) {
    val interests by viewModel.interests.collectAsState()
    val selected = interests.filter { it.isChosen }
    val user by viewModel.currentUser.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
            Text(user?.name ?: "TripSpark")
            AvatarImage(
                avatarUrl = user?.imageId,
                modifier = Modifier.clickable {
                    navController.navigate("profile")
                }
            )}
        OptimizedSvg()
        InterestsList(
            interests = interests,
            selectedInterests = selected,
            onInterestToggle = { interest ->
                viewModel.toggleInterest(interest.id)
            }
        )
        GetRecommendationsButton(
            isEnabled = selected.isNotEmpty(),
            onClick = {
                viewModel.saveSelectedInterests(selected)
                navController.navigate("recommendations")
            }
        )
    }
}

@Composable
fun OptimizedSvg() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .padding(all = 20.dp)
            .clip(RectangleShape)
    ) {
        Image(
            painter = painterResource(R.drawable.world_map),
            contentDescription = "optimized",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun ChoiceInterestButton(
    interest: Interest,
    isSelected: Boolean,
    onInterestToggle: (Interest) -> Unit
) {
    Button(
        onClick = {
            onInterestToggle(interest)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) {
                colorResource(id = R.color.teal_700)
            } else {
                colorResource(id = R.color.teal_200)
            },
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(4.dp)
    ) {
        Text(
            text = interest.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun GetRecommendationsButton(isEnabled: Boolean,
                             onClick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .padding(horizontal = 16.dp),
    ) { Button(
        onClick = onClick,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(vertical = 15.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = colorResource(R.color.purple_700)
        )

    ) {
        Text(
            text = "GET RECOMMENDATIONS",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    } }
}

@Composable
fun AvatarImage(avatarUrl: String?,
                modifier: Modifier = Modifier,
                placeholderRes: Int = R.drawable.avatar_base) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(avatarUrl)
            .crossfade(true)
            .error(placeholderRes)
            .placeholder(placeholderRes)
            .build()
    )
    Box {
        Image(
            painter = painter,
            contentDescription = "User avatar",
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun InterestsList(interests: List<Interest>,
                  selectedInterests: List<Interest>,
                  onInterestToggle: (Interest) -> Unit){
    Box(
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .fillMaxWidth()
    ) {
        LazyColumn {
            items(count = interests.size){interest ->
                ChoiceInterestButton(
                    interest = interests[interest],
                    isSelected = selectedInterests.contains(interests[interest]),
                    onInterestToggle = onInterestToggle
                )
            }
        }
    }
}