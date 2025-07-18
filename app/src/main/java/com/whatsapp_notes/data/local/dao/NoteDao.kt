package com.whatsapp_notes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.whatsapp_notes.data.local.entities.NoteEntity
import com.whatsapp_notes.data.local.relations.NoteWithThreads
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>> // Changed to Flow

    @Transaction
    @Query("SELECT * FROM notes")
    fun getAllNotesWithThreads(): Flow<List<NoteWithThreads>> // Changed to Flow

    // --- New method for filtering by category ---
    @Transaction
    @Query("SELECT * FROM notes WHERE :category = 'All' OR category = :category")
    fun getNotesWithThreadsByCategory(category: String): Flow<List<NoteWithThreads>>

    @Query(
        """
        SELECT
            n.*,
            (SELECT t.content FROM threads AS t WHERE t.noteOwnerId = n.noteId ORDER BY t.timestamp DESC LIMIT 1) AS lastThreadContent
        FROM notes AS n
        """
    )
    fun getAllNotesWithLastThread(): Flow<List<NoteWithLastThread>> // Changed to Flow

    @Query("SELECT * FROM notes WHERE noteId = :id")
    suspend fun getNoteById(id: String): NoteEntity? // Still suspend as it's a one-time fetch

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity) // Still suspend for write operations

    @Update
    suspend fun updateNote(note: NoteEntity) // Still suspend for write operations

    @Delete
    suspend fun deleteNote(note: NoteEntity) // Still suspend for write operations
}