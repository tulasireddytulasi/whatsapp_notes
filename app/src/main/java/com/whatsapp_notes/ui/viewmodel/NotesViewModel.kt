package com.whatsapp_notes.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.whatsapp_notes.data.local.dao.NoteDao
import com.whatsapp_notes.data.local.dao.ThreadDao
import com.whatsapp_notes.data.local.entities.NoteEntity
import com.whatsapp_notes.data.local.entities.ThreadEntity
import com.whatsapp_notes.data.local.relations.NoteWithThreads
import com.whatsapp_notes.data.model.NoteUiState
import com.whatsapp_notes.data.model.ThreadUiState
import com.whatsapp_notes.ui.screens.create_edit_notes_screen.common.LinkMetadataFetcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant

class NotesViewModel(private val noteDao: NoteDao, private val threadDao: ThreadDao) : ViewModel() {

    private val _currentNoteId = MutableStateFlow<String?>(null)

    // Tracks selected thread IDs to reapply selection state
    private val _threadSelectionStates = MutableStateFlow<Map<String, Boolean>>(emptyMap())

    // The actual UI-facing StateFlow for ThreadUiState objects
    @OptIn(ExperimentalCoroutinesApi::class)
    val threads: StateFlow<List<ThreadUiState>> = _currentNoteId
        .flatMapLatest { noteId ->
            if (noteId != null) {
                threadDao.getThreadsForNote(noteId) // Database source of truth for ThreadEntity
            } else {
                MutableStateFlow(emptyList())
            }
        }
        .combine(_threadSelectionStates) { threadEntities, selectionMap ->
            // Map ThreadEntity to ThreadUiState, reapplying current selection status
            threadEntities.map { entity ->
                ThreadUiState(
                    thread = entity,
                    isSelected = selectionMap[entity.threadId] ?: false
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Keep active for 5 seconds after last observer
            initialValue = emptyList() // Initial value for the combined flow
        )

    private val _messageInput = MutableStateFlow("")
    val messageInput: StateFlow<String> = _messageInput.asStateFlow()

    // State for link metadata
    private val _linkMetadata = MutableLiveData<LinkMetadataFetcher.LinkMetadata?>()
    val linkMetadata: LiveData<LinkMetadataFetcher.LinkMetadata?> = _linkMetadata

    private val _isLoadingLinkMetadata = MutableLiveData(false)
    val isLoadingLinkMetadata: LiveData<Boolean> = _isLoadingLinkMetadata

    private val _linkMetadataErrorMessage = MutableLiveData<String?>()
    val linkMetadataErrorMessage: LiveData<String?> = _linkMetadataErrorMessage

    // Consolidated state for link preview visibility and data
    private val _showLinkPreview = MutableStateFlow(false)
    val showLinkPreview: StateFlow<Boolean> = _showLinkPreview.asStateFlow()

    private val _previewImageUrl = MutableStateFlow<String?>(null)
    val previewImageUrl: StateFlow<String?> = _previewImageUrl.asStateFlow()

    private val _previewTitle = MutableStateFlow<String?>(null)
    val previewTitle: StateFlow<String?> = _previewTitle.asStateFlow()

    private val _previewDescription = MutableStateFlow<String?>(null)
    val previewDescription: StateFlow<String?> = _previewDescription.asStateFlow()

    // Selection mode active status
    private val _selectionModeActive = MutableStateFlow(false)
    val selectionModeActive: StateFlow<Boolean> = _selectionModeActive.asStateFlow()

    // Selection mode active status (for notes)
    private val _noteSelectionModeActive = MutableStateFlow(false)
    val noteSelectionModeActive: StateFlow<Boolean> = _noteSelectionModeActive.asStateFlow()

    // State for the list of NoteUiState objects
    private val _notesUiState = MutableStateFlow<List<NoteUiState>>(emptyList())
    val notesUiState: StateFlow<List<NoteUiState>> = _notesUiState.asStateFlow()


    // States for Create/Edit Note Screen
    private val _noteTitle = MutableStateFlow("")
    val noteTitle: StateFlow<String> = _noteTitle.asStateFlow()

    private val _noteDescription = MutableStateFlow("")
    val noteDescription: StateFlow<String> = _noteDescription.asStateFlow()

    private val _selectedCategory = MutableStateFlow("")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // States for Create/Edit Note Screen
    private val _selectedColor = MutableStateFlow("#FFFFFF")
    val selectedColor: StateFlow<String> = _selectedColor.asStateFlow()

    // --- New additions for category filtering ---
    private val _selectedCategoryFilter = MutableStateFlow("All") // Default to "All"
    val selectedCategoryFilter: StateFlow<String> = _selectedCategoryFilter.asStateFlow()

    // This will now be the main source for notesWithThreads, filtered by category
    @OptIn(ExperimentalCoroutinesApi::class)
    val notesWithThreads: StateFlow<List<NoteWithThreads>> = _selectedCategoryFilter
        .flatMapLatest { category ->
            if (category == "All") {
                noteDao.getAllNotesWithThreads() // Get all notes if category is "All"
            } else {
                noteDao.getNotesWithThreadsByCategory(category) // Filter by specific category
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    // --- End new additions ---

    // Function to set the category filter
    fun setCategoryFilter(category: String) {
        _selectedCategoryFilter.value = category
    }

    init {
        getAllNotesWithThreads()
    }

    private fun getAllNotesWithThreads() {
        // Observe notesWithThreads and map them to NoteUiState
        viewModelScope.launch {
            notesWithThreads.collect { noteWithThreadsList ->
                // Map NoteWithThreads to NoteUiState, preserving existing selection state if any
                _notesUiState.value = noteWithThreadsList.map { noteWithThreads ->
                    val existingUiState = _notesUiState.value.find {
                        it.note.note.noteId ==
                                noteWithThreads
                                    .note.noteId
                    }
                    NoteUiState(
                        note = noteWithThreads,
                        isSelected = existingUiState?.isSelected ?: false
                    )
                }
            }
        }
    }

    // New: Function to delete selected notes
    fun deleteSelectedNotes() {
        viewModelScope.launch {
            val selectedNoteIds = getSelectedNotes().map { it.noteId }
            if (selectedNoteIds.isNotEmpty()) {
                val deletedCount = noteDao.deleteNotesByIds(selectedNoteIds)
                Log.d("NotesViewModel", "Deleted $deletedCount selected notes.")
                // Also delete associated threads
                threadDao.deleteThreadsByNoteIds(selectedNoteIds)
                clearAllNoteSelections() // Clear note selections after deletion
            }
        }
    }

    // Functions for managing note selection
    fun toggleNoteSelectionMode(isActive: Boolean) {
        _noteSelectionModeActive.value = isActive
        if (!isActive) {
            clearAllNoteSelections()
        }
    }

    fun toggleNoteSelection(noteId: String) {
        _notesUiState.value = _notesUiState.value.map { noteUiState ->
            if (noteUiState.note.note.noteId == noteId) {
                noteUiState.copy(isSelected = !noteUiState.isSelected)
            } else {
                noteUiState
            }
        }
        // Update selection mode active status based on whether any notes are selected
        _noteSelectionModeActive.value = _notesUiState.value.any { it.isSelected }
    }

    fun getSelectedNotes(): List<NoteEntity> {
        return _notesUiState.value.filter { it.isSelected }.map { it.note.note }
    }

    fun clearAllNoteSelections() {
        _notesUiState.value = _notesUiState.value.map { it.copy(isSelected = false) }
        _noteSelectionModeActive.value = false
    }

    fun setCurrentNoteId(noteId: String?) {
        _currentNoteId.value = noteId
        if (noteId == null) {
            // If navigating away from a note, clear all selection states
            _threadSelectionStates.value = emptyMap()
            _selectionModeActive.value = false
        }
        // The `threads` flow (which is derived from _currentNoteId and _threadSelectionStates)
        // will automatically update itself.
    }

    fun toggleSelectionMode(isActive: Boolean) {
        _selectionModeActive.value = isActive
        if (!isActive) {
            clearAllSelections() // Clear selections when exiting selection mode
        }
    }

    fun toggleThreadSelection(threadId: String) {
        // Update the selection map
        _threadSelectionStates.value = _threadSelectionStates.value.toMutableMap().apply {
            val currentSelection = get(threadId) ?: false
            put(threadId, !currentSelection)
        }
        // Update selection mode active status based on whether any threads are selected
        _selectionModeActive.value = _threadSelectionStates.value.any { it.value }
    }


    private fun clearAllSelections() {
        _threadSelectionStates.value = emptyMap()
        _selectionModeActive.value = false
    }

    fun getSelectedThreads(): List<ThreadEntity> {
        return threads.value.filter { it.isSelected }.map { it.thread }
    }

    fun updateMessageInput(newValue: String) {
        _messageInput.value = newValue
        fetchMetadataForText(newValue)
    }

    fun addNotes(noteEntity: NoteEntity, threadEntity: ThreadEntity) {
        viewModelScope.launch {
            noteDao.insertNote(noteEntity)
            threadDao.insertThreads(listOf(threadEntity))
        }
    }

    fun addThread(threadEntity: ThreadEntity) {
        viewModelScope.launch {
            threadDao.insertThread(threadEntity)
        }
    }

    // New: Function to delete selected threads
    fun deleteSelectedThreads() {
        viewModelScope.launch {
            val selectedThreadIds = getSelectedThreads().map { it.threadId }
            if (selectedThreadIds.isNotEmpty()) {
                val deletedCount = threadDao.deleteThreadsByIds(selectedThreadIds)
                Log.d("NotesViewModel", "Deleted $deletedCount selected threads.")
                // Clear selections after deletion
                clearAllSelections()
            }
        }
    }

    // New: Function to delete a particular thread by ID
    fun deleteParticularThread(threadId: String) {
        viewModelScope.launch {
            val deletedCount = threadDao.deleteThreadById(threadId)
            Log.d("NotesViewModel", "Deleted $deletedCount thread with ID: $threadId")
            // No need to clear all selections for single deletion unless it affects current selection mode
            // If the deleted thread was selected, its selection state will naturally disappear when `threads` re-emits.
        }
    }

    fun clearLinkMetadata() {
        _linkMetadata.postValue(null)
        _showLinkPreview.value = false
        _previewImageUrl.value = null
        _previewTitle.value = null
        _previewDescription.value = null
    }

    private fun fetchMetadataForText(text: String) {
        _isLoadingLinkMetadata.postValue(true)
        _linkMetadataErrorMessage.postValue(null)

        viewModelScope.launch {
            try {
                val metadata = LinkMetadataFetcher.fetchFirstAvailableLinkMetadata(text)
                _linkMetadata.postValue(metadata)
                if (metadata != null) {
                    _showLinkPreview.value = true
                    _previewImageUrl.value = metadata.imageUrl
                    _previewTitle.value = metadata.title
                    _previewDescription.value = metadata.description
                } else {
                    clearLinkMetadata()
                    _linkMetadataErrorMessage.postValue("No link found or no link had sufficient metadata.")
                }
            } catch (e: Exception) {
                _linkMetadataErrorMessage.postValue("Failed to fetch metadata: ${e.message}")
                clearLinkMetadata()
                Log.e("LinkViewModel", "Error in fetching metadata", e)
            } finally {
                _isLoadingLinkMetadata.postValue(false)
            }
        }
    }

    fun sendMessage(noteId: String) {
        val currentMessage = _messageInput.value
        if (currentMessage.isNotEmpty()) {
            viewModelScope.launch {
                val newThread = ThreadEntity(
                    noteOwnerId = noteId,
                    threadId = "thread_${System.currentTimeMillis()}",
                    imageUrl = _previewImageUrl.value,
                    linkTitle = _previewTitle.value,
                    description = _previewDescription.value,
                    content = currentMessage,
                    timestamp = Instant.now().toString(),
                )
                addThread(newThread)
                _messageInput.value = ""
                clearLinkMetadata()
            }
        }
    }

    fun updateNotes(title: String) {
        viewModelScope.launch {
            try {
                val selectedNoteIds = getSelectedNotes().map { it.noteId }
                val noteId = selectedNoteIds.firstOrNull() ?: return@launch
                lateinit var newNote: NoteEntity
                val updatedNotes = noteDao.getNoteById(noteId)
                // current state
                updatedNotes?.let { notes ->
                    newNote = notes.copy(
                        title = title,
                        // category = category,
                        timestamp = Instant.now().toString(),
                        colorStripHex = _selectedColor.value,
                    )
                }
                val rowsUpdated = noteDao.updateNote(newNote)
                if (rowsUpdated > 0) {
                    Log.d(
                        "NotesViewModel",
                        "Notes ${updatedNotes?.noteId ?: ""} updated successfully."
                    )
                    // If your UI relies on the Flow from getThreadsForNote(),
                    // the UI will automatically update when Room emits the new data.
                } else {
                    Log.w(
                        "NotesViewModel",
                        "Notes ${updatedNotes?.noteId ?: ""} not found for update."
                    )
                }
            } catch (e: Exception) {
                Log.e("NotesViewModel", "Error updating notes}", e)
            }
        }
    }

    /**
     * Updates an existing thread in the database.
     * @param updatedThread The ThreadEntity object with the updated details.
     */
    private fun updateExistingThreadInDb(updatedThread: ThreadEntity) {
        viewModelScope.launch {
            try {
                val rowsUpdated = threadDao.updateThread(updatedThread)
                if (rowsUpdated > 0) {
                    Log.d(
                        "NotesViewModel",
                        "Thread ${updatedThread.threadId} updated successfully."
                    )
                    // If your UI relies on the Flow from getThreadsForNote(),
                    // the UI will automatically update when Room emits the new data.
                } else {
                    Log.w(
                        "NotesViewModel",
                        "Thread ${updatedThread.threadId} not found for update."
                    )
                }
            } catch (e: Exception) {
                Log.e("NotesViewModel", "Error updating thread ${updatedThread.threadId}", e)
            }
        }
    }

    fun updateNoteTitle(newValue: String) {
        _noteTitle.value = newValue
    }

    fun updateNoteDescription(newValue: String) {
        _noteDescription.value = newValue
        fetchMetadataForText(newValue)
    }

    fun updateSelectedCategory(newValue: String) {
        _selectedCategory.value = newValue
    }

    fun updateSelectedColor(newValue: String) {
        _selectedColor.value = newValue
    }

    fun loadNoteDetails(noteId: String, threadId: String) {
        viewModelScope.launch {
            val note = noteDao.getNoteById(noteId)
            val threadForNote =
                threadDao.getThreadById(threadId) // Use getThreadsForNoteOnce to fetch
            // current state
            note?.let {
                _noteTitle.value = it.title
                _selectedCategory.value = it.category
            }
            threadForNote?.let {
                _noteDescription.value = it.content
                // Also load link metadata if available for the existing thread
                _previewImageUrl.value = it.imageUrl
                _previewTitle.value = it.linkTitle
                _previewDescription.value = it.description
                _showLinkPreview.value =
                    !it.imageUrl.isNullOrEmpty() || !it.linkTitle.isNullOrEmpty()
            }
        }
    }

    fun saveNote(
        onSuccess: (NoteEntity) -> Unit,
        onError: (String) -> Unit,
        existingThreadId: String? = null
    ) {
        val title = _noteTitle.value
        val description = _noteDescription.value
        val category = _selectedCategory.value
        val selectedColor = _selectedColor.value

        if (title.isEmpty() || description.isEmpty() || category.isEmpty()) {
            onError("Please fill title, description & category fields")
            return
        }

        viewModelScope.launch {
            if (existingThreadId != null) {
                // Find the current ThreadEntity (you'd likely fetch it from the DB or your _threads.value)
                val currentThreadUiState = threadDao.getThreadById(existingThreadId)
                currentThreadUiState?.let { uiState ->
                    val updatedThreadEntity = uiState.copy(
                        content = description,
                        imageUrl = _previewImageUrl.value,
                        linkTitle = _previewTitle.value,
                        description = _previewDescription.value,
                        timestamp = Instant.now().toString()
                    )
                    updateExistingThreadInDb(updatedThreadEntity)
                    // If we're updating a thread, we assume the note already exists
                    // We might need to fetch the NoteEntity to pass it to onSuccess if required for navigation
                    val noteEntity = noteDao.getNoteById(uiState.noteOwnerId)
                    if (noteEntity != null) {
                        onSuccess(noteEntity)
                    } else {
                        onError("Note associated with thread not found.")
                    }
                } ?: onError("Thread to update not found.")
            } else {
                // Create new note and thread
                val newNote = NoteEntity(
                    noteId = "note_${System.currentTimeMillis()}",
                    title = title,
                    category = category,
                    timestamp = Instant.now().toString(),
                    isPinned = false,
                    colorStripHex = selectedColor,
                )
                noteDao.insertNote(newNote)

                val newThread = ThreadEntity(
                    threadId = "thread_${System.currentTimeMillis()}",
                    noteOwnerId = newNote.noteId,
                    content = description,
                    timestamp = Instant.now().toString(),
                    imageUrl = _previewImageUrl.value,
                    linkTitle = _previewTitle.value,
                    description = _previewDescription.value
                )
                threadDao.insertThreads(listOf(newThread))
                onSuccess(newNote)
            }
        }
    }

    /**
     * Resets the ViewModel's state related to note creation/editing.
     * Call this when navigating away from the create/edit screen.
     */
    fun resetNoteCreationState() {
        _noteTitle.value = ""
        _noteDescription.value = ""
        _selectedCategory.value = ""
        clearLinkMetadata()
    }
}

class NotesViewModelFactory(private val noteDao: NoteDao, private val threadDao: ThreadDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(noteDao, threadDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}