package com.example.tp_flashcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tp_flashcard.navigation.FlashcardNavHost
import com.example.tp_flashcard.repository.FlashcardRepository
import com.example.tp_flashcard.ui.screen.Background
import com.example.tp_flashcard.ui.theme.TP_FlashcardTheme
import com.example.tp_flashcard.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        FlashcardRepository.initDatabase(applicationContext)

        setContent {
            TP_FlashcardTheme {
                Background {
                    AppScaffold(homeViewModel = homeViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(homeViewModel: HomeViewModel){
    val navController = rememberNavController()

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val title = when {
        currentRoute == "home" -> "Catégories"
        currentRoute?.startsWith("flashcards/") == true -> {
            val categoryId = navBackStackEntry.value?.arguments?.getInt("categoryId")

            val categories = homeViewModel.categories.collectAsState()
            categories.value.find { it.id == categoryId }?.name ?: "Flashcards"
        }
        else -> "TP Flashcard"
    }

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = { Text(text = title) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                )
            )
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            FlashcardNavHost(
                navController = navController,
                homeViewModel = homeViewModel
            )
        }
    }
}