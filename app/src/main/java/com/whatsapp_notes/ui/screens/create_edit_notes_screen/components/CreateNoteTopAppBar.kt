package com.whatsapp_notes.ui.screens.create_edit_notes_screen.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.whatsapp_notes.ui.theme.DarkLighter
import com.whatsapp_notes.ui.theme.NotesAppTheme
import com.whatsapp_notes.ui.viewmodel.CreateEditNoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopAppBar(
    viewModel: CreateEditNoteViewModel, // Pass the ViewModel to access state and events
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = viewModel.pageTitle, // Dynamic title from ViewModel
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick ) { // Handle back click
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.LightGray
                )
            }
        },
        actions = {
            IconButton(onClick = { viewModel.onColorPickerClick() }) { // Handle color picker click
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Choose Color",
                    tint = Color.LightGray
                )
            }
            // Delete button is only visible in edit mode
            if (viewModel.isEditMode) {
                IconButton(onClick = { viewModel.onDeleteClick() }) { // Handle delete click
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkLighter // dark-lighter from tailwind config
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewNoteTopAppBarCreateMode() {
    NotesAppTheme {
        val viewModel = viewModel<CreateEditNoteViewModel>()
        viewModel.setEditModeVal(false) // Ensure it's in create mode for this preview
        NoteTopAppBar(viewModel = viewModel, onBackClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNoteTopAppBarEditMode() {
    NotesAppTheme {
        val viewModel = viewModel<CreateEditNoteViewModel>()
        viewModel.setEditModeVal(true) // Ensure it's in edit mode for this preview
        NoteTopAppBar(viewModel = viewModel, onBackClick = {})
    }
}