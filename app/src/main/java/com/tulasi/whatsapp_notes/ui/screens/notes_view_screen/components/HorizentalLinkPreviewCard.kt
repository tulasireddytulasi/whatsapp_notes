package com.tulasi.whatsapp_notes.ui.screens.notes_view_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tulasi.whatsapp_notes.ui.theme.NotesAppTheme


data class LinkPreviewData(
    val imageUrl: String?,
    val title: String,
    val description: String
)

@Composable
fun HorizontalLinkPreviewCard(
    imageUrl: String,
    title: String,
    description: String,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp) // 2sp padding (interpreted as 2dp for Composables)
            .clip(RoundedCornerShape(8.dp)) // Rounded corners for the whole widget
            .background(Color(0xFF333333)) // Dark background for the preview card
    ) {
        // Left side Image
        val imageModifier = Modifier
            .weight(0.2f) // 30% width
            .aspectRatio(1f) // Square shape (height = width)
            .padding(2.dp)
            .clip(RoundedCornerShape(4.dp)) // Rounded corners for the image
            .background(Color.Gray) // Placeholder background if image not loaded

        if (imageUrl.isNotEmpty()) {
            // In a real app, you'd use an image loading library like Coil or Glide here.
            // For this example, we'll use a placeholder drawable.
            // Coil Example:

            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = imageModifier
            )

            /*
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your placeholder
                contentDescription = "Link thumbnail",
                contentScale = ContentScale.Crop,
                modifier = imageModifier
            )
            */
        } else {
            // Placeholder if no image URL is provided
            Box(
                modifier = imageModifier
            ) {
                // You could put a default icon here
            }
        }

        // Right side Text Widgets
        Column(
            modifier = Modifier
                .weight(0.7f) // 70% width
                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Distribute space between text and icon
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2, // Usually 1 line if a close button is present
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(0.8f), // 80% width for text
                    lineHeight = 14.sp
                )
                IconButton(
                    onClick = onRemove, // Call the provided lambda
                    modifier = Modifier
                        .weight(0.2f) // 20% width for icon
                        .size(16.dp) // Smaller size for the icon button area
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove Link Preview",
                        tint = Color.LightGray, // Color for the close icon
                        modifier = Modifier.size(16.dp) // Actual icon size
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp)) // Space between title and description
            Text(
                text = description,
                fontSize = 10.sp,
                color = Color.LightGray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp
            )
        }
    }
}

// --- Preview ---
@Preview(showBackground = true)
@Composable
fun LinkPreviewWidgetPreview() {
    NotesAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            HorizontalLinkPreviewCard(
                imageUrl = "", // No image
                title = "Example Link Title",
                description = "This is a sample.",
                onRemove = {},
            )
            HorizontalLinkPreviewCard(
                imageUrl = "https://example.com/some_image.jpg", // Placeholder image in preview
                title = "Another Link with an Image and a Very Long Title That Should Wrap to Multiple Lines",
                description = "A longer effectively.",
                onRemove = {},
            )
        }
    }
}