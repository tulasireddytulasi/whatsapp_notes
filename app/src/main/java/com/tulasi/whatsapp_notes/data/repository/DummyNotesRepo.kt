package com.tulasi.whatsapp_notes.data.repository

import com.tulasi.whatsapp_notes.data.local.entities.NoteEntity

/**
 * A repository class for fetching note data.
 * Currently provides mock data to simulate API calls.
 */
object DummyNotesRepo {
    fun getFakeNotesData(): List<NoteEntity> {
        return  listOf(
            NoteEntity(
                noteId = "note1",
                title = "Meeting Notes - Q4 Planning",
                category = "Work",
                timestamp = "2025-07-13T10:00:00Z",
                isPinned = true,
                colorStripHex = "#60A5FA",
            ),
            NoteEntity(
                noteId = "note2",
                title = "Weekend Trip Ideas",
                category = "Personal",
                timestamp = "2025-07-13T11:00:00Z",
                isPinned = false,
                colorStripHex = "#60A5FA",
            ),
            NoteEntity(
                noteId = "note3",
                title = "App Feature Brainstorm",
                category = "Personal",
                timestamp = "2025-07-13T11:00:00Z",
                isPinned = true,
                colorStripHex = "#6EE7B7",
            ),
            NoteEntity(
                noteId = "note4",
                title = "Grocery Shopping List",
                category = "Personal",
                timestamp = "2025-07-13T11:12:00Z",
                isPinned = true,
                colorStripHex = "#EF4444",
            ),
            NoteEntity(
                noteId = "note5",
                title = "Client Feedback Review",
                category = "Work",
                timestamp = "2025-07-13T11:12:00Z",
                isPinned = false,
                colorStripHex = "#60A5FA",
            ),
            NoteEntity(
                noteId = "note6",
                title = "Book Recommendations",
                category = "Personal",
                timestamp = "2025-07-12T16:12:00Z",
                isPinned = false,
                colorStripHex = "#60A5FA",
            ),
            NoteEntity(
                noteId = "note7",
                title = "Workout Routine Update",
                category = "Personal",
                timestamp = "2025-07-11T16:12:00Z",
                isPinned = false,
                colorStripHex = "#FB923C",
            ),
            NoteEntity(
                noteId = "note8",
                title = "Investment Research Notes",
                category = "Work",
                timestamp = "2025-07-10T16:12:00Z",
                isPinned = false,
                colorStripHex = "#2DD4BF",
            ),
            NoteEntity(
                noteId = "note9",
                title = "Birthday Party Planning",
                category = "Personal",
                timestamp = "2025-07-13T11:00:00Z",
                isPinned = false,
                colorStripHex = "#F472B6",
            ),
            NoteEntity(
                noteId = "note10",
                title = "Learning Goals 2024",
                category = "Ideas",
                timestamp = "2025-07-06T16:12:00Z",
                isPinned = false,
                colorStripHex = "#818CF8",
            ),
            NoteEntity(
                noteId = "note11",
                title = "Home Renovation Ideas",
                category = "Personal",
                timestamp = "2025-06-29T16:12:00Z",
                isPinned = false,
                colorStripHex = "#FACC15",
            ),
        )
    }

    /**
     * Simulates fetching a single note by its ID.
     * In a real application, this would fetch from a database or API.
     */
//    fun getNoteById(noteId: String): NoteEntity? {
//        return getFakeNotesData().notesList.find { it.noteId == noteId }
//    }
}