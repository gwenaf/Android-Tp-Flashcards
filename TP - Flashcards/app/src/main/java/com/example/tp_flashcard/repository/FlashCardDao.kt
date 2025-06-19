package com.example.tp_flashcard.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tp_flashcard.model.Flashcard
import com.example.tp_flashcard.model.FlashcardCategory

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM FlashcardCategory")
    fun getAllCategories(): List<FlashcardCategory>

    @Query("SELECT * FROM Flashcard WHERE categoryId = :categoryId")
    fun getFlashcardsByCategoryId(categoryId : Int): List<Flashcard>

    @Insert
    fun insertCategories(categories: List<FlashcardCategory>)

    @Insert
    fun insertFlashCards(flashcards: List<Flashcard>)

    @Query("SELECT COUNT(*) FROM Flashcard")
    fun countFlashCards(): Int
}