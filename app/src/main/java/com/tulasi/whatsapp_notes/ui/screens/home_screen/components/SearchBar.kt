package com.tulasi.whatsapp_notes.ui.screens.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tulasi.whatsapp_notes.ui.theme.DarkDefault
import com.tulasi.whatsapp_notes.ui.theme.Gray400
import com.tulasi.whatsapp_notes.ui.theme.NotesAppTheme
import com.tulasi.whatsapp_notes.ui.theme.Primary

/**
 * Composable function for a reusable Search Bar.
 *
 * @param modifier Modifier for the search bar.
 * @param onSearchQueryChange Lambda to be invoked when the search query changes.
 * @param currentQuery The current search query string to display.
 */
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchQueryChange: (String) -> Unit,
    currentQuery: String
) {
    TextField(
        value = currentQuery,
        onValueChange = { newValue ->
            onSearchQueryChange(newValue)
        },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp) // Fixed height similar to HTML input
            .background(DarkDefault, RoundedCornerShape(8.dp)), // Background color and rounded corners
        placeholder = {
            Text(
                text = "Search notes...",
                color = Gray400 // Placeholder text color
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Gray400 // Search icon color
            )
        },
        trailingIcon = {
            // Show clear icon only if there's text in the search field
            if (currentQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChange("") }) { // Clear the text
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear Search",
                        tint = Gray400 // Clear icon color
                    )
                }
            }
        },
        singleLine = true, // Ensure it's a single line input
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = DarkDefault, // Background when focused
            unfocusedContainerColor = DarkDefault, // Background when unfocused
            disabledContainerColor = DarkDefault,
            focusedTextColor = Color.White, // Text color when focused
            unfocusedTextColor = Color.White, // Text color when unfocused
            cursorColor = Primary, // Cursor color
            focusedIndicatorColor = Primary, // Bottom line color when focused (acts as focus ring)
            unfocusedIndicatorColor = Color.Transparent, // No bottom line when unfocused
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp) // Apply rounded corners to the TextField itself
    )
}

/**
 * Preview Composable for the SearchBar.
 * Shows how the search bar will look in isolation.
 */
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    NotesAppTheme {
        var searchQuery by remember { mutableStateOf("") } // State for preview
        SearchBar(
            modifier = Modifier.padding(16.dp),
            onSearchQueryChange = { searchQuery = it },
            currentQuery = searchQuery
        )
    }
}
