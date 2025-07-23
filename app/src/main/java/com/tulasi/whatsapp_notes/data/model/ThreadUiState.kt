package com.tulasi.whatsapp_notes.data.model

import com.tulasi.whatsapp_notes.data.local.entities.ThreadEntity

data class ThreadUiState(
    val thread: ThreadEntity,
    var isSelected: Boolean = false
)