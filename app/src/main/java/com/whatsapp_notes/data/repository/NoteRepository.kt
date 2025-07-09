package com.whatsapp_notes.data.repository

import com.whatsapp_notes.data.model.Note

/**
 * A repository class for fetching note data.
 * Currently provides mock data to simulate API calls.
 */
object NoteRepository {

    /**
     * Simulates fetching a list of pinned notes.
     * @return A list of [Note] objects that are marked as pinned.
     */
    fun getPinnedNotes(): List<Note> {
        return listOf(
            Note(
                id = "1",
                title = "Meeting Notes - Q4 Planning",
                content = "Discussed quarterly goals, budget allocation, and team restructuring. Need to follow up with Sarah about the marketing campaign timeline.",
                category = "Work",
                timestamp = "2 hours ago",
                isPinned = true,
                colorStripHex = "#60A5FA" // Blue-400 for work
            ),
            Note(
                id = "2",
                title = "Weekend Trip Ideas",
                content = "Mountain hiking in Colorado, beach house in Malibu, or city break in Portland. Check weather forecast and book accommodation.",
                category = "Personal",
                timestamp = "Yesterday",
                isPinned = true,
                colorStripHex = "#6EE7B7" // Green-300 for personal
            ),
            Note(
                id = "3",
                title = "App Feature Brainstorm",
                content = "Voice notes integration, collaborative editing, smart categorization with AI, offline sync improvements.",
                category = "Ideas",
                timestamp = "3 days ago",
                isPinned = true,
                colorStripHex = "#A78BFA" // Purple-400 for ideas
            )
        )
    }

    /**
     * Simulates fetching a list of all notes.
     * @return A list of all [Note] objects.
     */
    fun getAllNotes(): List<Note> {
        return listOf(
            Note(
                id = "4",
                title = "Grocery Shopping List",
                content = "Organic vegetables, almond milk, whole grain bread, free-range eggs, Greek yogurt, fresh berries, quinoa, olive oil, and dark chocolate.",
                category = "Personal",
                timestamp = "4 hours ago",
                isPinned = false,
                colorStripHex = "#EF4444" // Red-400
            ),
            Note(
                id = "5",
                title = "Client Feedback Review",
                content = "Positive response to the new dashboard design. Requested minor adjustments to the color scheme and typography. Schedule follow-up meeting next week.",
                category = "Work",
                timestamp = "6 hours ago",
                isPinned = false,
                colorStripHex = "#60A5FA" // Blue-400
            ),
            Note(
                id = "6",
                title = "Book Recommendations",
                content = "Atomic Habits by James Clear, The Psychology of Money by Morgan Housel, Sapiens by Yuval Noah Harari. Start with the first one this weekend.",
                category = "Personal",
                timestamp = "Yesterday",
                isPinned = false,
                colorStripHex = "#A78BFA" // Purple-400
            ),
            Note(
                id = "7",
                title = "Workout Routine Update",
                content = "Monday: Upper body strength, Tuesday: Cardio and core, Wednesday: Rest day, Thursday: Lower body, Friday: Full body circuit, Weekend: Yoga or hiking.",
                category = "Personal",
                timestamp = "2 days ago",
                isPinned = false,
                colorStripHex = "#FB923C" // Orange-400
            ),
            Note(
                id = "8",
                title = "Investment Research Notes",
                content = "Tech stocks showing strong growth potential. Consider diversifying portfolio with index funds. Research ESG investment options for long-term sustainability.",
                category = "Work",
                timestamp = "3 days ago",
                isPinned = false,
                colorStripHex = "#2DD4BF" // Teal-400
            ),
            Note(
                id = "9",
                title = "Birthday Party Planning",
                content = "Venue: Riverside Park pavilion, Date: March 15th, Guest list: 25 people, Menu: BBQ and vegetarian options, Activities: Live music and games.",
                category = "Personal",
                timestamp = "4 days ago",
                isPinned = false,
                colorStripHex = "#F472B6" // Pink-400
            ),
            Note(
                id = "10",
                title = "Learning Goals 2024",
                content = "Master React Native development, improve Spanish fluency, learn photography basics, complete online course in data science, practice public speaking.",
                category = "Ideas",
                timestamp = "1 week ago",
                isPinned = false,
                colorStripHex = "#818CF8" // Indigo-400
            ),
            Note(
                id = "11",
                title = "Home Renovation Ideas",
                content = "Kitchen backsplash update, bathroom tile replacement, living room paint colors, garden landscaping, smart home automation installation.",
                category = "Personal",
                timestamp = "1 week ago",
                isPinned = false,
                colorStripHex = "#FACC15" // Yellow-400
            )
        )
    }
}
