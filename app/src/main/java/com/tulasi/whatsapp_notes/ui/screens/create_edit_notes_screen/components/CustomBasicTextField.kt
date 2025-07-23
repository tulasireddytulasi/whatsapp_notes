package com.tulasi.whatsapp_notes.ui.screens.create_edit_notes_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CustomBasicTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textStyleData: TextStyle? = null,
    placeholder: String,
    readOnly: Boolean = false,
){
    if (textStyleData != null) {
        TextField(
            readOnly = readOnly,
            value = value, // Current value of the TextField
            onValueChange = { newText -> onValueChange(newText) }, // Callback for when the text changes
            placeholder = { // Composable to display when the TextField is empty
                Text(
                    text = placeholder, // Hint text
                    style = textStyleData.copy(color = Color.Gray)
                )
            },

            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(0.dp),// Make the TextField fill the width of its parent
            maxLines = Int.MAX_VALUE, // allow unlimited lines
            singleLine = false,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent, // Remove indicator when focused
                unfocusedIndicatorColor = Color.Transparent, // Remove indicator when unfocused
                disabledIndicatorColor = Color.Transparent, // Remove indicator when disabled
                errorIndicatorColor = Color.Transparent, // Remove indicator when in error state
                focusedContainerColor = Color.Transparent, // Make background transparent when focused
                unfocusedContainerColor = Color.Transparent, // Make background transparent when unfocused
                disabledContainerColor = Color.Transparent, // Make background transparent when disabled
                errorContainerColor = Color.Transparent // Make background transparent when in error state
            ),
            textStyle = textStyleData,
        )
    }
}
