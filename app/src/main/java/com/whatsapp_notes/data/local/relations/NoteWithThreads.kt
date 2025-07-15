package com.whatsapp_notes.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.whatsapp_notes.data.local.entities.NoteEntity
import com.whatsapp_notes.data.local.entities.ThreadEntity

data class NoteWithThreads(
    @Embedded
    val note: NoteEntity,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "noteOwnerId"
    )
    val threads: List<ThreadEntity>
)