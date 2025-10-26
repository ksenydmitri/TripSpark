package com.ksenia.tripspark.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ksenia.tripspark.ui.viewmodel.InterestViewModel

@Composable
fun InterestsList(
    interests: List<InterestViewModel.SelectableInterest>,
    onInterestToggle: (InterestViewModel.SelectableInterest) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .fillMaxWidth()
    ) {
        LazyColumn {
            items(count = interests.size) { index ->
                val interest = interests[index]
                ChoiceInterestButton(
                    interest = interest,
                    isSelected = interest.isChosen,
                    onInterestToggle = { onInterestToggle(interest) }
                )
            }
        }
    }
}
