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
import com.whatsapp_notes.ui.screens.create_edit_notes_screen.common.LinkMetadataFetcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant

class NotesViewModel(private val noteDao: NoteDao, private val threadDao: ThreadDao) : ViewModel() {

    private val _notesWithThreads = MutableStateFlow<List<NoteWithThreads>>(emptyList())
    val notesWithThreads: StateFlow<List<NoteWithThreads>> = _notesWithThreads.asStateFlow()

    // --- New additions for robust thread loading ---
    private val _currentNoteId = MutableStateFlow<String?>(null) // State to hold the currently active note ID

    // 'threads' now reacts to changes in _currentNoteId

    // Corrected: Use stateIn to convert the Flow from flatMapLatest into a StateFlow
    @OptIn(ExperimentalCoroutinesApi::class)
    val threads: StateFlow<List<ThreadEntity>> = _currentNoteId.flatMapLatest { noteId ->
        if (noteId != null) {
            threadDao.getThreadsForNote(noteId)
        } else {
            MutableStateFlow(emptyList()) // Return an empty Flow if no noteId
        }
    }.stateIn( // Use stateIn here
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), // Start collecting when UI observes, stop after 5s if no observers
        initialValue = emptyList() // Initial value for the StateFlow
    )
    // --- End new additions ---

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

    init {
        viewModelScope.launch {
            noteDao.getAllNotesWithThreads().collectLatest { data ->
                _notesWithThreads.value = data
            }
        }
    }

    // This function now simply updates the currentNoteId state
    fun setCurrentNoteId(noteId: String?) {
        _currentNoteId.value = noteId
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
        _isLoadingLinkMetadata.value = true
        _linkMetadataErrorMessage.value = null
        viewModelScope.launch {
            try {
                val metadata = LinkMetadataFetcher.fetchFirstAvailableLinkMetadata(text)
                _linkMetadata.value = metadata
                if (metadata != null) {
                    _showLinkPreview.value = true
                    _previewImageUrl.value = metadata.imageUrl
                    _previewTitle.value = metadata.title
                    _previewDescription.value = metadata.description
                } else {
                    clearLinkMetadata()
                    _linkMetadataErrorMessage.value = "No link found or no link had sufficient metadata."
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