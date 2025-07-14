package com.whatsapp_notes.ui.screens.create_edit_notes_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whatsapp_notes.ui.theme.DarkLighter
import com.whatsapp_notes.ui.theme.Primary

/**
 * A reusable composable that displays a list of FilterChips with single selection behavior.
 *
 * @param options The list of strings to be displayed as chip labels.
 * @param onSelectionChanged A callback function that is invoked when the selection changes,
 * providing the text of the newly selected option.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SingleSelectChips(
    modifier: Modifier = Modifier,
    options: List<String>,
    showLeadingIcon : Boolean = false,
    onSelectionChanged: (String) -> Unit,
) {
    // State to hold the currently selected option.
    // It's nullable initially, meaning no option is selected by default.
    var selectedOption by remember { mutableStateOf<String?>(null) }

    // FlowRow is used to arrange chips in a flexible row that wraps to the next line
    // when there's not enough horizontal space.
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp), // Spacing between chips horizontally
        verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between rows of chips vertically
    ) {
        // Iterate through each option to create a FilterChip
        options.forEach { option ->
            // Determine if the current chip is selected
            val isSelected = selectedOption == option

            FilterChip(
                selected = isSelected, // Set the selected state of the chip
                onClick = {
                    // When a chip is clicked, update the selected option
                    selectedOption = option
                    // Invoke the callback with the newly selected option
                    onSelectionChanged(option)
                },
                label = {
                    // The text label for the chip
                    Text(text = option)
                },
                shape = RoundedCornerShape(8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = DarkLighter,
                    selectedContainerColor = Primary,
                    labelColor = Color.LightGray,
                    selectedLabelColor = Color.White
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = Color.Gray,
                    selectedBorderColor = Primary,
                    borderWidth = 1.dp
                ),
                leadingIcon = if (showLeadingIcon && isSelected) {
                    // If the chip is selected, display a checkmark icon
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Selected",
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                } else {
                    null // No leading icon if not selected
                }
            )
        }
    }
}