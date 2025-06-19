package com.example.tp_flashcard.repository

import android.content.Context
import androidx.room.Room
import com.example.tp_flashcard.model.Flashcard
import com.example.tp_flashcard.model.FlashcardCategory

object FlashcardRepository {
    private lateinit var database: FlashcardDatabase

    fun initDatabase(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            FlashcardDatabase::class.java,
            "flashcard-db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration(false)
            .build()

        val dao = database.flashcardDao()
        if (dao.countFlashCards() == 0) {
            val defaultCategories = listOf(
                FlashcardCategory(1, name = "Math"),
                FlashcardCategory(2, name = "Science"),
                FlashcardCategory(3, name = "History"),
                FlashcardCategory(4, name = "Literature")
            )

            val defaultFlashcards = listOf(
                Flashcard(1, 1, "Quelle est la capitale de la France ?", "Paris"),
                Flashcard(2, 1, "Quelle est la capitale de l'Allemagne ?", "Berlin"),
                Flashcard(3, 2, "5 + 7 = ?", "12"),
                Flashcard(4, 2, "Quelle est la racine carrée de 9 ?", "3"),
                Flashcard(5, 3, "Quelle est la formule chimique de l'eau ?", "H2O"),
                Flashcard(6, 3, "Quelle est la planète la plus proche du soleil ?", "Mercure")
            )

            dao.insertCategories(defaultCategories)
            dao.insertFlashCards(defaultFlashcards)
        }
    }

    fun getAllCategories() : List<FlashcardCategory>
    {
        return database.flashcardDao().getAllCategories()
    }

    fun getFlashcardsByCategory(categoryId: Int): List<Flashcard> {
        return database.flashcardDao().getFlashcardsByCategoryId(categoryId)
    }
}