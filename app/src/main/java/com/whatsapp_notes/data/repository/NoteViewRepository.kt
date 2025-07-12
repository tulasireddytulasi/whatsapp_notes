package com.whatsapp_notes.data.repository

import com.whatsapp_notes.data.model.LinkPreview
import com.whatsapp_notes.data.model.Message

object NoteViewRepository {

    /**
     * Simulates fetching a list of all messages or threads.
     * @return A list of [Message] objects that are marked as pinned.
     */
    fun getMessage(): List<Message> {
        return listOf(
            Message(
                id = "1",
                content = "Hey! Just wanted to share this amazing article I found about productivity techniques. It really changed my perspective on time management 🚀 https://example.com/productivity-guide",
                timestamp = "2:34 PM",
                linkPreview = LinkPreview(
                    imageUrl = "https://readdy.ai/api/search-image?query=productivity%20workspace%20with%20laptop%2C%20notebook%2C%20coffee%20cup%2C%20clean%20desk%20setup%2C%20modern%20office%20environment%2C%20natural%20lighting%2C%20professional%20atmosphere&width=300&height=160&seq=productivity1&orientation=landscape",
                    title = "The Ultimate Guide to Productivity",
                    description = "Discover proven strategies to boost your productivity and achieve more in less time. Learn from experts and transform your daily routine.",
                    url = "https://example.com/productivity-guide"
                )
            ),
            Message(
                id = "2",
                content = "Meeting notes from today's standup:\n• Sprint goals are on track ✅\n• Need to review API documentation\n• Schedule code review for Friday\n• Team lunch next Tuesday 🍕",
                timestamp = "1:45 PM"
            ),
            Message(
                id = "3",
                content = "Random thought: What if we could build an app that automatically categorizes your thoughts based on mood and context? 🤔 Could use AI to detect patterns and suggest optimal times for creative work vs. administrative tasks.",
                timestamp = "12:20 PM"
            ),
            Message(
                id = "4",
                content = "Grocery list for weekend:\n🥬 Organic spinach\n🥑 Avocados (3-4)\n🍌 Bananas\n🥛 Oat milk\n🍞 Sourdough bread\n🧀 Greek yogurt\n🥜 Mixed nuts",
                timestamp = "11:30 AM"
            ),
            Message(
                id = "5",
                content = "Check out this amazing design inspiration I found! The use of whitespace and typography is incredible. https://dribbble.com/shots/design-inspiration",
                timestamp = "10:15 AM",
                linkPreview = LinkPreview(
                    imageUrl = "https://readdy.ai/api/search-image?query=modern%20UI%20design%20mockup%2C%20clean%20interface%2C%20mobile%20app%20screens%2C%20minimalist%20design%2C%20colorful%20gradients%2C%20professional%20presentation&width=300&height=160&seq=design1&orientation=landscape",
                    title = "Mobile App Design Inspiration",
                    description = "Beautiful collection of modern mobile app designs featuring clean layouts, vibrant colors, and intuitive user experiences.",
                    url = "https://dribbble.com/shots/design-inspiration"
                )
            ),
            Message(
                id = "6",
                content = "Weekend plans:\nSaturday - Morning jog in the park 🏃‍♂️\nSaturday evening - Movie night with friends 🎬\nSunday - Brunch and museum visit 🎨\nSunday afternoon - Work on personal project 💻",
                timestamp = "Yesterday 6:20 PM"
            )
        )
    }
}