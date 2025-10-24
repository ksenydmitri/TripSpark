package com.ksenia.tripspark.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ksenia.tripspark.ui.viewmodel.RecommendationViewModel

@Composable
fun RecommendationScreen(navController: NavController,viewModel: RecommendationViewModel = hiltViewModel()) {
    val cards by viewModel.recommendations.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        cards.reversed().forEachIndexed { index, card ->
            /*SwipeableCard(
                recommendation = card,
                onSwiped = { viewModel.removeTopCard() },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(0.75f)
                    .offset(y = (index * 8).dp)
            )*/
        }
    }
}
