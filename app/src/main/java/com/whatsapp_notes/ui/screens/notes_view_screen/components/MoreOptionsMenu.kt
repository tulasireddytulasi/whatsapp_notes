package com.whatsapp_notes.ui.screens.notes_view_screen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A dropdown menu that displays more options for the note thread.
 *
 * This composable is meant to be anchored to the 'More Options' icon button in the app bar.
 * It provides a list of actions a user can take on the current note thread.
 *
 * @param expanded Whether the menu is currently visible.
 * @param onDismissRequest A lambda to be invoked when the user requests to dismiss the menu (e.g., by tapping outside).
 * @param onSearchClick A lambda to be invoked when the 'Search notes' item is clicked.
 * @param onExportClick A lambda to be invoked when the 'Export thread' item is clicked.
 * @param onSettingsClick A lambda to be invoked when the 'Settings' item is clicked.
 * @param onChangeThemeClick A lambda to be invoked when the 'Change Theme' item is clicked.
 * @param onArchiveClick A lambda to be invoked when the 'Archive thread' item is clicked.
 * @param onClearAllClick A lambda to be invoked when the 'Clear all notes' item is clicked.
 * @param modifier A [Modifier] to be applied to the dropdown menu.
 */
@Composable
fun MoreOptionsMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onSearchClick: () -> Unit,
    onExportClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onChangeThemeClick: () -> Unit,
    onArchiveClick: () -> Unit,
    onClearAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        // A helper composable for creating menu items to reduce repetition.
        @Composable
        fun MenuItem(
            text: String,
            icon: ImageVector,
            onClick: () -> Unit,
            contentColor: Color = MaterialTheme.colorScheme.onSurface
        ) {
            DropdownMenuItem(
                text = { Text(text, color = contentColor) },
                onClick = {
                    onClick()
                    onDismissRequest() // Dismiss the menu after a click
                },
                leadingIcon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = text,
                        tint = contentColor
                    )
                },
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        MenuItem(text = "Search notes", icon = Icons.Outlined.Search, onClick = onSearchClick)
        MenuItem(text = "Export thread", icon = Icons.Outlined.CheckCircle, onClick = onExportClick)
        MenuItem(text = "Settings", icon = Icons.Outlined.Settings, onClick = onSettingsClick)
        MenuItem(text = "Change Theme", icon = Icons.Outlined.Build, onClick = onChangeThemeClick)
        MenuItem(text = "Archive thread", icon = Icons.Outlined.AccountBox, onClick = onArchiveClick)
        MenuItem(
            text = "Clear all notes",
            icon = Icons.Outlined.Delete,
            onClick = onClearAllClick,
            contentColor = Color.Red // Special color for the destructive action
        )
    }
}

/**
 * Preview function for the MoreOptionsMenu composable.
 */
@Preview(showBackground = true)
@Composable
fun MoreOptionsMenuPreview() {
    MaterialTheme {
        MoreOptionsMenu(
            expanded = true, // Set to true to make it visible in the preview
            onDismissRequest = {},
            onSearchClick = {},
            onExportClick = {},
            onSettingsClick = {},
            onChangeThemeClick = {},
            onArchiveClick = {},
            onClearAllClick = {}
        )
    }
}
