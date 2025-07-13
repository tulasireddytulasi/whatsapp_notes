package com.whatsapp_notes.ui.screens.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whatsapp_notes.data.model.Note
import com.whatsapp_notes.ui.theme.DarkDarker
import com.whatsapp_notes.ui.theme.DarkLighter
import com.whatsapp_notes.ui.theme.Gray300
import com.whatsapp_notes.ui.theme.Gray500
import com.whatsapp_notes.ui.theme.NotesAppTheme
import com.whatsapp_notes.ui.theme.Primary
import com.whatsapp_notes.ui.theme.Blue900
import com.whatsapp_notes.ui.theme.Blue300
import com.whatsapp_notes.ui.theme.Green900
import com.whatsapp_notes.ui.theme.Green300
import com.whatsapp_notes.ui.theme.Purple900
import com.whatsapp_notes.ui.theme.Purple300
import java.util.Locale

/**
 * Composable function for a single pinned note card.
 *
 * @param note The [Note] data object to display.
 * @param onClick Lambda to be invoked when the note card is clicked.
 */
@Composable
fun PinnedNoteCard(
    note: Note,
    onClick: (Note) -> Unit
) {
    Box(
        modifier = Modifier
            .width(256.dp) // min-w-64 equivalent (256px / 4 = 64dp)
            .clip(RoundedCornerShape(8.dp)) // rounded-md equivalent
            .background(DarkLighter)
            .border(1.dp, DarkDarker, RoundedCornerShape(8.dp)) // border border-dark-darker
            .clickable { onClick(note) }
            .padding(12.dp) // p-3 equivalent
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.title,
                    fontSize = 14.sp, // text-sm
                    fontWeight = FontWeight.SemiBold, // font-semibold
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f) // Allow text to take available space
                )
                Box(
                    modifier = Modifier.size(16.dp), // w-4 h-4
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Place, // ri-pushpin-fill
                        contentDescription = "Pinned",
                        tint = Primary, // text-primary
                        modifier = Modifier.size(12.dp) // ri-sm equivalent
                    )
                }
            }
            Text(
                text = note.threads.first().content,
                fontSize = 12.sp, // text-xs
                color = Gray300, // text-gray-300
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 8.dp) // mb-2 equivalent
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Tag
                val categoryBackgroundColor = when (note.category.lowercase(Locale.ROOT)) {
                    "work" -> Blue900
                    "personal" -> Green900
                    "ideas" -> Purple900
                    else -> DarkLighter // Default for unknown categories
                }
                val categoryTextColor = when (note.category.lowercase(Locale.ROOT)) {
                    "work" -> Blue300
                    "personal" -> Green300
                    "ideas" -> Purple300
                    else -> Gray300 // Default for unknown categories
                }

                Box(
                    modifier = Modifier
                        .background(categoryBackgroundColor, RoundedCornerShape(4.dp)) // rounded text-xs
                        .padding(horizontal = 8.dp, vertical = 2.dp) // px-2 py-0.5
                ) {
                    Text(
                        text = note.category,
                        fontSize = 10.sp, // text-xs
                        color = categoryTextColor
                    )
                }
                Text(
                    text = note.timestamp,
                    fontSize = 12.sp, // text-xs
                    color = Gray500 // text-gray-500
                )
            }
        }
    }
}

/**
 * Preview Composable for PinnedNoteCard.
 */
@Preview(showBackground = true)
@Composable
fun PinnedNoteCardPreview() {
    NotesAppTheme {
        PinnedNoteCard(
            note = Note(
                noteId = "1",
                title = "Meeting Notes - Q4 Planning",
                category = "Work",
                timestamp = "2 hours ago",
                isPinned = true,
                threads = emptyList(),
            ),
            onClick = {}
        )
    }
}
