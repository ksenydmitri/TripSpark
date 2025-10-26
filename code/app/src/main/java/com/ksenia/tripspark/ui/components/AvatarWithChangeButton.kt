package com.ksenia.tripspark.ui.components

import android.R.attr.onClick
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ksenia.tripspark.R

@Composable
fun AvatarWithChangeButton(
    avatarUrl: String?,
    modifier: Modifier = Modifier,
    onAddAvatarClick: () -> Unit
) {
    Box(modifier = modifier) {
        AvatarImage(
            avatarUrl = avatarUrl,
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape)
        )
        IconButton(
            onClick = onAddAvatarClick,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.BottomEnd)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Добавить аватарку",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun AvatarImage(
    avatarUrl: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val startTime = remember { System.currentTimeMillis() }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(avatarUrl)
            .crossfade(true)
            .build()
    )

    val state = painter.state

    LaunchedEffect(state) {
        when (state) {
            is AsyncImagePainter.State.Loading -> {
                Log.d("AvatarLoad", "Началась загрузка аватарки: $avatarUrl")
            }
            is AsyncImagePainter.State.Success -> {
                val duration = System.currentTimeMillis() - startTime
                Log.d("AvatarLoad", "✅ Аватарка загружена: $avatarUrl, время=${duration}ms")
            }
            is AsyncImagePainter.State.Error -> {
                Log.e("AvatarLoad", "❌ Ошибка загрузки аватарки: $avatarUrl", state.result.throwable)
            }
            else -> {}
        }
    }

    Box(modifier = modifier) {
        if (state is AsyncImagePainter.State.Loading || state is
                    AsyncImagePainter.State.Error ||
            avatarUrl.isNullOrBlank()) {
            Image(
                painter = painterResource(id = R.drawable.avatar_base),
                contentDescription = "Базовая аватарка",
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        if (state is AsyncImagePainter.State.Success) {
            Image(
                painter = painter,
                contentDescription = "Аватар",
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}
