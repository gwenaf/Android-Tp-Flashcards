package com.example.tp_flashcard.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tp_flashcard.model.FlashcardCategory
import com.example.tp_flashcard.repository.FlashcardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _categories = MutableStateFlow<List<FlashcardCategory>>(emptyList())
    val categories: StateFlow<List<FlashcardCategory>> = _categories.asStateFlow()

    init {
        _categories.value = FlashcardRepository.getAllCategories()
    }
}