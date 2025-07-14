package com.whatsapp_notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whatsapp_notes.ui.screens.create_edit_notes_screen.CreateEditNoteScreen
import com.whatsapp_notes.ui.screens.home_screen.HomeScreen // Import HomeScreen
import com.whatsapp_notes.ui.screens.notes_view_screen.NoteViewScreen // Import NoteViewScreen
import com.whatsapp_notes.ui.theme.NotesAppTheme // Import your custom theme

/**
 * Object to hold navigation routes.
 */
object Routes {
    const val HOME_SCREEN = "home_screen"
    const val NOTE_VIEW_SCREEN = "note_view_screen/{noteId}" // Route with argument
    const val NOTE_ID_ARG = "noteId" // Argument key
    const val CREATE_EDIT_SCREEN = "create_edit_screen"
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
                    val animDurationMS = 400

                    NavHost(
                        navController = navController,
                        startDestination = Routes.HOME_SCREEN // Set the starting screen
                    ) {
                        // Define the route for the Home Screen
                        composable(
                            Routes.HOME_SCREEN,
                            exitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    tween(animDurationMS)
                                )
                            },
                            popEnterTransition = {
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right,
                                    tween(animDurationMS)
                                )
                            },
                        ) {
                            // Pass navController to HomeScreen
                            HomeScreen(navController = navController)
                        }
                        // Define the route for the Note View Screen with a noteId argument
                        composable(
                            route = Routes.NOTE_VIEW_SCREEN,
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    tween(animDurationMS)
                                )
                            },
                            popExitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection
                                        .Right,
                                    tween(animDurationMS)
                                )
                            },
//                            arguments = listOf(navArgument(Routes.NOTE_ID_ARG) {
//                                type = NavType.StringType // Define argument type
//                                nullable = true // Allow null for initial testing or if noteId is optional
//                            })
                        ) {
//                            val noteId = backStackEntry.arguments?.getString(Routes.NOTE_ID_ARG)
                            // params: noteId = noteId
                            NoteViewScreen(navController = navController)
                        }

                        // Create or Edit Screen
                        composable(
                            Routes.CREATE_EDIT_SCREEN,
                            exitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    tween(animDurationMS)
                                )
                            },
                            popEnterTransition = {
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right,
                                    tween(animDurationMS)
                                )
                            },
                        ) {
                            // Pass navController to HomeScreen
                            CreateEditNoteScreen(navController = navController)
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
