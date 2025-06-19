package com.example.tp_flashcard.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tp_flashcard.model.Flashcard
import com.example.tp_flashcard.model.FlashcardCategory

@Database(entities = [Flashcard::class, FlashcardCategory::class], version = 1)
abstract class FlashcardDatabase: RoomDatabase() {
    abstract fun flashcardDao(): FlashCardDao
}