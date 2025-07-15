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
import androidx.compose.runtime.getValue
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
import com.whatsapp_notes.data.local.entities.NoteEntity
import com.whatsapp_notes.data.local.entities.ThreadEntity
import com.whatsapp_notes.ui.screens.create_edit_notes_screen.components.CustomBasicTextField
import com.whatsapp_notes.ui.screens.create_edit_notes_screen.components.LinkPreviewCard
import com.whatsapp_notes.ui.screens.create_edit_notes_screen.components.NoteTopAppBar
import com.whatsapp_notes.ui.screens.create_edit_notes_screen.components.SingleSelectChips
import com.whatsapp_notes.ui.theme.DarkDarker
import com.whatsapp_notes.ui.theme.DarkLighter
import com.whatsapp_notes.ui.theme.NotesAppTheme
import com.whatsapp_notes.ui.viewmodel.CreateEditNoteViewModel
import com.whatsapp_notes.ui.viewmodel.NotesViewModel
import java.time.Instant

@Composable
fun CreateEditNoteScreen(
    navController: NavController,
    viewModel: CreateEditNoteViewModel = viewModel(), // ViewModel instance,
    notesViewModel: NotesViewModel,
) {
    // These states will eventually come from a ViewModel
    var noteTitle by remember { mutableStateOf("") }
    var noteDescription by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    val categories = listOf("Work", "Personal", "Ideas", "Travel")

    // Placeholder for link preview visibility and data
    val showLinkPreview by remember { mutableStateOf(true) } // Will be dynamic based on URL detection
    val previewImageUrl by remember { mutableStateOf("https://readdy.ai/api/search-image?query=modern%20website%20screenshot%20with%20clean%20design&width=800&height=400&seq=1&orientation=landscape") }
    val previewTitle by remember { mutableStateOf("Example Website Title") }
    val previewDescription by remember { mutableStateOf("This is a mock description for demonstration purposes. In a real implementation, this would be fetched from the actual URL metadata.") }
    val previewDomain by remember { mutableStateOf("example.com") }

    Scaffold(
        topBar = {
            NoteTopAppBar(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
            )
        },
        floatingActionButton = {
            val context = LocalContext.current
            FloatingActionButton(
                onClick = {
                    // If ALL three are null or empty, then we don't proceed.
                    if (noteTitle.isEmpty() && noteDescription.isEmpty() && selectedCategory.isEmpty()) {
                        println("Error: Title, description, and category cannot all be empty or null.")
                        // You should typically show a Toast message or SnackBar to the user here
                        // rather than just a println, as println messages are not visible to the user.
                        // For example:
                         Toast.makeText(context, "Please fill title and description fields", Toast
                             .LENGTH_SHORT).show()
                        return@FloatingActionButton // Stop execution of the lambda if validation fails
                    }
                  //  notesViewModel.addSampleNote()
                    val newNote = NoteEntity(
                        noteId = "note_${System.currentTimeMillis()}",
                        title = noteTitle,
                        category = selectedCategory,
                        timestamp = Instant.now().toString(),
                        isPinned = false,
                        colorStripHex = "#FF00FF"
                    )
                    val newThread = ThreadEntity(
                        threadId = "thread_${System.currentTimeMillis()}",
                        noteOwnerId = newNote.noteId,
                        content = noteDescription,
                        timestamp = Instant.now().toString(),
                        imageUrl = null, linkTitle= null, description = null
                    )
                    notesViewModel.addNotes(newNote, newThread)
                },
                containerColor = Color(0xFF2979FF), // Primary blue from HTML
                contentColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .border(1.dp, DarkDarker, RoundedCornerShape(2.dp))
                    .height(56.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Save Note", style = TextStyle(fontSize = 18.sp))
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
            // Category Section
            Text(
                text = "Category",
                color = Color.Gray,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            SingleSelectChips(
                options = categories,
                showLeadingIcon = false
            ) { selectedOption ->
                // Handle the selected option here
                println("Selected difficulty: $selectedOption")
                selectedCategory = selectedOption
            }

            Spacer(modifier = Modifier.height(12.dp))

            CustomBasicTextField(
                value = noteTitle,
                onValueChange = { noteTitle = it },
                textStyleData = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                ),
                placeholder = "Title...",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 60.dp),
            )

            Spacer(modifier = Modifier.height(0.dp))

            // Link Preview Section (conditionally visible)
            if (!showLinkPreview) {
                LinkPreviewCard(
                    imageUrl = previewImageUrl,
                    title = previewTitle,
                    description = previewDescription,
                    domain = previewDomain,
                    onRemove = { /* Handle remove preview */ }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Note Description Input
            CustomBasicTextField(
                value = noteDescription,
                onValueChange = { noteDescription = it },
                textStyleData = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
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