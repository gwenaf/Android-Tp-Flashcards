package com.example.tp_flashcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.tp_flashcard.navigation.FlashcardNavHost
import com.example.tp_flashcard.repository.FlashcardRepository
import com.example.tp_flashcard.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        FlashcardRepository.initDatabase(applicationContext)

        setContent {
            FlashcardNavHost(homeViewModel = homeViewModel)
        }
    }
}