package com.whatsapp_notes.data.repository

import com.whatsapp_notes.data.model.LinkPreview
import com.whatsapp_notes.data.model.Note
import com.whatsapp_notes.data.model.NotesModel
import com.whatsapp_notes.data.model.Thread

/**
 * A repository class for fetching note data.
 * Currently provides mock data to simulate API calls.
 * Simulates fetching a list of all notes.
 * @return A list of all [Note] objects.
 */

object NoteRepository {
    fun getFakeNotesData(): NotesModel {
        return NotesModel(
            notesList = listOf(
                Note(
                    noteId = "note1",
                    title = "Meeting Notes - Q4 Planning",
                    category = "Work",
                    timestamp = "2025-07-13T10:00:00Z",
                    isPinned = true,
                    colorStripHex = "#60A5FA",
                    threads = listOf(
                        Thread(
                            threadId = "ttt001",
                            content = "Discussed quarterly goals, budget allocation, and team restructuring. Need to follow up with Sarah about the marketing campaign timeline.",
                            timestamp = "2025-07-13T09:04:00Z",
                            linkPreview = LinkPreview(
                                imageUrl = "https://readdy.ai/api/search-image?query=productivity%20workspace%20with%20laptop%2C%20notebook%2C%20coffee%20cup%2C%20clean%20desk%20setup%2C%20modern%20office%20environment%2C%20natural%20lighting%2C%20professional%20atmosphere&width=300&height=160&seq=productivity1&orientation=landscape",
                                title = "The Ultimate Guide to Productivity",
                                description = "Discover proven strategies to boost your " +
                                        "productivity and achieve more in less time. Learn from experts and transform your daily routine.",
                                url = "https://www.dribbble.com"
                            )
                        ),
                        Thread(
                            threadId = "ttt003",
                            content = "Meeting notes from today's standup:\n• Sprint goals are on track ✅\n• Need to review API documentation\n• Schedule code review for Friday\n• Team lunch next Tuesday 🍕",
                            timestamp = "2025-07-13T08:15:00Z"
                        ),
                        Thread(
                            threadId = "ttt004",
                            content = "Random thought: What if we could build an app that automatically categorizes your thoughts based on mood and context? 🤔 Could use AI to detect patterns and suggest optimal times for creative work vs. administrative tasks.",
                            timestamp = "2025-07-13T06:50:00Z"
                        ),
                        Thread(
                            threadId = "ttt005",
                            content = "Grocery list for weekend:\n🥬 Organic spinach\n🥑 Avocados (3-4)\n🍌 Bananas\n🥛 Oat milk\n🍞 Sourdough bread\n🧀 Greek yogurt\n🥜 Mixed nuts",
                            timestamp = "2025-07-13T06:00:00Z"
                        ),
                        Thread(
                            threadId = "ttt006",
                            content = "Check out this amazing design inspiration I found! The use of whitespace and typography is incredible. https://dribbble.com/shots/design-inspiration",
                            timestamp = "2025-07-13T04:45:00Z",
                            linkPreview = LinkPreview(
                                imageUrl = "https://readdy.ai/api/search-image?query=modern%20UI%20design%20mockup%2C%20clean%20interface%2C%20mobile%20app%20screens%2C%20minimalist%20design%2C%20colorful%20gradients%2C%20professional%20presentation&width=300&height=160&seq=design1&orientation=landscape",
                                title = "Mobile App Design Inspiration",
                                description = "Beautiful collection of modern mobile app designs featuring clean layouts, vibrant colors, and intuitive user experiences.",
                                url = "https://dribbble.com/shots/design-inspiration"
                            )
                        ),
                        Thread(
                            threadId = "ttt007",
                            content = "Weekend plans:\nSaturday - Morning jog in the park 🏃‍♂️\nSaturday evening - Movie night with friends 🎬\nSunday - Brunch and museum visit 🎨\nSunday afternoon - Work on personal project 💻",
                            timestamp = "2025-07-12T12:50:00Z"
                        ),
                    )
                ),
                Note(
                    noteId = "note2",
                    title = "Weekend Trip Ideas",
                    category = "Personal",
                    timestamp = "2025-07-13T11:00:00Z",
                    isPinned = false,
                    colorStripHex = "#60A5FA",
                    threads = listOf(
                        Thread(
                            threadId = "ttt003",
                            content = "Mountain hiking in Colorado, beach house in Malibu, or city break in Portland. Check weather forecast and book accommodation.",
                            timestamp = "2025-07-13T11:05:00Z",
                            linkPreview = LinkPreview(
                                imageUrl = "https://image.com",
                                title = "Cool Design",
                                description = "Design inspiration",
                                url = "https://www.behance.net"
                            )
                        ),
                        Thread(
                            threadId = "ttt004",
                            content = "More ideas...",
                            timestamp = "2025-07-13T11:10:00Z",
                            linkPreview = LinkPreview(
                                imageUrl = "https://image.com",
                                title = "Inspo",
                                description = "Creative vibes",
                                url = "https://www.pinterest.com"
                            )
                        )
                    )
                ),
                Note(
                    noteId = "note3",
                    title = "App Feature Brainstorm",
                    category = "Personal",
                    timestamp = "2025-07-13T11:00:00Z",
                    isPinned = true,
                    colorStripHex = "#6EE7B7",
                    threads = listOf(
                        Thread(
                            threadId = "ttt001",
                            content = "Voice notes integration, collaborative editing, smart categorization with AI, offline sync improvements.",
                            timestamp = "2025-07-13T11:05:00Z",
                            linkPreview = LinkPreview(
                                imageUrl = "https://image.com",
                                title = "Cool Design",
                                description = "Design inspiration",
                                url = "https://www.behance.net"
                            )
                        ),
                    )
                ),
                Note(
                    noteId = "note4",
                    title = "Grocery Shopping List",
                    category = "Personal",
                    timestamp = "2025-07-13T11:12:00Z",
                    isPinned = true,
                    colorStripHex = "#EF4444",
                    threads = listOf(
                        Thread(
                            threadId = "ttt001",
                            content = "Organic vegetables, almond milk, whole grain bread, free-range eggs, Greek yogurt, fresh berries, quinoa, olive oil, and dark chocolate.",
                            timestamp = "2025-07-13T11:05:00Z",
                            linkPreview = LinkPreview(
                                imageUrl = "https://image.com",
                                title = "Cool Design",
                                description = "Design inspiration",
                                url = "https://www.behance.net"
                            )
                        ),
                    )
                ),
                Note(
                    noteId = "note5",
                    title = "Client Feedback Review",
                    category = "Work",
                    timestamp = "2025-07-13T11:12:00Z",
                    isPinned = false,
                    colorStripHex = "#60A5FA",
                    threads = listOf(
                        Thread(
                            threadId = "ttt001",
                            content = "Positive response to the new dashboard design. Requested minor adjustments to the color scheme and typography. Schedule follow-up meeting next week.",
                            timestamp = "2025-07-13T11:05:00Z",
                            linkPreview = LinkPreview(
                                imageUrl = "https://image.com",
                                title = "Cool Design",
                                description = "Design inspiration",
                                url = "https://www.behance.net"
                            )
                        ),
                    )
                ),
                Note(
                    noteId = "note6",
                    title = "Book Recommendations",
                    category = "Personal",
                    timestamp = "2025-07-12T16:12:00Z",
                    isPinned = false,
                    colorStripHex = "#60A5FA",
                    threads = listOf(
                        Thread(
                            threadId = "ttt001",
                            content = "Atomic Habits by James Clear, The Psychology of Money by Morgan Housel, Sapiens by Yuval Noah Harari. Start with the first one this weekend.",
                            timestamp = "2025-07-13T11:05:00Z",
                            linkPreview = LinkPreview(
                                imageUrl = "https://image.com",
                                title = "Cool Design",
                                description = "Design inspiration",
                                url = "https://www.behance.net"
                            )
                        ),
                    )
                ),
                Note(
                    noteId = "note7",
                    title = "Workout Routine Update",
                    category = "Personal",
                    timestamp = "2025-07-11T16:12:00Z",
                    isPinned = false,
                    colorStripHex = "#FB923C",
                    threads = listOf(
                        Thread(
                            threadId = "ttt001",
                            content = "Monday: Upper body strength, Tuesday: Cardio and core, Wednesday: Rest day, Thursday: Lower body, Friday: Full body circuit, Weekend: Yoga or hiking.",
                            timestamp = "2025-07-13T11:05:00Z",
                            linkPreview = null,
                        ),
                    )
                ),
                Note(
                    noteId = "note8",
                    title = "Investment Research Notes",
                    category = "Work",
                    timestamp = "2025-07-10T16:12:00Z",
                    isPinned = false,
                    colorStripHex = "#2DD4BF",
                    threads = listOf(
                        Thread(
                            threadId = "ttt001",
                            content = "Tech stocks showing strong growth potential. Consider diversifying portfolio with index funds. Research ESG investment options for long-term sustainability.",
                            timestamp = "2025-07-13T11:05:00Z",
                            linkPreview = null,
                        ),
                    )
                ),
                Note(
                    noteId = "note9",
                    title = "Birthday Party Planning",
                    category = "Personal",
                    timestamp = "2025-07-13T11:00:00Z",
                    isPinned = false,
                    colorStripHex = "#F472B6",
                    threads = listOf(
                        Thread(
                            threadId = "ttt001",
                            content = "Venue: Riverside Park pavilion, Date: March 15th, Guest list: 25 people, Menu: BBQ and vegetarian options, Activities: Live music and games.",
                            timestamp = "2025-07-13T11:05:00Z",
                            linkPreview = null,
                        ),
                    )
                ),
                Note(
                    noteId = "note10",
                    title = "Learning Goals 2024",
                    category = "Ideas",
                    timestamp = "2025-07-06T16:12:00Z",
                    isPinned = false,
                    colorStripHex = "#818CF8",
                    threads = listOf(
                        Thread(
                            threadId = "ttt001",
                            content = "Master React Native development, improve Spanish fluency, learn photography basics, complete online course in data science, practice public speaking.",
                            timestamp = "2025-07-13T11:05:00Z",
                            linkPreview = null,
                        ),
                    )
                ),
                Note(
                    noteId = "note11",
                    title = "Home Renovation Ideas",
                    category = "Personal",
                    timestamp = "2025-06-29T16:12:00Z",
                    isPinned = false,
                    colorStripHex = "#FACC15",
                    threads = listOf(
                        Thread(
                            threadId = "ttt001",
                            content = "Kitchen backsplash update, bathroom tile replacement, living room paint colors, garden landscaping, smart home automation installation.",
                            timestamp = "2025-07-13T11:05:00Z",
                            linkPreview = null,
                        ),
                    )
                ),
            )
        )
    }
}
