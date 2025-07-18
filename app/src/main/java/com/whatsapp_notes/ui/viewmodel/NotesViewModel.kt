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
import com.whatsapp_notes.data.model.ThreadUiState
import com.whatsapp_notes.ui.screens.create_edit_notes_screen.common.LinkMetadataFetcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant

class NotesViewModel(private val noteDao: NoteDao, private val threadDao: ThreadDao) : ViewModel() {

    private val _notesWithThreads = MutableStateFlow<List<NoteWithThreads>>(emptyList())
    val notesWithThreads: StateFlow<List<NoteWithThreads>> = _notesWithThreads.asStateFlow()

    private val _currentNoteId = MutableStateFlow<String?>(null)

    // The actual state holder for ThreadUiState objects
    private val _threads = MutableStateFlow<List<ThreadUiState>>(emptyList())
    val threads: StateFlow<List<ThreadUiState>> = _threads.asStateFlow() // Expose as StateFlow

    // State for the message input field
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

    // New state for selection mode
    private val _selectionModeActive = MutableStateFlow(false)
    val selectionModeActive: StateFlow<Boolean> = _selectionModeActive.asStateFlow()

    // States for Create/Edit Note Screen
    private val _noteTitle = MutableStateFlow("")
    val noteTitle: StateFlow<String> = _noteTitle.asStateFlow()

    private val _noteDescription = MutableStateFlow("")
    val noteDescription: StateFlow<String> = _noteDescription.asStateFlow()

    private val _selectedCategory = MutableStateFlow("")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    init {
        viewModelScope.launch {
            noteDao.getAllNotesWithThreads().collectLatest { data ->
                _notesWithThreads.value = data
            }
        }
    }

    // Function to update the current note ID and load threads for it
    fun setCurrentNoteId(noteId: String?) {
        _currentNoteId.value = noteId
        viewModelScope.launch {
            if (noteId != null) {
                threadDao.getThreadsForNote(noteId).collectLatest { threadEntities ->
                    // Map ThreadEntity to ThreadUiState, preserving selection if possible
                    val currentUiStates = _threads.value.associateBy { it.thread.threadId }
                    val newUiStates = threadEntities.map { entity ->
                        currentUiStates[entity.threadId]?.copy(thread = entity)
                            ?: ThreadUiState(thread = entity, isSelected = false)
                    }
                    _threads.value = newUiStates
                }
            } else {
                _threads.value = emptyList() // Clear threads if no noteId
            }
        }
    }

    // New function to toggle selection mode
    fun toggleSelectionMode(isActive: Boolean) {
        _selectionModeActive.value = isActive
        if (!isActive) {
            clearAllSelections() // If selection mode is deactivated, unselect all threads
        }
    }

    // Function to toggle the isSelected state of a specific thread
    fun toggleThreadSelection(threadId: String) {
        // Create a new list with the updated item
        _threads.value = _threads.value.map { threadUiState ->
            if (threadUiState.thread.threadId == threadId) {
                threadUiState.copy(isSelected = !threadUiState.isSelected)
            } else {
                threadUiState
            }
        }
    }

    // Function to clear all selections
    fun clearAllSelections() {
        _threads.value = _threads.value.map { it.copy(isSelected = false) }
    }


    fun updateMessageInput(newValue: String) {
        _messageInput.value = newValue
        fetchMetadataForText(newValue)
    }

    fun addSampleNote() {
        viewModelScope.launch {
            val newNote = NoteEntity(
                noteId = "note_${System.currentTimeMillis()}",
                title = "Meeting at 11:30 AM Every Monday",
                category = "Meeting",
                timestamp = Instant.now().toString(),
                isPinned = false,
                colorStripHex = "#FF00FF"
            )
            noteDao.insertNote(newNote)
            val newThread = ThreadEntity(
                threadId = "thread_${System.currentTimeMillis()}",
                noteOwnerId = newNote.noteId,
                content = "This is a sample thread content for ${newNote.noteId}",
                timestamp = Instant.now().toString(),
                imageUrl = null, linkTitle = null, description = null
            )
            threadDao.insertThreads(listOf(newThread))
        }
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

    fun clearLinkMetadata() {
        _linkMetadata.value = null
        _showLinkPreview.value = false
        _previewImageUrl.value = null
        _previewTitle.value = null
        _previewDescription.value = null
    }

    private fun fetchMetadataForText(text: String) {
        viewModelScope.launch {
            try {
                _isLoadingLinkMetadata.value = true
                _linkMetadataErrorMessage.value = null
                val metadata = LinkMetadataFetcher.fetchFirstAvailableLinkMetadata(text)
                _linkMetadata.value = metadata
                if (metadata != null) {
                    _showLinkPreview.value = true
                    _previewImageUrl.value = metadata.imageUrl
                    _previewTitle.value = metadata.title
                    _previewDescription.value = metadata.description
                } else {
                    clearLinkMetadata()
                    _linkMetadataErrorMessage.value =
                        "No link found or no link had sufficient metadata."
                }
            } catch (e: Exception) {
                _linkMetadataErrorMessage.value = "Failed to fetch metadata: ${e.message}"
                clearLinkMetadata()
                Log.e("LinkViewModel", "Error in fetching metadata", e)
            } finally {
                _isLoadingLinkMetadata.value = false
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

    fun deleteSelectedThreads() {
        viewModelScope.launch {
            val selectedThreadIds = _threads.value
                .filter { it.isSelected }
                .map { it.thread.threadId }

            if (selectedThreadIds.isNotEmpty()) {
                val deletedCount = threadDao.deleteThreadsByIds(selectedThreadIds)
                Log.d("NotesViewModel", "Deleted $deletedCount selected threads.")
                // After deletion, you might want to clear selection mode and refresh the list
                toggleSelectionMode(false)
                // The `setCurrentNoteId` will automatically re-fetch the updated list
                // but if you want to be explicit, you can call it again or ensure your
                // threadDao.getThreadsForNote() Flow handles deletions automatically.
            }
        }
    }

    fun deleteParticularThread(threadId: String) {
        viewModelScope.launch {
            val deletedCount = threadDao.deleteThreadById(threadId)
            Log.d("NotesViewModel", "Deleted $deletedCount thread with ID: $threadId")
            // No need to clear selection mode for single deletion unless it's part of a batch action
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
                    colorStripHex = "#FF00FF"
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