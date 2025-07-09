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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.whatsapp_notes.ui.screens.HomeScreen // Import HomeScreen
import com.whatsapp_notes.ui.screens.NoteViewScreen // Import NoteViewScreen
import com.whatsapp_notes.ui.theme.NotesAppTheme // Import your custom theme

/**
 * Object to hold navigation routes.
 */
object Routes {
    const val HOME_SCREEN = "home_screen"
    const val NOTE_VIEW_SCREEN = "note_view_screen/{noteId}" // Route with argument
    const val NOTE_ID_ARG = "noteId" // Argument key
}

/**
 * The main activity for the Notes application.
 * This activity serves as the entry point and hosts the Jetpack Compose UI.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController() // Create a NavController

                    NavHost(
                        navController = navController,
                        startDestination = Routes.HOME_SCREEN // Set the starting screen
                    ) {
                        // Define the route for the Home Screen
                        composable(Routes.HOME_SCREEN) {
                            HomeScreen(navController = navController) // Pass navController to HomeScreen
                        }
                        // Define the route for the Note View Screen with a noteId argument
                        composable(
                            route = Routes.NOTE_VIEW_SCREEN,
                            arguments = listOf(navArgument(Routes.NOTE_ID_ARG) {
                                type = NavType.StringType // Define argument type
                                nullable = true // Allow null for initial testing or if noteId is optional
                            })
                        ) { backStackEntry ->
                            val noteId = backStackEntry.arguments?.getString(Routes.NOTE_ID_ARG)
                            NoteViewScreen(noteId = noteId)
                        }
                    }
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
        // For preview, we can just show the HomeScreen directly
        // In a real app, you might mock NavController if needed for complex previews
        HomeScreen(navController = rememberNavController())
    }
}
