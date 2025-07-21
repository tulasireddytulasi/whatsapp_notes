package com.whatsapp_notes.ui.screens.create_edit_notes_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.whatsapp_notes.Routes
import com.whatsapp_notes.ui.screens.create_edit_notes_screen.components.ColorPickerDialog
import com.whatsapp_notes.ui.screens.create_edit_notes_screen.components.CustomBasicTextField
import com.whatsapp_notes.ui.screens.create_edit_notes_screen.components.LinkPreviewCard
import com.whatsapp_notes.ui.screens.create_edit_notes_screen.components.NoteTopAppBar
import com.whatsapp_notes.ui.screens.create_edit_notes_screen.components.SingleSelectChips
import com.whatsapp_notes.ui.theme.DarkDarker
import com.whatsapp_notes.ui.theme.DarkLighter
import com.whatsapp_notes.ui.theme.NotesAppTheme
import com.whatsapp_notes.ui.viewmodel.NotesViewModel

@Composable
fun CreateEditNoteScreen(
    navController: NavController,
    notesViewModel: NotesViewModel,
    threadIdToEdit: String? = null, // Null for creation, provided for editing
    noteId: String? = null
) {
    val context = LocalContext.current

    // State to control the visibility of the color picker dialog
    var showColorPickerDialog by remember { mutableStateOf(false) }

    // Collect states from ViewModel
    val noteTitle by notesViewModel.noteTitle.collectAsState()
    val noteDescription by notesViewModel.noteDescription.collectAsState()
    val selectedCategory by notesViewModel.selectedCategory.collectAsState()
    val selectedColor by notesViewModel.selectedColor.collectAsState()
    val showLinkPreview by notesViewModel.showLinkPreview.collectAsState()
    val previewImageUrl by notesViewModel.previewImageUrl.collectAsState()
    val previewTitle by notesViewModel.previewTitle.collectAsState()
    val previewDescription by notesViewModel.previewDescription.collectAsState()
    val isLoadingLinkMetadata by notesViewModel.isLoadingLinkMetadata.observeAsState(false)


    val categories = listOf("Work", "Personal", "Ideas", "Others")

    // Determine if we are in edit mode
    val isEditMode = (threadIdToEdit != "{threadId}") && (noteId != "{noteId}")
    if (threadIdToEdit != null) {
        println("isEditttt: $isEditMode, $threadIdToEdit, $noteId")
    }

    LaunchedEffect(threadIdToEdit) {
        if (isEditMode) {
            // For editing, we might want to load the note's title and category as well,
            // assuming a thread is always part of a note.
            // If CreateEditNoteScreen is solely for creating/editing *threads* related to a note,
            // then only thread content is relevant here.
            // If it's for editing the primary thread of a note, then load note details.
            notesViewModel.loadNoteDetails(noteId ?: "", threadIdToEdit ?: "")
        } else {
            // Clear the ViewModel states when creating a new note
            notesViewModel.resetNoteCreationState()
        }
    }


    Scaffold(
        topBar = {
            val selectedColorData = Color(android.graphics.Color.parseColor(selectedColor))
            NoteTopAppBar(
                // The CreateEditNoteViewModel is not used here anymore, can be removed if not needed elsewhere
                // If it's for top app bar specific actions, it can remain.
                viewModel = viewModel(), // Consider passing NotesViewModel if actions relate to it
                onBackClick = { navController.popBackStack() },
                selectedColor = selectedColorData,
                onColorPick = { showColorPickerDialog = !showColorPickerDialog },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    notesViewModel.saveNote(
                        onSuccess = { newNote ->
                            navController.navigate(
                                "${Routes.NOTE_FIRST_ARG}/${newNote.noteId}/${newNote.title}/${newNote.isPinned}"
                            ) {
                                popUpTo(Routes.HOME_SCREEN) { inclusive = false }
                            }
                        },
                        onError = { errorMessage ->
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        },
                        existingThreadId = if (threadIdToEdit != "{threadId}") threadIdToEdit else
                            null,
                    )
                },
                containerColor = Color(0xFF2979FF), // Primary blue from HTML
                contentColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .border(1.dp, DarkDarker, RoundedCornerShape(2.dp))
                    .height(56.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        if (isEditMode) "Update Note" else "Save Note",
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(DarkLighter)
                .verticalScroll(rememberScrollState()) // Make content scrollable
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            ColorPickerDialog(
                showDialog = showColorPickerDialog,
                onDismissRequest = { showColorPickerDialog = false },
                onColorSelected = { color ->
                    notesViewModel.updateSelectedColor(color)
                   // selectedColor = color
                    showColorPickerDialog = false // Dismiss dialog after color is selected
                },
                initialColor = selectedColor, // Pass the current selected color
                // as initial
            )
            // Category Section
            Text(
                text = "Category",
                color = Color.Gray,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            SingleSelectChips(
                options = categories, showLeadingIcon = false,
                selectedOption = selectedCategory,
            ) { selectedOption ->
                // Handle the selected option here
                println("Selected difficulty: $selectedOption")
                notesViewModel.updateSelectedCategory(selectedOption)
            }

            Spacer(modifier = Modifier.height(12.dp))

            CustomBasicTextField(
                value = noteTitle,
                onValueChange = {
                    if (!isEditMode) { // Title only editable in create mode
                        notesViewModel.updateNoteTitle(it)
                    }
                },
                textStyleData = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                ),
                placeholder = "Title...",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 60.dp),
                readOnly = isEditMode // Make read-only if in edit mode
            )

            Spacer(modifier = Modifier.height(0.dp))

            // Link Preview Section (conditionally visible)
            if (showLinkPreview && !isLoadingLinkMetadata) {
                LinkPreviewCard(
                    imageUrl = previewImageUrl ?: "",
                    title = previewTitle ?: "",
                    description = previewDescription ?: "",
                    domain = notesViewModel.linkMetadata.value?.url?.let {
                        android.net.Uri.parse(it).host
                    } ?: "", // Extract domain from URL
                    onRemove = { notesViewModel.clearLinkMetadata() }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Note Description Input (Thread Content)
            CustomBasicTextField(
                value = noteDescription,
                onValueChange = { notesViewModel.updateNoteDescription(it) },
                textStyleData = TextStyle(
                    color = Color.White, fontSize = 16.sp, lineHeight = 24.sp
                ),
                placeholder = "Type your thoughts...",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp), // Minimum height for textarea
            )
            Spacer(modifier = Modifier.height(100.dp)) // Padding at the bottom for FAB
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateEditNoteScreen() {
    NotesAppTheme {
        CreateEditNoteScreen(navController = rememberNavController(), notesViewModel = viewModel())
    }
}