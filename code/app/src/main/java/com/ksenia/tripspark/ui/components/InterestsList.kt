package com.ksenia.tripspark.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ksenia.tripspark.ui.viewmodel.InterestViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun InterestsList(
    interests: List<InterestViewModel.SelectableInterest>,
    onInterestToggle: (InterestViewModel.SelectableInterest) -> Unit,
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp
    ) {
        interests.forEach { interest ->
            ChoiceInterestButton(
                interest = interest,
                isSelected = interest.isChosen,
                onInterestToggle = { onInterestToggle(interest) }
            )
        }
    }
}
