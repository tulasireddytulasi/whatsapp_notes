package com.tulasi.whatsapp_notes.ui.screens.home_screen.components // Updated package name

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tulasi.whatsapp_notes.ui.theme.NotesAppTheme

/**
 * Composable function for the top app bar of the Notes Home Screen.
 * Displays the app title and a user profile icon.
 *
 * @param onProfileClick Lambda to be invoked when the user profile icon is clicked.
 */
@Composable
fun HomeTopBar(
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Fixed height similar to HTML header
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Distributes space between title and icon
    ) {
        // App Title
        Text(
            text = "WhatsAppNotes",
            fontSize = 20.sp, // Similar to h1 in HTML
            fontWeight = FontWeight.Bold,
            color = Color.White // Text color from HTML
        )

        // User Profile Icon
        Box(
            modifier = Modifier
                .size(32.dp) // Fixed size for icon container
                .clickable(onClick = onProfileClick), // Make the icon clickable
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Settings, // Using a default Material Design icon for now
                contentDescription = "User Profile",
                tint = Color(0xFFD1D5DB) // Equivalent to gray-300 in Tailwind
            )
        }
    }
}

/**
 * Preview Composable for HomeTopBar.
 * Shows how the top bar will look in isolation.
 */
@Preview(showBackground = true)
@Composable
fun HomeTopBarPreview() {
    NotesAppTheme { // Use your app's theme for accurate preview
        HomeTopBar(onProfileClick = { /* Do nothing for preview */ })
    }
}
