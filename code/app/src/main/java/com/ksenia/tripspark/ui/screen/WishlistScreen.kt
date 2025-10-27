package com.ksenia.tripspark.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ksenia.tripspark.R
import com.ksenia.tripspark.domain.model.Note
import com.ksenia.tripspark.domain.model.WishlistItem
import com.ksenia.tripspark.ui.components.AddNoteDialog
import com.ksenia.tripspark.ui.components.AvatarImage
import com.ksenia.tripspark.ui.components.WishlistComponentCard
import com.ksenia.tripspark.ui.viewmodel.WishlistViewModel

@Composable
fun WishlistScreen(navController: NavController,
                   viewModel: WishlistViewModel = hiltViewModel()) {
    val wishlist by viewModel.wishlist.collectAsState()
    val user by viewModel.currentUser.collectAsState()

    if (user == null) {
        androidx.compose.material3.Text("Загрузка пользователя...")
        return
    }

    val userId = user!!.id

    viewModel.currentUser.collectAsState().value?.imageId
    var showDialogForId by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        )
    }

    Column{
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    end = 16.dp,
                    top = 16.dp
                )) {
            Button(
                onClick = {
                    navController.navigate("recommendations")
                },
                colors = ButtonColors(
                    contentColor = colorResource(R.color.white),
                    containerColor = Color.Transparent,
                    disabledContentColor = colorResource(R.color.primary_blue),
                    disabledContainerColor = Color.Transparent
                ),
            ) {
                Text(text = "←",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold)
            }
            AvatarImage(
                avatarUrl = user?.imageId,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(3.dp, colorResource(R.color.primary_blue), CircleShape)
                    .clickable { navController.navigate("profile") }
            )}
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(wishlist) { item ->
                WishlistComponentCard(
                    item = item,
                    onEditClick = { showDialogForId = item.destinationId },
                    onDeleteClick = { viewModel.remove(userId, item.destinationId) }
                )
            }
        }
    }

    showDialogForId?.let { destinationId ->
        val currentNote = wishlist.find { it.destinationId == destinationId }?.note ?: ""
        AddNoteDialog(
            initialText = currentNote,
            onDismiss = { showDialogForId = null },
            onConfirm = { newNoteText ->
                val note = Note(
                    id = destinationId,
                    text = newNoteText,
                    status = "active",
                    userId = userId,
                    destinationId = destinationId
                )
                viewModel.updateNote(userId, destinationId, note)
                showDialogForId = null
            }
        )
    }

}