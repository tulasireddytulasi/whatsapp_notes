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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.whatsapp_notes.data.local.entities.ThreadEntity
import com.whatsapp_notes.ui.screens.notes_view_screen.components.HorizontalLinkPreviewCard
import com.whatsapp_notes.ui.screens.notes_view_screen.components.MessageBubble
import com.whatsapp_notes.ui.screens.notes_view_screen.components.MessageInputTextField
import com.whatsapp_notes.ui.screens.notes_view_screen.components.NoteAppBar
import com.whatsapp_notes.ui.theme.DarkLighter
import com.whatsapp_notes.ui.viewmodel.NotesViewModel
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteViewScreen(
    navController: NavController,
    notesViewModel: NotesViewModel,
    noteId: String,
//    messages: List<Message>,
//    modifier: Modifier = Modifier,
//    onLinkClick: (String) -> Unit = {}
) {
    notesViewModel.loadAllThreads(noteId)
    val threads by notesViewModel.threads.collectAsState(initial = emptyList())

    val linkMetaInfo = notesViewModel.linkMetadata.observeAsState()

    // Placeholder for link preview visibility and data
    var message by remember { mutableStateOf("") }
    var showLinkPreview by remember { mutableStateOf(false) } // Will be dynamic based on URL detection
    var previewImageUrl by remember { mutableStateOf("") }
    var previewTitle by remember { mutableStateOf("") } // "Example Website Title"
    var previewDescription by remember { mutableStateOf("") }

    if (linkMetaInfo.value != null) {
        showLinkPreview = true
        previewImageUrl = linkMetaInfo.value?.imageUrl.toString()
        previewTitle = linkMetaInfo.value?.title.toString()
        previewDescription = linkMetaInfo.value?.description.toString()
    } else {
        showLinkPreview = false
        previewImageUrl = ""
        previewTitle = ""
        previewDescription = ""
    }

    Scaffold(
        topBar = {
            NoteAppBar(
                title = "Notes Viewer", isPinned = true,
                onBackClick = {
                    println("Search query changed to:")
                    navController.popBackStack()
                },
                onPinClick = {
                    println("Search query changed to:")
                },
                onMoreOptionsClick = {
                    println("Search query changed to:")
                },
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkLighter)
                    .padding(bottom = 8.dp)
            ) {
                if(showLinkPreview){
                    HorizontalLinkPreviewCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        imageUrl = previewImageUrl, // No image
                        title = previewTitle,
                        description = previewDescription,
                        onRemove = {
                            notesViewModel.clearLinkMetadata()
                            showLinkPreview = false
                            previewImageUrl = ""
                            previewTitle = ""
                            previewDescription = ""
                        },
                    )
                }

                MessageInputTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    value = message,
                    onSendMessage = {
                        // Handle sending the message, e.g., send to ViewModel
                        println("Sending message: $message")
                        if(message.isNotEmpty()){
                            val newThread = ThreadEntity(
                                noteOwnerId = noteId,
                                threadId = "thread_${System.currentTimeMillis()}",
                                imageUrl = previewImageUrl,
                                linkTitle = previewTitle,
                                description = previewDescription,
                                content = message,
                                timestamp = Instant.now().toString(),
                            )
                            notesViewModel.addThread(newThread)
                            notesViewModel.clearLinkMetadata()
                            message = ""
                            showLinkPreview = false
                            previewImageUrl = ""
                            previewTitle = ""
                            previewDescription = ""
                        }
                    },
                    onValueChange = { message = it
                         notesViewModel.fetchMetadataForText(it)
                                    },
                    onMicButtonClick = {
                        // Handle mic button click, e.g., start voice input
                        println("Mic button clicked!")
                    }
                )
            }

        }
    ) { paddingValues ->
        LazyColumn(
            reverseLayout = true,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Padding to account for header and input
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp) // Internal padding
        ) {
            items(threads.reversed()) { thread ->
                MessageBubble(thread = thread, onLinkClick = {})
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
    NoteViewScreen(
        navController = rememberNavController(),
        notesViewModel = viewModel(),
        noteId = "",
    )
}