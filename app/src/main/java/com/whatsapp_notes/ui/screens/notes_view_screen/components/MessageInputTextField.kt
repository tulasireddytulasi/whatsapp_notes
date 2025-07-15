package com.whatsapp_notes.ui.screens.notes_view_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whatsapp_notes.ui.theme.NotesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onSendMessage: () -> Unit,
    onValueChange: (String) -> Unit,
    onMicButtonClick: () -> Unit
) {
   // var text by remember { mutableStateOf("") }
    val isSendButtonEnabled = value.isNotBlank()

    OutlinedTextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Type a message...") },
        shape = RoundedCornerShape(8.dp), // Rounded corners
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Black, // Customize cursor color
            focusedBorderColor = Color.Gray, // Customize border color if needed
            unfocusedBorderColor = Color.LightGray,
        ),
        trailingIcon = {
            Row(
                modifier = Modifier.padding(end = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
            ) {
                // Mic Button
                IconButton(
                    onClick = onMicButtonClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Mic"
                    )
                }

                // Spacer between buttons (optional)
                Spacer(modifier = Modifier.width(4.dp))

                // Send Button
                IconButton(
                    onClick = onSendMessage,
                    enabled = isSendButtonEnabled // Disable when text is empty
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send"
                    )
                }
            }
        },
        singleLine = false, // Typically for message input fields
        maxLines = 30,
    )
}

@Preview(showBackground = true)
@Composable
fun MessageInputTextFieldPreview() {
    NotesAppTheme { // Use your app's theme for accurate preview
        MessageInputTextField(
            onSendMessage = {
                println("Message sent: ")
            },
            onMicButtonClick = {
                println("Mic button clicked")
            },
            modifier = Modifier.padding(16.dp),
            onValueChange = {},
            value = "",
        )
    }
}