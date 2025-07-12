package com.whatsapp_notes.ui.screens.notes_view_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whatsapp_notes.data.model.Message
import com.whatsapp_notes.data.model.LinkPreview

@Composable
fun MessageBubble(
    message: Message,
    modifier: Modifier = Modifier,
    onLinkClick: (String) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { /* TODO: Implement on long press/context menu later */ },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A)),
        border = BorderStroke(1.dp, Color(0xFF333333))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            Text(
                text = message.content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
            )

            message.linkPreview?.let { preview ->
                Spacer(modifier = Modifier.height(8.dp))
                LinkPreviewCard(preview = preview, onLinkClick = onLinkClick)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Pushes children to opposite ends
            ) {
                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF9E9E9E), // Equivalent to gray-500 in dark theme
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageBubblePreview() {
    val sampleMessageWithLink = Message(
        id = "1",
        content = "Hey! Just wanted to share this amazing article I found about productivity techniques. It really changed my perspective on time management 🚀 https://example.com/productivity-guide",
        timestamp = "2:34 PM",
        linkPreview = LinkPreview(
            imageUrl = "https://readdy.ai/api/search-image?query=productivity%20workspace%20with%20laptop%2C%20notebook%2C%20coffee%20cup%2C%20clean%20desk%20setup%2C%20modern%20office%20environment%2C%20natural%20lighting%2C%20professional%20atmosphere&width=300&height=160&seq=productivity1&orientation=landscape",
            title = "The Ultimate Guide to Productivity",
            description = "Discover proven strategies to boost your productivity and achieve more in less time. Learn from experts and transform your daily routine.",
            url = "https://example.com/productivity-guide"
        )
    )

    val sampleMessageWithoutLink = Message(
        id = "2",
        content = "Meeting notes from today's standup:\n• Sprint goals are on track ✅\n• Need to review API documentation\n• Schedule code review for Friday\n• Team lunch next Tuesday 🍕",
        timestamp = "1:45 PM"
    )

    Column(modifier = Modifier.padding(16.dp)) {
        MessageBubble(message = sampleMessageWithLink)
        Spacer(modifier = Modifier.height(16.dp))
        MessageBubble(message = sampleMessageWithoutLink)
    }
}