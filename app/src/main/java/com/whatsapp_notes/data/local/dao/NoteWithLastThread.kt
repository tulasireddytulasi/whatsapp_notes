package com.whatsapp_notes.data.local.dao

// In a suitable package, e.g., com.example.notesapp.data.local.dto or com.example.notesapp.data.local.relations

import androidx.room.Embedded
import com.whatsapp_notes.data.local.entities.NoteEntity

data class NoteWithLastThread(
    @Embedded
    val note: NoteEntity,
    val lastThreadContent: String? // This will map to your 'lastThreadContent' column
)