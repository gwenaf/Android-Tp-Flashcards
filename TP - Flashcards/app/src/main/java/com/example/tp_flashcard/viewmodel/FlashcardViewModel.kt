package com.example.tp_flashcard.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tp_flashcard.model.Flashcard
import com.example.tp_flashcard.repository.FlashcardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FlashcardUiState(
    val currentIndex: Int = 0,
    val flashcards: List<Flashcard> = emptyList(),
    val isFinished: Boolean = false
)

class FlashcardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FlashcardUiState())
    val uiState: StateFlow<FlashcardUiState> = _uiState.asStateFlow()

    fun loadCategory(categoryId: Int) {
        val cards = FlashcardRepository.getFlashcardsByCategory(categoryId)
        _uiState.value = FlashcardUiState(
            currentIndex = 0,
            flashcards = cards,
            isFinished = false
        )
    }

    fun moveToNextCard() {
        val state = _uiState.value
        if (state.currentIndex < state.flashcards.size -1){
            _uiState.value = state.copy(currentIndex = state.currentIndex + 1)
        } else {
            _uiState.value = state.copy(isFinished = true)
        }
    }
}