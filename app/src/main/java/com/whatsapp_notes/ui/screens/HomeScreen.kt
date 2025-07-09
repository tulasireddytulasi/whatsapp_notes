package com.whatsapp_notes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width // Required for fixed width of pinned notes
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whatsapp_notes.data.model.Note
import com.whatsapp_notes.data.repository.NoteRepository
import com.whatsapp_notes.ui.components.CategoryFilterButtons
import com.whatsapp_notes.ui.components.HomeTopBar
import com.whatsapp_notes.ui.components.NoteCard // Import the unified NoteCard
import com.whatsapp_notes.ui.components.SearchBar
import com.whatsapp_notes.ui.theme.DarkDefault
import com.whatsapp_notes.ui.theme.DarkLighter
import com.whatsapp_notes.ui.theme.Gray400
import com.whatsapp_notes.ui.theme.Gray500
import com.whatsapp_notes.ui.theme.NotesAppTheme

/**
 * Composable function for the main Home Screen layout of the Notes App.
 * This screen displays the top bar, search, filters, pinned notes,
 * and all notes sections, along with a floating action button.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Work", "Personal", "Ideas")

    val pinnedNotes: List<Note> = remember { NoteRepository.getPinnedNotes() }
    val allNotes: List<Note> = remember { NoteRepository.getAllNotes() }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkLighter)
                    .padding(bottom = 8.dp)
            ) {
                HomeTopBar(onProfileClick = { /* TODO: Handle profile click navigation */ })

                SearchBar(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onSearchQueryChange = { newQuery ->
                        searchQuery = newQuery
                        println("Search query changed to: $searchQuery")
                    },
                    currentQuery = searchQuery
                )

                Spacer(modifier = Modifier.height(12.dp))

                CategoryFilterButtons(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { newCategory ->
                        selectedCategory = newCategory
                        println("Category selected: $selectedCategory")
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Handle new note creation */ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "+")
            }
        },
        containerColor = DarkDefault
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Pinned Notes Section
            item {
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
                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp) // gap-3 equivalent
                    ) {
                        items(pinnedNotes) { note ->
                            NoteCard(
                                note = note,
                                cardModifier = Modifier.width(256.dp), // Fixed width for pinned notes
                                onClick = { clickedNote ->
                                    println("Clicked pinned note: ${clickedNote.title}")
                                }
                            )
                        }
                    }
                }
            }

            // All Notes Section
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "All Notes",
                            fontSize = 14.sp, // text-sm
                            color = Gray400, // text-gray-400
                            style = MaterialTheme.typography.labelSmall // font-medium
                        )
                        Text(
                            text = "${allNotes.size} notes",
                            fontSize = 12.sp, // text-xs
                            color = Gray500 // text-gray-500
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    // No need for a nested LazyColumn height or scrollEnabled here,
                    // as the parent LazyColumn will handle the overall scrolling.
                    Column( // Using Column here as the parent LazyColumn handles scrolling
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp) // space-y-3 equivalent
                    ) {
                        allNotes.forEach { note ->
                            NoteCard(
                                note = note,
                                cardModifier = Modifier.fillMaxWidth(), // Fill width for all notes
                                onClick = { clickedNote ->
                                    println("Clicked all note: ${clickedNote.title}")
                                },
                                onDeleteClick = { deletedNote ->
                                    println("Delete note: ${deletedNote.title}")
                                },
                                onArchiveClick = { archivedNote ->
                                    println("Archive note: ${archivedNote.title}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Preview Composable for the HomeScreen.
 * Shows how the overall screen layout will look.
 */
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreview() {
    NotesAppTheme {
        HomeScreen()
    }
}
