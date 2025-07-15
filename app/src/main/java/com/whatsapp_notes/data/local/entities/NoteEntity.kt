package com.whatsapp_notes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey
    val noteId: String,
    val title: String,
    val category: String,
    val timestamp: String, // Consider using a TypeConverter for Instant or LocalDateTime
    val isPinned: Boolean,
    val colorStripHex: String
)