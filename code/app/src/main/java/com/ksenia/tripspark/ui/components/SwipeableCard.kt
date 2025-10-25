package com.ksenia.tripspark.ui.components

import android.location.Location
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ksenia.tripspark.domain.model.Destination
import com.ksenia.tripspark.domain.model.Recommendation
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun SwipeableCard(
    recommendation: Recommendation,
    onSwiped: () -> Unit,
    onSwipedRight: () -> Unit,
    modifier: Modifier = Modifier
) {
    val offsetX = remember { Animatable(0f) }
    val rotation = offsetX.value / 60f
    val scope = rememberCoroutineScope()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val swipeThreshold = with(LocalDensity.current) { (screenWidth * 0.25f).toPx() }

    Box(
        modifier = modifier
            .graphicsLayer {
                translationX = offsetX.value
                rotationZ = rotation
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        if (abs(offsetX.value) > swipeThreshold) {
                            val isRightSwipe = offsetX.value > 0
                            val target = if (isRightSwipe) screenWidth.value * 2 else -screenWidth.value * 2
                            scope.launch {
                                offsetX.animateTo(target, animationSpec = tween(durationMillis = 300))
                                if (isRightSwipe) {
                                    Log.e("SWIPE RIGHT", "BEFORE")
                                    onSwipedRight()
                                } else {
                                    onSwiped()
                                }
                            }
                        } else {
                            scope.launch {
                                offsetX.animateTo(0f, tween(300))
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                        }
                    }
                )
            }
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .shadow(12.dp, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Image(
                painter = rememberAsyncImagePainter(recommendation.destination.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = recommendation.destination.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = recommendation.destination.description,
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "⭐ ${recommendation.relevance}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFFFA000)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Spring",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4CAF50)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF1976D2))
                    .padding(vertical = 10.dp),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "Add to wishlist",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()) {
                IconButton(
                    onClick = { onSwiped },
                    modifier = Modifier
                        .padding(start = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIos,
                        contentDescription = "Previous",
                        tint = Color.Black,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                IconButton(
                    onClick = { onSwipedRight},
                    modifier = Modifier
                        .padding(end = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = "Next",
                        tint = Color.Black,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeableCardPreview() {
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

    SwipeableCard(
        recommendation = mockRecommendation,
        onSwiped = {},
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.75f),
        onSwipedRight = {}
    )
}
