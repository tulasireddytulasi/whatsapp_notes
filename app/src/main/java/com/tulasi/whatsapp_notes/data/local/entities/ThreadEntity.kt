package com.tulasi.whatsapp_notes.data.local.entities


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "threads",
    foreignKeys = [
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = ["noteId"],
            childColumns = ["noteOwnerId"],
            onDelete = ForeignKey.CASCADE // This ensures cascade deletion of threads when a note is deleted
        )
    ],
    indices = [Index(value = ["noteOwnerId"])]
)
data class ThreadEntity(
    @PrimaryKey
    val threadId: String,
    val noteOwnerId: String, // Foreign Key referencing NoteEntity
    val content: String,
    val timestamp: String, // Consider using a TypeConverter for Instant or LocalDateTime
    val imageUrl: String?, // Nullable
    val linkTitle: String?,    // Nullable
    val description: String? // Nullable
)