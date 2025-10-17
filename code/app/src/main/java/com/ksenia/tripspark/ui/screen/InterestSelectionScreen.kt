package com.ksenia.tripspark.ui.screen

import android.util.Log.i
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
fun InterestSelectionScreen(
) {
    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
        ) {
            OptimizedSvg()
            GetRecommendationsButton()

        }
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
fun ChoiceInterestButton(interest: Interest) {
    var isSelected by remember { mutableStateOf(false) }

    Button(
        onClick = {
            isSelected = !isSelected
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

@Preview
@Composable
fun GetRecommendationsButton(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp),
    ) { Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(70.dp)
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
fun InterestsList(){

}