package com.ksenia.tripspark.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
    Box(modifier = modifier.size(120.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(Color.LightGray)
                .border(width = 2.dp, color = Color.White, shape = CircleShape)
        ) {
            AvatarImage(
                avatarUrl = avatarUrl,
                modifier = Modifier.fillMaxSize()
            )
        }
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

    if (avatarUrl.isNullOrBlank()) {
        Log.d("AvatarLoad", "👤 Показываем базовую аватарку (нет URL)")
        Image(
            painter = painterResource(id = R.drawable.avatar_base),
            contentDescription = "Базовая аватарка",
            modifier = modifier
        )
        return
    }

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

    Image(
        painter = painter,
        contentDescription = "Аватар",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}
