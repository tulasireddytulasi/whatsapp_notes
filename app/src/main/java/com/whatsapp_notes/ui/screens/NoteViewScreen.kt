package com.whatsapp_notes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whatsapp_notes.ui.theme.DarkDefault
import com.whatsapp_notes.ui.theme.NotesAppTheme

/**
 * Composable function for the Note View Screen.
 * This is a placeholder for now, which will eventually display the full details of a note.
 *
 * @param noteId The ID of the note to display.
 */
@Composable
fun NoteViewScreen(noteId: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkDefault)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Note View Screen",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Note ID: $noteId",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

/**
 * Preview Composable for NoteViewScreen.
 */
@Preview(showBackground = true)
@Composable
fun NoteViewScreenPreview() {
    NotesAppTheme {
        NoteViewScreen(noteId = "4")
    }
}
