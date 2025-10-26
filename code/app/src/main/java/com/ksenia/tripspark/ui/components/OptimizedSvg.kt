package com.ksenia.tripspark.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ksenia.tripspark.R
import com.ksenia.tripspark.domain.model.Continent
import com.ksenia.tripspark.ui.viewmodel.InterestViewModel

@Composable
fun OptimizedSvgWithMarkers(
    continents: List<InterestViewModel.SelectableContinent>,
    onToggle: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(3.dp, colorResource(R.color.primary_blue), RoundedCornerShape(19.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.world_map),
            contentDescription = "World Map",
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Fit
        )

        continents.forEach { continent ->
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = continent.name,
                tint = if (continent.isSelected) colorResource(R.color.teal_700) else Color.Gray,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopStart)
                    .absoluteOffset(
                        x = (continent.screenXPercent * LocalConfiguration.current.screenWidthDp + (-16)).dp,
                        y = (continent.screenYPercent * 250f+ (-16)).dp
                    )
                    .clickable { onToggle(continent.id)}
            )
        }
    }
}
