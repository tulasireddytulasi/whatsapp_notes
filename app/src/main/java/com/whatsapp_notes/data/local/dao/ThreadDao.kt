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

    /**
     * Updates an existing ThreadEntity in the database.
     * Room uses the primary key of the provided entity to find and update the corresponding row.
     * @param thread The ThreadEntity object to update.
     * @return The number of rows updated (usually 1 if successful).
     */
    @Update
    suspend fun updateThread(thread: ThreadEntity): Int // Added return type for clarity

    @Delete
    suspend fun deleteThread(thread: ThreadEntity)

    // --- New deletion queries ---

    /**
     * Deletes a specific thread by its ID.
     * @param threadId The ID of the thread to delete.
     * @return The number of rows deleted.
     */
    @Query("DELETE FROM threads WHERE threadId = :threadId")
    suspend fun deleteThreadById(threadId: String): Int

    /**
     * Deletes multiple threads based on a list of their IDs.
     * @param threadIds A list of thread IDs to delete.
     * @return The number of rows deleted.
     */
    @Query("DELETE FROM threads WHERE threadId IN (:threadIds)")
    suspend fun deleteThreadsByIds(threadIds: List<String>): Int

    /**
     * Deletes multiple ThreadEntity objects directly.
     * This method is useful if you already have the ThreadEntity objects you want to delete.
     * @param threads The list of ThreadEntity objects to delete.
     */
    @Delete
    suspend fun deleteMultipleThreads(threads: List<ThreadEntity>)

}