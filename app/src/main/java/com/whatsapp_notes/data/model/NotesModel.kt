package com.whatsapp_notes.data.model

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
    val threads: List<Thread>
)

data class Thread(
    val threadId: String,
    val content: String,
    val timestamp: String,
    val linkPreview: LinkPreview? = null // Nullable to indicate if a link preview exists
)

data class LinkPreview(
    val imageUrl: String,
    val title: String,
    val description: String,
    val url: String
)

