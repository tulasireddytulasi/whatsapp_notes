package com.whatsapp_notes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CreateEditNoteViewModel : ViewModel() {

    // State to determine if the screen is in edit mode
    // In a real app, this would likely come from navigation arguments or a loaded note
    var isEditMode by mutableStateOf(false)
        private set

    // Page title, dynamically changes based on edit mode
    var pageTitle by mutableStateOf("Create Note")
        private set

    // State for showing modals (will be used later)
    var showColorPickerModal by mutableStateOf(false)
        private set
    var showDeleteConfirmationModal by mutableStateOf(false)
        private set
    var showDiscardChangesConfirmationModal by mutableStateOf(false)
        private set
    var showAddCategoryModal by mutableStateOf(false)
        private set
    var showReminderModal by mutableStateOf(false)
        private set
    private var showEmptyNoteModal by mutableStateOf(false)
        private set
    var showNoteSavedModal by mutableStateOf(false)
        private set


    init {
        // For demonstration, let's simulate edit mode after a delay
        // In a real app, you'd pass an argument to the ViewModel or check saved state
        // For now, we'll just set it to true to see the delete button
        // isEditMode = true // Uncomment to test edit mode initially
        updatePageTitle()
    }

    private fun updatePageTitle() {
        pageTitle = if (isEditMode) "Edit Note" else "Create Note"
    }

    fun setEditModeVal(editMode: Boolean) {
        isEditMode = editMode
        updatePageTitle()
    }

    fun onBackClick() {
        // In a real app, this would navigate back,
        // potentially showing a "discard changes" modal if there are unsaved changes.
        // For now, we'll just log it.
        println("Back button clicked!")
        // TODO: Implement logic to check for unsaved changes and show modal
        // showDiscardChangesConfirmationModal = true
    }

    fun onColorPickerClick() {
        // Show the color picker modal
        showColorPickerModal = true
        println("Color picker button clicked!")
    }

    fun onDeleteClick() {
        // Show the delete confirmation modal
        showDeleteConfirmationModal = true
        println("Delete button clicked!")
    }

    // Functions to dismiss modals (will be fully implemented later)
    fun dismissColorPickerModal() {
        showColorPickerModal = false
    }

    fun dismissDeleteConfirmationModal() {
        showDeleteConfirmationModal = false
    }

    fun dismissDiscardChangesConfirmationModal() {
        showDiscardChangesConfirmationModal = false
    }

    fun dismissAddCategoryModal() {
        showAddCategoryModal = false
    }

    fun dismissReminderModal() {
        showReminderModal = false
    }

    fun dismissEmptyNoteModal() {
        showEmptyNoteModal = false
    }

    fun dismissNoteSavedModal() {
        showNoteSavedModal = false
    }
}