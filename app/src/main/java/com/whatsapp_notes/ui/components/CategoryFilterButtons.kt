package com.whatsapp_notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whatsapp_notes.ui.theme.DarkLighter
import com.whatsapp_notes.ui.theme.Gray300
import com.whatsapp_notes.ui.theme.NotesAppTheme
import com.whatsapp_notes.ui.theme.Primary

/**
 * Composable function for displaying a horizontally scrollable row of category filter buttons.
 *
 * @param categories A list of strings representing the available categories.
 * @param selectedCategory The currently selected category string.
 * @param onCategorySelected Lambda to be invoked when a category button is clicked,
 * passing the new selected category.
 */
@Composable
fun CategoryFilterButtons(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp), // Space between buttons
        contentPadding = PaddingValues(horizontal = 16.dp) // Horizontal padding for the row itself
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory
            val backgroundColor = if (isSelected) Primary else DarkLighter
            val textColor = if (isSelected) Color.White else Gray300

            Box(
                modifier = Modifier
                    .background(backgroundColor, RoundedCornerShape(9999.dp)) // Rounded-full equivalent
                    .clickable { onCategorySelected(category) }
                    .padding(horizontal = 12.dp, vertical = 6.dp), // px-3 py-1.5 equivalent
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category,
                    color = textColor,
                    fontSize = 12.sp // text-xs equivalent
                )
            }
        }
    }
}

/**
 * Preview Composable for CategoryFilterButtons.
 * Shows how the category filter buttons will look in isolation.
 */
@Preview(showBackground = true)
@Composable
fun CategoryFilterButtonsPreview() {
    NotesAppTheme {
        val categories = listOf("All", "Work", "Personal", "Ideas", "Shopping", "Reminders")
        // In a preview, we can use a mutable state to simulate selection
        // In a real app, this state would typically come from a ViewModel
        val selectedCategory = "Work" // Example selected category for preview
        CategoryFilterButtons(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { /* Do nothing for preview */ }
        )
    }
}
