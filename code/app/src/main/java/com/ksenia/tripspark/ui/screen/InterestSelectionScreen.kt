package com.ksenia.tripspark.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ksenia.tripspark.R
import com.ksenia.tripspark.domain.model.Interest

@Preview
@Composable
fun InterestSelectionScreen() {
    val interests = listOf(
        Interest("Пляжный отдых", "beach"),
        Interest("Горные лыжи", "skiing")
    )
    var selected by remember { mutableStateOf(emptyList<Interest>()) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
            Text("TripSpark")
            AvatarImage()}
        OptimizedSvg()
        InterestsList(
            interests = interests,
            selectedInterests = selected,
            onInterestToggle = { interest ->
                selected = if (selected.contains(interest)) {
                    selected - interest
                } else {
                    selected + interest
                }
            }
        )
        GetRecommendationsButton(
            isEnabled = selected.isNotEmpty(),
            onClick = { }
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
        onClick = {},
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
fun AvatarImage(bitmap: ImageBitmap) {
    Box {
        Image(
            painter = painterResource(R.drawable.avatar_base),
            contentDescription = "User avatar",
            modifier = Modifier
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