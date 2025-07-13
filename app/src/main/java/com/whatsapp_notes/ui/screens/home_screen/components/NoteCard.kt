package com.whatsapp_notes.ui.screens.home_screen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width // Changed from widthIn(min = ...) to width()
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MailOutline
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
import com.whatsapp_notes.ui.theme.Blue300
import com.whatsapp_notes.ui.theme.Blue900
import com.whatsapp_notes.ui.theme.DarkDarker
import com.whatsapp_notes.ui.theme.DarkLighter
import com.whatsapp_notes.ui.theme.Gray300
import com.whatsapp_notes.ui.theme.Gray500
import com.whatsapp_notes.ui.theme.Green300
import com.whatsapp_notes.ui.theme.Green900
import com.whatsapp_notes.ui.theme.NotesAppTheme
import com.whatsapp_notes.ui.theme.Primary
import com.whatsapp_notes.ui.theme.Purple300
import com.whatsapp_notes.ui.theme.Purple900
import generateRandomHexColor
import generateRandomRainbowHexColor
import getRelativeTime
import java.util.Locale

/**
 * A reusable Composable function for displaying a note card.
 * Can be used for both pinned notes (fixed width) and all notes (fill width).
 *
 * @param note The [Note] data object to display.
 * @param cardModifier Modifier for the main Box container of the card, allows controlling width.
 * @param onClick Lambda to be invoked when the note card is clicked.
 * @param onDeleteClick Lambda to be invoked when the delete action is triggered (e.g., via swipe).
 * @param onArchiveClick Lambda to be invoked when the archive action is triggered (e.g., via swipe).
 */
@Composable
fun NoteCard(
    note: Note,
    @SuppressLint("ModifierParameter") cardModifier: Modifier = Modifier, // Modifier to control card width
    onClick: (Note) -> Unit,
    onDeleteClick: (Note) -> Unit = {}, // Default empty lambda for optional actions
    onArchiveClick: (Note) -> Unit = {}  // Default empty lambda for optional actions
) {
    Box(
        modifier = cardModifier
            .clip(RoundedCornerShape(8.dp)) // rounded-md equivalent
            .background(DarkLighter)
            .border(1.dp, DarkDarker, RoundedCornerShape(8.dp)) // border border-dark-darker
            .clickable { onClick(note) } // This is the main click handler for the card
            .height(IntrinsicSize.Min) // Allow children to define min height
    ) {
        // Swipe Actions (Visual placeholders for now, actual logic will be added later)
        // These are only relevant for 'All Notes', but included here for completeness
        // and will be controlled by their respective onClick lambdas.
        if (!note.isPinned) { // Only show swipe actions for non-pinned notes
            // Delete (Left Swipe) - positioned on the right, hidden by default
            Box(
                modifier = Modifier
                    .matchParentSize() // Occupy the same size as the parent Box
                    .background(Color.Red.copy(alpha = 0.6f)) // Example background for delete
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp), // Padding for the icon
                // Removed .clickable { onDeleteClick(note) } here
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Note",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Archive (Right Swipe) - positioned on the left, hidden by default
            Box(
                modifier = Modifier
                    .matchParentSize() // Occupy the same size as the parent Box
                    .background(Primary.copy(alpha = 0.6f)) // Example background for archive
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp), // Padding for the icon
                // Removed .clickable { onArchiveClick(note) } here
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = "Archive Note",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }


        // Main Note Content
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkLighter) // Ensure content is on top of swipe actions
                .padding(16.dp), // p-4 equivalent
            horizontalArrangement = Arrangement.spacedBy(12.dp), // gap-3 equivalent
            verticalAlignment = Alignment.Top // Align content to top
        ) {
            // Color Strip (only for All Notes)
            if (!note.isPinned && note.colorStripHex != null) {
                val colorStripColor = Color(android.graphics.Color.parseColor
                    (generateRandomRainbowHexColor()))
                Box(
                    modifier = Modifier
                        .width(4.dp) // width: 4px
                        .fillMaxHeight() // height: 100%
                        .clip(RoundedCornerShape(2.dp)) // border-radius: 2px
                        .background(colorStripColor)
                )
            }

            // Note Details
            Column(
                modifier = Modifier.weight(1f) // Fill remaining width
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = note.title,
                        fontSize = if (note.isPinned) 14.sp else 16.sp, // text-sm for pinned, text-base for all
                        fontWeight = FontWeight.SemiBold, // font-semibold
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f) // Allow text to take available space
                    )
                    // Pin Icon (only for Pinned Notes)
                    if (note.isPinned) {
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
                }
                Text(
                    text = note.threads.first().content,
                    fontSize = if (note.isPinned) 12.sp else 14.sp, // text-xs for pinned, text-sm for all
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
                    val categoryTextColor = when (note.category.lowercase(Locale.getDefault())) {
                        "work" -> Blue300
                        "personal" -> Green300
                        "ideas" -> Purple300
                        else -> Gray300 // Default for unknown categories
                    }

                    Box(
                        modifier = Modifier
                            .background(
                                categoryBackgroundColor,
                                RoundedCornerShape(4.dp)
                            ) // rounded text-xs
                            .padding(horizontal = 8.dp, vertical = 2.dp) // px-2 py-0.5
                    ) {
                        Text(
                            text = note.category,
                            fontSize = 10.sp, // text-xs
                            color = categoryTextColor
                        )
                    }
                    Text(
                        text = getRelativeTime(note.timestamp),
                        fontSize = 12.sp, // text-xs
                        color = Gray500 // text-gray-500
                    )
                }
            }
        }
    }
}

/**
 * Preview Composable for NoteCard (Pinned version).
 */
@Preview(name = "Pinned Note Card", showBackground = true)
@Composable
fun NoteCardPinnedPreview() {
    NotesAppTheme {
        NoteCard(
            note = Note(
                noteId = "1",
                title = "Meeting Notes - Q4 Planning",
                category = "Work",
                timestamp = "2 hours ago",
                isPinned = true,
                threads = emptyList(),
            ),
            cardModifier = Modifier.width(256.dp), // Fixed width for pinned
            onClick = {}
        )
    }
}

/**
 * Preview Composable for NoteCard (All Notes version).
 */
@Preview(name = "All Note Card", showBackground = true, widthDp = 360)
@Composable
fun NoteCardAllPreview() {
    NotesAppTheme {
        NoteCard(
            note = Note(
                noteId = "4",
                title = "Grocery Shopping List",
                category = "Personal",
                timestamp = "4 hours ago",
                isPinned = false,
                colorStripHex = "#EF4444",
                threads = emptyList(),
            ),
            cardModifier = Modifier.fillMaxWidth(), // Fill width for all notes
            onClick = {},
            onDeleteClick = {},
            onArchiveClick = {}
        )
    }
}
