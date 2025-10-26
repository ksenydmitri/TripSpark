package com.ksenia.tripspark.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.ksenia.tripspark.R

@Composable
fun AddNoteDialog(
    initialText: String = "",
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var noteText by remember { mutableStateOf(initialText) }

    val primaryBlue = colorResource(id = R.color.primary_blue)
    val accentLavender = colorResource(id = R.color.accent_lavender)
    val neutralWhite = colorResource(id = R.color.neutral_white)
    val neutralBlack = colorResource(id = R.color.neutral_black)
    val neutralGrayMedium = colorResource(id = R.color.nature_green)
    val neutralGrayDark = colorResource(id = R.color.nature_mountain)

    AlertDialog(
        containerColor = neutralWhite,
        titleContentColor = neutralGrayDark,
        textContentColor = neutralBlack,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(noteText) }) {
                Text(
                    text = "Сохранить",
                    color = primaryBlue
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Отмена",
                    color = accentLavender
                )
            }
        },
        title = {
            Text(
                text = "Заметка к направлению",
                color = neutralGrayDark
            )
        },
        text = {
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                label = {
                    Text(
                        text = "Введите заметку",
                        color = neutralGrayMedium
                    )
                },
                textStyle = androidx.compose.ui.text.TextStyle(color = neutralBlack),
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}
