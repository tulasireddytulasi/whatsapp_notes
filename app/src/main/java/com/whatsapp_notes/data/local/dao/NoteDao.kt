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
    suspend fun updateNote(note: NoteEntity): Int // Still suspend for write operations

    @Delete
    suspend fun deleteNote(note: NoteEntity) // Still suspend for write operations

    @Query("DELETE FROM notes WHERE noteId = :noteId")
    suspend fun deleteNotesById(noteId: String): Int

    @Query("DELETE FROM notes WHERE noteId IN (:noteIds)")
    suspend fun deleteNotesByIds(noteIds: List<String>): Int

    /**
     * Updates the pinned status of a list of notes.
     *
     * @param noteIds The list of note IDs to update.
     * @param isPinnedStatus The new pinned status (true for pinned, false for unpinned).
     * @return The number of rows updated.
     */
    @Query("UPDATE notes SET isPinned = :isPinnedStatus WHERE noteId IN (:noteIds)")
    suspend fun updateNotesPinnedStatus(noteIds: List<String>, isPinnedStatus: Boolean): Int

}