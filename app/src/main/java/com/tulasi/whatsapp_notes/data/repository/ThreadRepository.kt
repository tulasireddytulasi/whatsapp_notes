package com.tulasi.whatsapp_notes.data.repository

import com.tulasi.whatsapp_notes.data.local.entities.ThreadEntity

/**
 * A repository class for fetching thread data.
 * Currently provides mock data to simulate API calls.
 */
object ThreadRepository {
    private val allThreads = listOf(
        ThreadEntity(
            threadId = "ttt001",
            noteOwnerId = "note1",
            content = "Discussed quarterly goals, budget allocation, and team restructuring. Need to follow up with Sarah about the marketing campaign timeline.",
            timestamp = "2025-07-13T09:04:00Z",
            imageUrl = "https://readdy.ai/api/search-image?query=productivity%20workspace%20with%20laptop%2C%20notebook%2C%20coffee%20cup%2C%20clean%20desk%20setup%2C%20modern%20office%20environment%2C%20natural%20lighting%2C%20professional%20atmosphere&width=300&height=160&seq=productivity1&orientation=landscape",
            linkTitle = "The Ultimate Guide to Productivity",
            description = "Discover proven strategies to boost your productivity and achieve more in less time. Learn from experts and transform your daily routine.",
        ),
        ThreadEntity(
            threadId = "ttt003",
            noteOwnerId = "note1",
            content = "Meeting notes from today's standup:\n• Sprint goals are on track ✅\n• Need to review API documentation\n• Schedule code review for Friday\n• Team lunch next Tuesday 🍕",
            timestamp = "2025-07-13T08:15:00Z",
            imageUrl = null,
            linkTitle = null,
            description = null
        ),
        ThreadEntity(
            threadId = "ttt004",
            noteOwnerId = "note1",
            content = "Random thought: What if we could build an app that automatically categorizes your thoughts based on mood and context? 🤔 Could use AI to detect patterns and suggest optimal times for creative work vs. administrative tasks.",
            timestamp = "2025-07-13T06:50:00Z",
            imageUrl = null,
            linkTitle = null,
            description = null
        ),
        ThreadEntity(
            threadId = "ttt005",
            noteOwnerId = "note1",
            content = "Grocery list for weekend:\n🥬 Organic spinach\n🥑 Avocados (3-4)\n🍌 Bananas\n🥛 Oat milk\n🍞 Sourdough bread\n🧀 Greek yogurt\n🥜 Mixed nuts",
            timestamp = "2025-07-13T06:00:00Z",
            imageUrl = null,
            linkTitle = null,
            description = null
        ),
        ThreadEntity(
            threadId = "ttt006",
            noteOwnerId = "note1",
            content = "Check out this amazing design inspiration I found! The use of whitespace and typography is incredible. https://dribbble.com/shots/design-inspiration",
            timestamp = "2025-07-13T04:45:00Z",
            imageUrl = "https://readdy.ai/api/search-image?query=modern%20UI%20design%20mockup%2C%20clean%20interface%2C%20mobile%20app%20screens%2C%20minimalist%20design%2C%20colorful%20gradients%2C%20professional%20presentation&width=300&height=160&seq=design1&orientation=landscape",
            linkTitle = "Mobile App Design Inspiration",
            description = "Beautiful collection of modern mobile app designs featuring clean layouts, vibrant colors, and intuitive user experiences.",
        ),
        ThreadEntity(
            threadId = "ttt007",
            noteOwnerId = "note1",
            content = "Weekend plans:\nSaturday - Morning jog in the park 🏃‍♂️\nSaturday evening - Movie night with friends 🎬\nSunday - Brunch and museum visit 🎨\nSunday afternoon - Work on personal project 💻",
            timestamp = "2025-07-12T12:50:00Z",
            imageUrl = null,
            linkTitle = null,
            description = null
        ),
        // Threads for Note 2
        ThreadEntity(
            threadId = "thread2_1",
            noteOwnerId = "note2",
            content = "Mountain hiking in Colorado, beach house in Malibu, or city break in Portland. Check weather forecast and book accommodation.",
            timestamp = "2025-07-13T11:05:00Z",
            imageUrl = "https://image.com",
            linkTitle = "Cool Design",
            description = "Design inspiration",
        ),
        ThreadEntity(
            threadId = "thread2_2",
            noteOwnerId = "note2",
            content = "More ideas...",
            timestamp = "2025-07-13T11:10:00Z",
            imageUrl = "https://image.com",
            linkTitle = "Inspo",
            description = "Creative vibes",
        ),
        // Threads for Note 3
        ThreadEntity(
            threadId = "thread3_1",
            noteOwnerId = "note3",
            content = "Voice notes integration, collaborative editing, smart categorization with AI, offline sync improvements.",
            timestamp = "2025-07-13T11:05:00Z",
            imageUrl = "https://image.com",
            linkTitle = "Cool Design",
            description = "Design inspiration",
        ),
        // Threads for Note 4
        ThreadEntity(
            threadId = "thread4_1",
            noteOwnerId = "note4",
            content = "Organic vegetables, almond milk, whole grain bread, free-range eggs, Greek yogurt, fresh berries, quinoa, olive oil, and dark chocolate.",
            timestamp = "2025-07-13T11:05:00Z",
            imageUrl = "https://image.com",
            linkTitle = "Cool Design",
            description = "Design inspiration",
        ),
        // Threads for Note 5
        ThreadEntity(
            threadId = "thread5_1",
            noteOwnerId = "note5",
            content = "Positive response to the new dashboard design. Requested minor adjustments to the color scheme and typography. Schedule follow-up meeting next week.",
            timestamp = "2025-07-13T11:05:00Z",
            imageUrl = "https://image.com",
            linkTitle = "Cool Design",
            description = "Design inspiration",
        ),
        // Threads for Note 6
        ThreadEntity(
            threadId = "thread6_1",
            noteOwnerId = "note6",
            content = "Atomic Habits by James Clear, The Psychology of Money by Morgan Housel, Sapiens by Yuval Noah Harari. Start with the first one this weekend.",
            timestamp = "2025-07-13T11:05:00Z",
            imageUrl = "https://image.com",
            linkTitle = "Cool Design",
            description = "Design inspiration",
        ),
        // Threads for Note 7
        ThreadEntity(
            threadId = "thread7_1",
            noteOwnerId = "note7",
            content = "Monday: Upper body strength, Tuesday: Cardio and core, Wednesday: Rest day, Thursday: Lower body, Friday: Full body circuit, Weekend: Yoga or hiking.",
            timestamp = "2025-07-13T11:05:00Z",
            imageUrl = null,
            linkTitle = null,
            description = null,
        ),
        // Threads for Note 8
        ThreadEntity(
            threadId = "thread8_1",
            noteOwnerId = "note8",
            content = "Tech stocks showing strong growth potential. Consider diversifying portfolio with index funds. Research ESG investment options for long-term sustainability.",
            timestamp = "2025-07-13T11:05:00Z",
            imageUrl = null,
            linkTitle = null,
            description = null,
        ),
        // Threads for Note 9
        ThreadEntity(
            threadId = "thread9_1",
            noteOwnerId = "note9",
            content = "Venue: Riverside Park pavilion, Date: March 15th, Guest list: 25 people, Menu: BBQ and vegetarian options, Activities: Live music and games.",
            timestamp = "2025-07-13T11:05:00Z",
            imageUrl = null,
            linkTitle = null,
            description = null,
        ),
        // Threads for Note 10
        ThreadEntity(
            threadId = "thread10_1",
            noteOwnerId = "note10",
            content = "Master React Native development, improve Spanish fluency, learn photography basics, complete online course in data science, practice public speaking.",
            timestamp = "2025-07-13T11:05:00Z",
            imageUrl = null,
            linkTitle = null,
            description = null,
        ),
        // Threads for Note 11
        ThreadEntity(
            threadId = "thread11_1",
            noteOwnerId = "note11",
            content = "Kitchen backsplash update, bathroom tile replacement, living room paint colors, garden landscaping, smart home automation installation.",
            timestamp = "2025-07-13T11:05:00Z",
            imageUrl = null,
            linkTitle = null,
            description = null,
        ),
    )

    /**
     * Returns a list of all fake thread data.
     * In a real application, this would fetch from a database or API.
     */
    fun getAllThreads(): List<ThreadEntity> {
        return allThreads
    }

    /**
     * Simulates fetching threads for a specific note.
     * @param noteId The ID of the note for which to retrieve threads.
     * @return A list of [ThreadEntity] objects associated with the given note ID.
     */
    fun getThreadsForNote(noteId: String): List<ThreadEntity> {
        return allThreads.filter { it.noteOwnerId == noteId }
    }
}