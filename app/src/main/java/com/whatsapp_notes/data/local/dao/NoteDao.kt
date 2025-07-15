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

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<NoteEntity>

    @Transaction
    @Query("SELECT * FROM notes")
    suspend fun getAllNotesWithThreads(): List<NoteWithThreads>

//    @Query(
//        """
//        SELECT
//            n.*,
//            (SELECT t.content FROM threads AS t WHERE t.noteOwnerId = n.noteId ORDER BY t.timestamp DESC LIMIT 1) AS lastThreadContent
//        FROM notes AS n
//        """
//    )
//    fun getAllNotesWithLastThreadContent(): Map<NoteEntity, String?>

    @Query(
        """
        SELECT
            n.*,
            (SELECT t.content FROM threads AS t WHERE t.noteOwnerId = n.noteId ORDER BY t.timestamp DESC LIMIT 1) AS lastThreadContent
        FROM notes AS n
        """
    )
    // Change the return type here
    suspend fun getAllNotesWithLastThread(): List<NoteWithLastThread>

    @Query("SELECT * FROM notes WHERE noteId = :id")
    suspend fun getNoteById(id: String): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}