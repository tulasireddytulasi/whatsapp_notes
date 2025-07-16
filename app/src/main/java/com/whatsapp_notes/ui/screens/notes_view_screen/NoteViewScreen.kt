package com.whatsapp_notes.ui.screens.notes_view_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.whatsapp_notes.ui.screens.notes_view_screen.components.HorizontalLinkPreviewCard
import com.whatsapp_notes.ui.screens.notes_view_screen.components.MessageBubble
import com.whatsapp_notes.ui.screens.notes_view_screen.components.MessageInputTextField
import com.whatsapp_notes.ui.screens.notes_view_screen.components.NoteAppBar
import com.whatsapp_notes.ui.theme.DarkLighter
import com.whatsapp_notes.ui.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteViewScreen(
    navController: NavController,
    notesViewModel: NotesViewModel,
    noteId: String,
    noteTitle: String,
    isPinned: Boolean,
) {
    // Load threads when the screen is first composed
    notesViewModel.loadAllThreads(noteId)

    // Observe states from ViewModel
    val threads by notesViewModel.threads.collectAsState(initial = emptyList())
    val messageInput by notesViewModel.messageInput.collectAsState()
    val showLinkPreview by notesViewModel.showLinkPreview.collectAsState()
    val previewImageUrl by notesViewModel.previewImageUrl.collectAsState()
    val previewTitle by notesViewModel.previewTitle.collectAsState()
    val previewDescription by notesViewModel.previewDescription.collectAsState()

    Scaffold(
        topBar = {
            NoteAppBar(
                title = noteTitle,
                isPinned = isPinned,
                onBackClick = { navController.popBackStack() },
                onPinClick = { /* Handle pin click */ },
                onMoreOptionsClick = { /* Handle more options click */ },
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkLighter)
                    .padding(bottom = 8.dp)
            ) {
                if (showLinkPreview && previewImageUrl.isNullOrEmpty()) {
                    HorizontalLinkPreviewCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        imageUrl = previewImageUrl ?: "",
                        title = previewTitle.orEmpty(), // Provide default for nullability
                        description = previewDescription.orEmpty(), // Provide default for nullability
                        onRemove = { notesViewModel.clearLinkMetadata() },
                    )
                }

                MessageInputTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    value = messageInput,
                    onSendMessage = { notesViewModel.sendMessage(noteId) },
                    onValueChange = { notesViewModel.updateMessageInput(it) },
                    onMicButtonClick = { /* Handle mic button click */ }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            reverseLayout = true,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            items(threads) { thread ->
                MessageBubble(thread = thread, onLinkClick = {})
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
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
                        color = Color(0xFF757575),
                        textAlign = TextAlign.Center,
                        letterSpacing = 2.sp,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Written with intention, stored with care. 💛",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF616161),
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
    NoteViewScreen(
        navController = rememberNavController(),
        notesViewModel = viewModel(),
        noteId = "",
        noteTitle = "Preview Note",
        isPinned = false,
    )
}