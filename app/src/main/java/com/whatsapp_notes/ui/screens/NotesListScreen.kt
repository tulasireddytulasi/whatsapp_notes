package com.whatsapp_notes.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whatsapp_notes.data.local.entities.NoteEntity
import com.whatsapp_notes.ui.theme.NotesAppTheme
import com.whatsapp_notes.ui.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(viewModel: NotesViewModel) {
    // Change the collection type
    val notesWithLastThread by viewModel.notesWithThreads.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Notes") })
        }
    ) { paddingValues ->
        if (notesWithLastThread.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No notes found. Add some!")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notesWithLastThread) { noteWithLastThread -> // Iterate directly on the list
                    // Pass the embedded note and lastThreadContent
                    NoteCard(
                        note = noteWithLastThread.note,
                        lastThreadContent = noteWithLastThread.threads.first().content,
                    )
                }
            }
        }
    }
}

// Keep NoteCard as is, as it receives NoteEntity and String?
@Composable
fun NoteCard(note: NoteEntity, lastThreadContent: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Note ID: ${note.noteId}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Title: ${note.title}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Category: ${note.category}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Pinned: ${note.isPinned}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Last Thread Content:",
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = lastThreadContent ?: "No threads yet.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotesAppTheme {
        Column {
            NoteCard(
                note = NoteEntity(
                    noteId = "previewNote1",
                    title = "gggggggggggggggggggggg",
                    category = "Work",
                    timestamp = "2025-07-15T10:00:00Z",
                    isPinned = true,
                    colorStripHex = "#FF5733"
                ),
                lastThreadContent = "This is a preview of the last thread content."
            )
            Spacer(modifier = Modifier.height(8.dp))
            NoteCard(
                note = NoteEntity(
                    noteId = "previewNote2",
                    title = "ddddddddddddd",
                    category = "Personal",
                    timestamp = "2025-07-14T15:30:00Z",
                    isPinned = false,
                    colorStripHex = "#33A1C9"
                ),
                lastThreadContent = null // Example with no threads
            )
        }
    }
}