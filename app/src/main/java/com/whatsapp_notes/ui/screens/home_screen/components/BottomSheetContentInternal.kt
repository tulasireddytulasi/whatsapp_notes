package com.whatsapp_notes.ui.screens.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whatsapp_notes.R
import com.whatsapp_notes.ui.viewmodel.NotesViewModel

/**
 * Internal composable for the content of the bottom sheet.
 * This is kept private to the reusable widget.
 */
@Composable
fun BottomSheetContentInternal(
    title: String,
    modifier: Modifier = Modifier,
    onUpdate: (String) -> Unit,
    onColorPicker: () -> Unit,
    notesViewModel: NotesViewModel
) {
    var textInput by remember { mutableStateOf(title) }
    val selectedColor by notesViewModel.selectedColor.collectAsState()

    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // Distribute space
    ) {
        // Top section: TextField and Color Picker Icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = textInput,
                maxLines = Int.MAX_VALUE,
                onValueChange = { textInput = it },
                label = { Text("Enter text") },
                modifier = Modifier.weight(1f), // Takes available space
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.White, // Customize cursor color
                    focusedBorderColor = Color.Gray, // Customize border color if needed
                    unfocusedBorderColor = Color.LightGray,
                ),
                trailingIcon = {
                    IconButton(
                        onClick = onColorPicker,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.color_picker),
                            contentDescription = "Mic",
                            tint = Color(android.graphics.Color.parseColor(selectedColor)),
                        )
                    }
                },
            )
        }

        // Bottom section: Update Button
        Button(
            onClick = {
                onUpdate(textInput)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp), // Add padding above the button
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Update", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}