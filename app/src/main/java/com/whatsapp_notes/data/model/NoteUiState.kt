package com.whatsapp_notes.data.model

import com.whatsapp_notes.data.local.relations.NoteWithThreads

data class NoteUiState(
    val note: NoteWithThreads,
    // If you need thread information directly within the UI state for a note,
    // you can include it here. Otherwise, NoteWithThreads already handles it.
    // For simplicity in this example, we'll assume NoteUiState represents the NoteEntity directly
    // and selection is per Note, not per NoteWithThreads.
    // If you need selection on NoteWithThreads, you'd apply this pattern to NoteWithThreads.
    var isSelected: Boolean = false
)