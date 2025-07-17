package com.whatsapp_notes.ui.screens.notes_view_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.whatsapp_notes.data.local.entities.ThreadEntity
import com.whatsapp_notes.data.model.LinkPreview
import com.whatsapp_notes.data.model.ThreadUiState
import getRelativeTime

@OptIn(ExperimentalFoundationApi::class) // Opt-in for combinedClickable
@Composable
fun MessageBubble(
    threadUiState: ThreadUiState, // Change to ThreadUiState
    modifier: Modifier = Modifier,
    onLinkClick: (String) -> Unit = {},
    onLongPress: (String) -> Unit, // Callback for long press
    onClick: (String) -> Unit // Callback for regular click
) {
    val thread = threadUiState.thread
    val isSelected = threadUiState.isSelected

    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable( // Use combinedClickable
                onClick = { onClick(thread.threadId) },
                onLongClick = { onLongPress(thread.threadId) }
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF4A4A4A) else Color(0xFF2A2A2A) // Change color if selected
        ),
        border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFF333333)) // Change border if selected
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            Text(
                text = thread.content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
            )

            thread.imageUrl?.let {
                val preview =  LinkPreview(
                    title = thread.linkTitle!!,
                    description = thread.description!!,
                    imageUrl = thread.imageUrl,
                    url = thread.imageUrl,
                )
                if(preview.imageUrl.isNotEmpty())  Spacer(modifier = Modifier.height(8.dp))
                if(preview.imageUrl.isNotEmpty())  LinkPreviewCard(preview = preview, onLinkClick = onLinkClick)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = getRelativeTime(thread.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF9E9E9E),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageBubblePreview() {
    val sampleMessageWithLink = ThreadEntity(
        threadId = "1",
        noteOwnerId = "",
        content = "Hey! Just wanted to share this amazing article I found about productivity techniques. It really changed my perspective on time management 🚀 https://example.com/productivity-guide",
        timestamp = "2:34 PM",
        imageUrl = "https://readdy.ai/api/search-image?query=productivity%20workspace%20with%20laptop%2C%20notebook%2C%20coffee%20cup%2C%20clean%20desk%20setup%2C%20modern%20office%20environment%2C%20natural%20lighting%2C%20professional%20atmosphere&width=300&height=160&seq=productivity1&orientation=landscape",
        linkTitle = "The Ultimate Guide to Productivity",
        description = "Discover proven strategies to boost your productivity and achieve more in less time. Learn from experts and transform your daily routine.",
    )

    val sampleMessageWithoutLink = ThreadEntity(
        threadId = "2",
        noteOwnerId = "",
        content = "Meeting notes from today's 🍕",
        timestamp = "1:45 PM",
        imageUrl = null,
        description = null,
        linkTitle = null
    )

    Column(modifier = Modifier.padding(16.dp)) {
        MessageBubble(threadUiState = ThreadUiState(sampleMessageWithLink, isSelected = false), onLongPress = {}, onClick = {})
        Spacer(modifier = Modifier.height(16.dp))
        MessageBubble(threadUiState = ThreadUiState(sampleMessageWithoutLink, isSelected = true), onLongPress = {}, onClick = {})
    }
}