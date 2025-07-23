package com.tulasi.whatsapp_notes.ui.screens.home_screen.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tulasi.whatsapp_notes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionAppBar(
    selectedCount: Int,
    showPinIcon: Boolean,
    onPinnedSelected: () -> Unit,
    onEditSelection: () -> Unit,
    onClearSelection: () -> Unit,
    onDeleteSelected: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "$selectedCount selected") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        navigationIcon = {
            IconButton(onClick = onClearSelection) {
                Icon(Icons.Filled.Close, contentDescription = "Clear selection")
            }
        },
        actions = {
            val pinIcon = if (showPinIcon) R.drawable.pin_filled else R.drawable.pin_off_filled
            IconButton(onClick = onPinnedSelected) {
                Icon(
                    painter = painterResource(id = pinIcon),
                    contentDescription = "Pin selected",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }
            if (selectedCount < 2) {
                IconButton(onClick = onEditSelection) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit selected")
                }
            }
            IconButton(onClick = onDeleteSelected) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete selected")
            }
        }
    )
}