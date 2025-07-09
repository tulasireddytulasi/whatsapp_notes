package com.whatsapp_notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whatsapp_notes.ui.screens.HomeScreen // Import the HomeScreen Composable
import com.whatsapp_notes.ui.theme.NotesAppTheme // Import your custom theme

/**
 * The main activity for the Notes application.
 * This activity serves as the entry point and hosts the Jetpack Compose UI.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Apply the custom NotesAppTheme to the entire application UI
            NotesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), // Make the surface fill the entire screen
                    color = MaterialTheme.colorScheme.background // Use the background color defined in your theme
                ) {
                    // Display the HomeScreen Composable
                    HomeScreen()
                }
            }
        }
    }
}

/**
 * Preview Composable for the MainActivity.
 * This allows you to see a preview of the HomeScreen directly in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotesAppTheme {
        HomeScreen()
    }
}
