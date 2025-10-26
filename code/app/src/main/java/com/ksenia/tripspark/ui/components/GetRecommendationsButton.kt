package com.ksenia.tripspark.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ksenia.tripspark.R

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