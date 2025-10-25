package com.ksenia.tripspark.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ksenia.tripspark.domain.model.Note
import com.ksenia.tripspark.domain.model.WishlistItem
import com.ksenia.tripspark.ui.components.AddNoteDialog
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

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(wishlist) { item ->
            WishlistComponentCard(
                item = item,
                onEditClick = { showDialogForId = item.destinationId },
                onDeleteClick = { viewModel.remove(userId, item.destinationId) }
            )
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


@Preview(showBackground = true)
@Composable
fun WishlistScreenPreview() {
    val mockItems = listOf(
        WishlistItem(
            destinationId = "1",
            name = "Tokyo",
            note = "Visit during cherry blossom season",
            addedAt = System.currentTimeMillis()
        ),
        WishlistItem(
            destinationId = "2",
            name = "Paris",
            note = "Climb the Eiffel Tower at sunset",
            addedAt = System.currentTimeMillis()
        ),
        WishlistItem(
            destinationId = "3",
            name = "Reykjavik",
            note = "See the northern lights",
            addedAt = System.currentTimeMillis()
        ),
        WishlistItem(
            destinationId = "4",
            name = "Cape Town",
            note = "Hike Table Mountain",
            addedAt = System.currentTimeMillis()
        ),
        WishlistItem(
            destinationId = "5",
            name = "Kyiv",
            note = "Explore Podil and try local food",
            addedAt = System.currentTimeMillis()
        )
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(mockItems) { item ->
            WishlistComponentCard(
                item = item,
                onEditClick = {},
                onDeleteClick = {}
            )
        }
    }
}