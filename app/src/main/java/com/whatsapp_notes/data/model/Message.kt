package com.whatsapp_notes.data.model

data class Message(
    val id: String,
    val content: String,
    val timestamp: String,
    val linkPreview: LinkPreview? = null // Nullable to indicate if a link preview exists
)

data class LinkPreview(
    val imageUrl: String,
    val title: String,
    val description: String,
    val url: String
)