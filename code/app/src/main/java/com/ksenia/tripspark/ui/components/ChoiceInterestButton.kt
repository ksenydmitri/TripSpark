package com.ksenia.tripspark.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ksenia.tripspark.R
import com.ksenia.tripspark.ui.viewmodel.InterestViewModel

@Composable
fun ChoiceInterestButton(
    interest: InterestViewModel.SelectableInterest,
    isSelected: Boolean,
    onInterestToggle: (InterestViewModel.SelectableInterest) -> Unit
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