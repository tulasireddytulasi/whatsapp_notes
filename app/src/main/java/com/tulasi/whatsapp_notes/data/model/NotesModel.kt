package com.tulasi.whatsapp_notes.data.model

data class NotesModel(
    val notesList: List<Note>
)

data class Note(
    val noteId: String,
    val title: String,
    val category: String,
    val timestamp: String,
    val isPinned: Boolean,
    val colorStripHex: String? = null,
)

data class Thread(
    val threadId: String,
    val noteOwnerId: String,
    val content: String,
    val timestamp: String,
    val imageUrl: String?, // Nullable
    val linkTitle: String?,    // Nullable
    val description: String? // Nullable
)

data class LinkPreview(
    val imageUrl: String,
    val title: String,
    val description: String,
)

