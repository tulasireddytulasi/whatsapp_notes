package com.tulasi.whatsapp_notes.ui.screens.home_screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tulasi.whatsapp_notes.Routes
import com.tulasi.whatsapp_notes.data.model.NoteUiState
import com.tulasi.whatsapp_notes.ui.screens.create_edit_notes_screen.components.ColorPickerDialog
import com.tulasi.whatsapp_notes.ui.screens.home_screen.components.BottomSheetContentInternal
import com.tulasi.whatsapp_notes.ui.screens.home_screen.components.CategoryFilterButtons
import com.tulasi.whatsapp_notes.ui.screens.home_screen.components.HomeTopBar
import com.tulasi.whatsapp_notes.ui.screens.home_screen.components.NoteCard
import com.tulasi.whatsapp_notes.ui.screens.home_screen.components.SearchBar
import com.tulasi.whatsapp_notes.ui.screens.home_screen.components.SelectionAppBar
import com.tulasi.whatsapp_notes.ui.theme.DarkDefault
import com.tulasi.whatsapp_notes.ui.theme.DarkLighter
import com.tulasi.whatsapp_notes.ui.theme.Gray400
import com.tulasi.whatsapp_notes.ui.theme.Gray500
import com.tulasi.whatsapp_notes.ui.theme.NotesAppTheme
import com.tulasi.whatsapp_notes.ui.viewmodel.NotesViewModel
import kotlinx.coroutines.launch

/**
 * Composable function for the main Home Screen layout of the Notes App.
 * This screen displays the top bar, search, filters, pinned notes,
 * and all notes sections, along with a floating action button.
 *
 * @param navController The NavController used for navigating between screens.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    notesViewModel: NotesViewModel
) { // Add navController as a parameter
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    val selectedCategory by notesViewModel.selectedCategoryFilter.collectAsState()
    val categories = listOf("All", "Work", "Personal", "Ideas", "Others")
    val notesWithLastThread by notesViewModel.notesUiState.collectAsState(initial = emptyList())

    val noteSelectionModeActive by notesViewModel.noteSelectionModeActive.collectAsState()
    val selectedNotesCount = notesWithLastThread.count { it.isSelected }
    val showPin = notesViewModel.showPinIcon.collectAsState()

    val unPinnedNotes: List<NoteUiState> = notesWithLastThread.filter {
        !it.note.note.isPinned // The condition to check if a notes is pinned
    }

    // Logic: Use the 'filter' higher-order function available on Kotlin Collections.
    // The 'filter' function iterates over each element in the 'notes' list
    // and includes it in the resulting list only if the provided lambda expression
    // evaluates to 'true' for that element.
    val pinnedNotes: List<NoteUiState> = notesWithLastThread.filter { notes ->
        notes.note.note.isPinned // The condition to check if a notes is pinned
    }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showColorPickerDialog by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkLighter)
                    .padding(bottom = 8.dp)
            ) {
                if (noteSelectionModeActive) {
                    SelectionAppBar(
                        showPinIcon = showPin.value,
                        selectedCount = selectedNotesCount,
                        onClearSelection = { notesViewModel.toggleNoteSelectionMode(false) },
                        onDeleteSelected = {
                            notesViewModel.deleteSelectedNotes()
                        },
                        onEditSelection = {
                            // Todo: Impl Edit Notes
                            showBottomSheet = true
                        },
                        onPinnedSelected = {
                            notesViewModel.updatePinStatus(
                                onSuccess = {},
                                onError = {
                                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                },
                            )
                        },
                    )
                } else {
                    HomeTopBar(onProfileClick = {
                        navController.navigate(
                            Routes.SETTINGS_SCREEN
                        )
                    })
                }

                SearchBar(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onSearchQueryChange = { newQuery ->
                        searchQuery = newQuery
                        Toast.makeText(context, "Search notes disabled", Toast.LENGTH_SHORT).show()
                        println("Search query changed to: $searchQuery")
                    },
                    currentQuery = searchQuery
                )

                Spacer(modifier = Modifier.height(12.dp))

                CategoryFilterButtons(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { newCategory ->
                        notesViewModel.setCategoryFilter(newCategory)
                        println("Category selected: $selectedCategory")
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.CREATE_EDIT_SCREEN) },
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
                if (pinnedNotes.isNotEmpty()) {
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
                                    noteThread = note,
                                    // pinned notes
                                    cardModifier = Modifier
                                        .width(256.dp)
                                        .combinedClickable( // Use combinedClickable for long press
                                            onClick = {
                                                if (noteSelectionModeActive) {
                                                    notesViewModel.toggleNoteSelection(note.note.note.noteId)
                                                } else {
                                                    navController.navigate(
                                                        "${Routes.NOTE_FIRST_ARG}/${
                                                            note.note.note
                                                                .noteId
                                                        }/${note.note.note.title}/${note.note.note.isPinned}"
                                                    )
                                                }
                                            },
                                            onLongClick = {
                                                notesViewModel.toggleNoteSelectionMode(true)
                                                notesViewModel.toggleNoteSelection(note.note.note.noteId)
                                            }
                                        ),
                                )
                            }
                        }
                    }
                }
            }

            // All Notes Section
            item {
                if(unPinnedNotes.isNotEmpty()){
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
                                text = "${unPinnedNotes.size} notes",
                                fontSize = 12.sp, // text-xs
                                color = Gray500 // text-gray-500
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp) // space-y-3 equivalent
                        ) {
                            unPinnedNotes.forEach { note ->
                                NoteCard(
                                    noteThread = note,
                                    cardModifier = Modifier
                                        .fillMaxWidth()
                                        .combinedClickable( // Use combinedClickable for long press
                                            onClick = {
                                                if (noteSelectionModeActive) {
                                                    notesViewModel.toggleNoteSelection(note.note.note.noteId)
                                                } else {
                                                    navController.navigate(
                                                        "${Routes.NOTE_FIRST_ARG}/${
                                                            note.note.note
                                                                .noteId
                                                        }/${note.note.note.title}/${note.note.note.isPinned}"
                                                    )
                                                }
                                            },
                                            onLongClick = {
                                                notesViewModel.toggleNoteSelectionMode(true)
                                                notesViewModel.toggleNoteSelection(note.note.note.noteId)
                                            }
                                        ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    ColorPickerDialog(
        showDialog = showColorPickerDialog,
        onDismissRequest = { showColorPickerDialog = false },
        onColorSelected = { color ->
            notesViewModel.updateSelectedColor(color)
            // selectedColor = color
            showColorPickerDialog = false // Dismiss dialog after color is selected
        },
        initialColor = "#FFFFFF", // Pass the current selected color as initial
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }, sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            // Calculate 30% of screen height for the bottom sheet content
            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp.dp
            val sheetHeight = screenHeight * 0.3f // 30% of screen height
            BottomSheetContentInternal(
                title = notesViewModel.getSelectedNotes().firstOrNull()?.title ?: "",
                notesViewModel = notesViewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sheetHeight) // Set the fixed height for the bottom sheet
                    .background(MaterialTheme.colorScheme.surface), // Apply background for visibility
                onUpdate = { text ->
                    notesViewModel.updateNotes(text)
                    notesViewModel.toggleNoteSelectionMode(false)
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }, // Pass the external onUpdate callback
                onColorPicker = {
                    showColorPickerDialog = true
                }
            )
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
        // For preview, provide a mock NavController
        HomeScreen(navController = rememberNavController(), notesViewModel = viewModel())
    }
}
