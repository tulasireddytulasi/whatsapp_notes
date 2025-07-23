package com.tulasi.whatsapp_notes.ui.screens.settings_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "WhatsApp Note v2.1.0",
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
        SettingsScreen()
    }
}