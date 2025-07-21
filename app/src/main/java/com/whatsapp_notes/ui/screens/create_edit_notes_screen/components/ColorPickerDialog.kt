package com.whatsapp_notes.ui.screens.create_edit_notes_screen.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController


/**
 * Reusable Composable for picking a color using HsvColorPicker in a dialog.
 *
 * @param showDialog Controls the visibility of the dialog.
 * @param onDismissRequest Lambda to be invoked when the dialog is dismissed (e.g., by clicking outside).
 * @param onColorSelected Lambda to be invoked when a color is confirmed. Provides the selected Color.
 * @param initialColor The initial color to display in the color picker.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onColorSelected: (String) -> Unit,
    initialColor: String = "#FFFFFF" // Default initial color
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            // State to hold the color currently being picked within the dialog
            var dialogPickedColor by remember { mutableStateOf(initialColor) }
            val controller = rememberColorPickerController()

            // Card to give the dialog a nice elevated look and white background
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    // Controller for the HsvColorPicker
                    AlphaTile(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        controller = controller
                    )

                    // HSV Color Picker from the library
                    HsvColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        controller = controller,
                        onColorChanged = {
                            Log.d("Color", "#${it.hexCode}")
                            dialogPickedColor = "#${it.hexCode}"
                        }
                    )

                    AlphaSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(35.dp),
                        controller = controller,
                        tileOddColor = Color.White,
                        tileEvenColor = Color.Black
                    )
                    BrightnessSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(35.dp),
                        controller = controller,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Button to confirm the color selection and dismiss the dialog
                    Button(
                        onClick = {
                            onColorSelected(dialogPickedColor) // Pass the picked color back
                            // onDismissRequest() // onColorSelected will handle dismissing the dialog
                        },
                        modifier = Modifier.fillMaxWidth(0.6f)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

// Preview for the standalone ColorPickerDialog
@Preview(showBackground = true)
@Composable
fun PreviewColorPickerDialog() {
    MaterialTheme {
        ColorPickerDialog(
            showDialog = true,
            onDismissRequest = { /* Do nothing for preview */ },
            onColorSelected = { /* Do nothing for preview */ },
            initialColor = "#FFFFFF"
        )
    }
}
