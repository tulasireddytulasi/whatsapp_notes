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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant

class NotesViewModel(private val noteDao: NoteDao, private val threadDao: ThreadDao) : ViewModel() {

    private val _notesWithThreads = MutableStateFlow<List<NoteWithThreads>>(emptyList())
    val notesWithThreads: StateFlow<List<NoteWithThreads>> = _notesWithThreads

    private val _threads = MutableStateFlow<List<ThreadEntity>>(emptyList())
    val threads: StateFlow<List<ThreadEntity>> = _threads


    init {
        loadNotesWithLastThreadContent()
    }

    private fun loadNotesWithLastThreadContent() {
        viewModelScope.launch {
            // Observe changes from the database (if you want live updates)
            // For simplicity, this example just fetches once.
            // If you want continuous updates, your DAO query should return Flow<Map<NoteEntity, String?>>
            // and then collectAsState will automatically update.
            val data = noteDao.getAllNotesWithThreads()
            _notesWithThreads.value = data
        }
    }

     fun loadAllThreads(noteId : String) {
        viewModelScope.launch {
            val data = threadDao.getThreadsForNote(noteId)
            _threads.value = data
        }
    }

    // You might also add functions here to insert, update, or delete notes for your app
    // Example:

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
            threadDao.insertThreads(listOf(newThread)) // Assuming you have a way to insert threads via NoteDao or directly via ThreadDao
            loadNotesWithLastThreadContent() // Refresh the data
        }
    }

    fun addNotes(noteEntity: NoteEntity, threadEntity: ThreadEntity) {
        viewModelScope.launch {
            noteDao.insertNote(noteEntity)
            threadDao.insertThreads(listOf(threadEntity))
            loadNotesWithLastThreadContent()
        }
    }

    fun addThread(threadEntity: ThreadEntity) {
        viewModelScope.launch {
            threadDao.insertThread(threadEntity)
            loadAllThreads(threadEntity.noteOwnerId)
        }
    }

    private val _linkMetadata = MutableLiveData<LinkMetadataFetcher.LinkMetadata?>()
    val linkMetadata: LiveData<LinkMetadataFetcher.LinkMetadata?> = _linkMetadata

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage


    fun clearLinkMetadata() {
        _linkMetadata.value = null
    }
    fun fetchMetadataForText(text: String) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                // Call the updated function
                val metadata = LinkMetadataFetcher.fetchFirstAvailableLinkMetadata(text)
                _linkMetadata.value = metadata
                if (metadata == null) {
                    _errorMessage.value = "No link found or no link had sufficient metadata."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch metadata: ${e.message}"
                _linkMetadata.value = null
                Log.e("LinkViewModel", "Error in fetching metadata", e)
            } finally {
                _isLoading.value = false
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