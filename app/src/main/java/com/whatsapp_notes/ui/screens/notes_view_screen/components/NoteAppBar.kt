package com.whatsapp_notes.ui.screens.notes_view_screen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
// import com.example.notesapp.R // Assuming you will have a drawable resource for the unpinned icon

/**
 * A custom Top App Bar for the Note Thread screen.
 *
 * This composable creates a center-aligned top app bar with navigation, title, and action icons.
 * It is designed to be reusable and customizable through its parameters.
 *
 * @param title The title to be displayed in the center of the app bar.
 * @param isPinned A boolean state to determine whether the note thread is pinned. Controls the tint of the pin icon.
 * @param onBackClick A lambda function to be invoked when the back arrow icon is clicked.
 * @param onPinClick A lambda function to be invoked when the pin icon is clicked.
 * @param onMoreOptionsClick A lambda function to be invoked when the more options icon is clicked.
 * @param modifier A [Modifier] to be applied to the app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAppBar(
    title: String,
    isPinned: Boolean,
    onBackClick: () -> Unit,
    onPinClick: () -> Unit,
    onMoreOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        // Setting custom colors for the app bar to match the dark theme from the HTML
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF1E1E1E), // bg-[#1E1E1E]
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.Gray // Default color for action icons
        ),
        // The title of the screen
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        // The navigation icon (back arrow) on the left
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        // Action icons on the right side of the app bar
        actions = {
            // Pin Icon
            IconButton(onClick = onPinClick) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Pin Note",
                    // Change tint based on the `isPinned` state
                    tint = if (isPinned) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
            // More Options Icon
            IconButton(onClick = onMoreOptionsClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Options"
                )
            }
        }
    )
}

/**
 * Preview function for the NoteAppBar composable.
 * This allows us to see the component in Android Studio's design view.
 */
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun NoteAppBarPreview() {
    MaterialTheme {
        NoteAppBar(
            title = "Note Thread",
            isPinned = false,
            onBackClick = { /* Preview doesn't handle clicks */ },
            onPinClick = { /* Preview doesn't handle clicks */ },
            onMoreOptionsClick = { /* Preview doesn't handle clicks */ }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun NoteAppBarPinnedPreview() {
    MaterialTheme {
        NoteAppBar(
            title = "Note Thread",
            isPinned = true,
            onBackClick = { /* Preview doesn't handle clicks */ },
            onPinClick = { /* Preview doesn't handle clicks */ },
            onMoreOptionsClick = { /* Preview doesn't handle clicks */ }
        )
    }
}
