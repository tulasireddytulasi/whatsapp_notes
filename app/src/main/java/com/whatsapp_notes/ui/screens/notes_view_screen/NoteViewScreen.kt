package com.whatsapp_notes.ui.screens.notes_view_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whatsapp_notes.data.model.Message
import com.whatsapp_notes.data.repository.NoteViewRepository
import com.whatsapp_notes.ui.screens.notes_view_screen.components.MessageBubble
import com.whatsapp_notes.ui.screens.notes_view_screen.components.NoteAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteViewScreen(
//    messages: List<Message>,
//    modifier: Modifier = Modifier,
//    onLinkClick: (String) -> Unit = {}
) {
    val messages: List<Message> = remember { NoteViewRepository.getMessage() }

    Scaffold(
        topBar = {
            NoteAppBar(
                title = "Notes Viewer", isPinned = true,
                onBackClick = {
                    println("Search query changed to:")
                },
                onPinClick = {
                    println("Search query changed to:")
                },
                onMoreOptionsClick = {
                    println("Search query changed to:")
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Padding to account for header and input
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp) // Internal padding
        ) {
            items(messages) { message ->
                MessageBubble(message = message, onLinkClick = {})
            }

            item {
                Spacer(modifier = Modifier.height(32.dp)) // Space before end of notes
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    thickness = 1.dp,
                    color = Color(0xFF2E2E2E)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "THAT'S ALL FOR NOW",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF757575), // Equivalent to gray-500
                        textAlign = TextAlign.Center,
                        letterSpacing = 2.sp,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Written with intention, stored with care. 💛",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF616161), // Equivalent to gray-600
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteViewScreenPreview() {
    NoteViewScreen()
}