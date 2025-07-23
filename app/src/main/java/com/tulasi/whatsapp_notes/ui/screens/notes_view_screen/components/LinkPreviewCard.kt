package com.tulasi.whatsapp_notes.ui.screens.notes_view_screen.components

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tulasi.whatsapp_notes.data.model.LinkPreview

@Composable
fun LinkPreviewCard(
    preview: LinkPreview,
    modifier: Modifier = Modifier,
    onLinkClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onLinkClick(preview.url) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A)),
        border = BorderStroke(1.dp, Color(0xFF333333))
    ) {
        Column {
            AsyncImage(
                model = preview.imageUrl,
                contentDescription = "Link preview image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp) // Adjusted height for better visual based on HTML
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = preview.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = preview.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF9E9E9E), // gray-400 equivalent
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = preview.imageUrl.substringAfter("://").substringBefore("/"), // Extracting
                    // hostname
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64B5F6) // blue-400 equivalent
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LinkPreviewCardPreview() {
    val sampleLinkPreview = LinkPreview(
        imageUrl = "https://readdy.ai/api/search-image?query=productivity%20workspace%20with%20laptop%2C%20notebook%2C%20coffee%20cup%2C%20clean%20desk%20setup%2C%20modern%20office%20environment%2C%20natural%20lighting%2C%20professional%20atmosphere&width=300&height=160&seq=productivity1&orientation=landscape",
        title = "The Ultimate Guide to Productivity",
        description = "Discover proven strategies to boost your productivity and achieve more in less time. Learn from experts and transform your daily routine.",
        url = "https://example.com/productivity-guide"
    )
    LinkPreviewCard(preview = sampleLinkPreview) {}
}