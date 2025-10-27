package com.ksenia.tripspark.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ksenia.tripspark.R

@Composable
fun ErrorWindow(
    modifier: Modifier,
    eMessage: String
) {
    Box(modifier = modifier
        .background(color = colorResource(R.color.accent_sunset),
            shape = RoundedCornerShape(16.dp))
        .border(
            shape = RoundedCornerShape(16.dp),
            width = 3.dp,
            color = colorResource(R.color.error_red)
        ),
        contentAlignment = Alignment.Center){
        Text(eMessage,
            Modifier.padding(16.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.error_red))
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorWindowPreview(){
    val eMassege = "some message eiwgjps;lnew lkefmwelsnfwl lsknflsfnkslefn lknsfflgnsglnk lsgn"
    ErrorWindow(modifier = Modifier
        .width(300.dp)
        .height(100.dp),
        eMassege)
}