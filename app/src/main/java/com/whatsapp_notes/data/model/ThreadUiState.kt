package com.whatsapp_notes.data.model

import com.whatsapp_notes.data.local.entities.ThreadEntity

data class ThreadUiState(
    val thread: ThreadEntity,
    var isSelected: Boolean = false
)