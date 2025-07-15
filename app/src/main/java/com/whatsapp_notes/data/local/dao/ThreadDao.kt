package com.whatsapp_notes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.whatsapp_notes.data.local.entities.ThreadEntity

@Dao
interface ThreadDao {
    @Query("SELECT * FROM threads WHERE noteOwnerId = :noteId")
    suspend fun getThreadsForNote(noteId: String): List<ThreadEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThreads(threads: List<ThreadEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThread(thread: ThreadEntity)

    @Update
    suspend fun updateThread(thread: ThreadEntity)

    @Delete
    suspend fun deleteThread(thread: ThreadEntity)
}