package com.example.tp_flashcard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tp_flashcard.model.FlashcardCategory
import com.example.tp_flashcard.ui.screen.FlashcardScreen
import com.example.tp_flashcard.ui.screen.HomeScreen
import com.example.tp_flashcard.viewmodel.FlashcardViewModel
import com.example.tp_flashcard.viewmodel.HomeViewModel

@Composable
fun FlashcardNavHost(
    navController: NavHostController,
    homeViewModel: HomeViewModel
) {
    NavHost(navController = navController, startDestination = "home"){
        composable("home") {
            HomeScreen(
                homeViewModel = homeViewModel,
                onCategoryClick = { category: FlashcardCategory ->
                    navController.navigate("flashcards/${category.id}")
                }
            )
        }
        composable(
            route = "flashcards/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType} )
        ){
            backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0

            val flashcardViewModel: FlashcardViewModel = viewModel()

            LaunchedEffect(categoryId) {
                flashcardViewModel.loadCategory(categoryId)
            }

            FlashcardScreen(
                flashcardViewModel = flashcardViewModel,
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
    }
}