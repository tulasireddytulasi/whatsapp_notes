package com.whatsapp_notes.ui.screens.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whatsapp_notes.data.model.Note
import com.whatsapp_notes.data.repository.NoteRepository
import com.whatsapp_notes.ui.theme.Gray400
import com.whatsapp_notes.ui.theme.Gray500
import com.whatsapp_notes.ui.theme.NotesAppTheme

/**
 * Composable function for the Pinned Notes section on the Home Screen.
 * Displays a header with "Pinned" title and note count, followed by a
 * horizontally scrollable list of pinned note cards.
 *
 * @param pinnedNotes The list of [Note] objects to display as pinned.
 * @param onNoteClick Lambda to be invoked when a pinned note card is clicked.
 */
@Composable
fun PinnedNotesSection(
    pinnedNotes: List<Note>,
    onNoteClick: (Note) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Pinned",
                fontSize = 14.sp, // text-sm
                color = Gray400, // text-gray-400
                style = MaterialTheme.typography.labelSmall // font-medium
            )
            Text(
                text = "${pinnedNotes.size} notes",
                fontSize = 12.sp, // text-xs
                color = Gray500 // text-gray-500
            )
        }
        Spacer(modifier = Modifier.height(12.dp)) // mb-3 equivalent

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp) // gap-3 equivalent
        ) {
            items(pinnedNotes) { note ->
                PinnedNoteCard(note = note, onClick = onNoteClick)
            }
        }
    }
}

/**
 * Preview Composable for PinnedNotesSection.
 */
@Preview(showBackground = true)
@Composable
fun PinnedNotesSectionPreview() {
    NotesAppTheme {
        val allNotes: List<Note> =  NoteRepository.getFakeNotesData().notesList
        val mockPinnedNotes: List<Note> = allNotes.filter { note ->
            note.isPinned // The condition to check if a notes is pinned
        }
        PinnedNotesSection(
            pinnedNotes = mockPinnedNotes,
            onNoteClick = { /* Do nothing for preview */ }
        )
    }
}
