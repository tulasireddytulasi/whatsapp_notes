package com.whatsapp_notes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.whatsapp_notes.data.local.entities.ThreadEntity
import kotlinx.coroutines.flow.Flow // Import Flow

@Dao
interface ThreadDao {
    @Query("SELECT * FROM threads WHERE noteOwnerId = :noteId ORDER BY timestamp DESC") // Added ORDER BY for consistent thread order
    fun getThreadsForNote(noteId: String): Flow<List<ThreadEntity>> // Changed to Flow

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThreads(threads: List<ThreadEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThread(thread: ThreadEntity)

    @Update
    suspend fun updateThread(thread: ThreadEntity)

    @Delete
    suspend fun deleteThread(thread: ThreadEntity)
}