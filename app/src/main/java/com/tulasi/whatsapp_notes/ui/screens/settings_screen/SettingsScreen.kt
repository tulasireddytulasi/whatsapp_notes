package com.tulasi.whatsapp_notes.ui.screens.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp) // Fixed height similar to HTML header
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start // Distributes space between title and icon
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp) // Fixed size for icon container
                        .clickable(onClick = {navController.popBackStack()}), // Make the icon clickable
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, // Using a default Material Design icon
                        // for now
                        contentDescription = "User Profile",
                        tint = Color(0xFFD1D5DB) // Equivalent to gray-300 in Tailwind
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                // App Title
                Text(
                    text = "Notes Settings",
                    fontSize = 20.sp, // Similar to h1 in HTML
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Text color from HTML
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "WhatsApp Notes v0.0.1",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground // Or a specific color if needed
            )
            Text(
                text = "Built with care for your thoughts",
                fontSize = 16.sp,
                color = Color.LightGray // Explicitly setting light grey
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWhatsAppNoteSplashScreen() {
    // You might want to wrap this in your app's theme if you have one
    MaterialTheme {
        SettingsScreen(navController = rememberNavController())
    }
}