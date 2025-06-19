package com.example.tp_flashcard.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Flashcard(
    @PrimaryKey
    val id : Int,
    val categoryId: Int,
    val question: String,
    val answer: String
)

@Entity
data class FlashcardCategory(
    @PrimaryKey
    val id: Int,
    val name: String
)