package com.whatsapp_notes.data.model

/**
 * Data class representing a single note.
 *
 * @property id Unique identifier for the note.
 * @property title The title of the note.
 * @property content The main content/body of the note.
 * @property category The category the note belongs to (e.g., "Work", "Personal", "Ideas").
 * @property timestamp The timestamp when the note was last updated or created.
 * @property isPinned Boolean indicating if the note is pinned.
 * @property colorStripHex The hex color code for the color strip (e.g., "#EF4444" for red).
 */
data class Note(
    val id: String,
    val title: String,
    val content: String,
    val category: String,
    val timestamp: String, // Using String for simplicity, can be converted to DateTime later
    val isPinned: Boolean,
    val colorStripHex: String? = null // Optional color strip for all notes
)
