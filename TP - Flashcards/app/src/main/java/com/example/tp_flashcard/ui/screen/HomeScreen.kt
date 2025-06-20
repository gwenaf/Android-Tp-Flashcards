package com.example.tp_flashcard.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items // this import isn't managed by the IDE but NEEDED for LazyGrids
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tp_flashcard.model.FlashcardCategory
import com.example.tp_flashcard.viewmodel.HomeViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onCategoryClick: (FlashcardCategory) -> Unit
) {
    val categories by homeViewModel.categories.collectAsState()

    val visibleCategories = remember { mutableStateListOf<FlashcardCategory>() }

    LaunchedEffect(categories) {
        categories.forEachIndexed { index, category ->
            delay(100L * index)
            visibleCategories.add(category)
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(visibleCategories) { category ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(
                        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                    ) + expandIn(
                        expandFrom = Alignment.Center,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ) + slideInVertically(
                        initialOffsetY = { it / 4 },
                        animationSpec = spring(stiffness = Spring.StiffnessLow)
                    ),
                    exit = fadeOut(
                        animationSpec = tween(durationMillis = 150)
                    ) + shrinkOut(
                        shrinkTowards = Alignment.Center,
                        animationSpec = spring(stiffness = Spring.StiffnessMedium)
                    )
                ) {
                    CategoryItem(
                        category = category,
                        onClick = { onCategoryClick(category) },
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: FlashcardCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        Color(0xFFE57373), // Rouge clair
        Color(0xFF64B5F6), // Bleu clair
        Color(0xFF81C784), // Vert clair
        Color(0xFFFFD54F)  // Jaune
    )
    val backgroundColor = colors[category.id % colors.size]

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}