package com.example.tp_flashcard.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tp_flashcard.model.FlashcardCategory
import com.example.tp_flashcard.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onCategoryClick: (FlashcardCategory) -> Unit
) {
    val categories by homeViewModel.categories.collectAsState()
    val gridState = rememberLazyGridState()

    // 1) liste visible progressivement
    val visibleCategories = remember { mutableStateListOf<FlashcardCategory>() }
    // 2) IDs qu'on vient d'ajouter et qu'on doit animer
    val animatedIds = remember { mutableStateSetOf<String>() }

    LaunchedEffect(categories) {
        visibleCategories.clear()
        animatedIds.clear()
        categories.chunked(2).forEachIndexed { rowIndex, pair ->
            delay(rowIndex * 50L)
            visibleCategories += pair
            animatedIds += pair.map { it.id.toString() }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize(tween(500))
    ) {
        items(visibleCategories, key = { it.id }) { category ->
            // si l'ID est dans animatedIds, on joue l'enter, sinon pas d'animation
            val shouldAnimate = animatedIds.remove(category.id.toString())
            AnimatedVisibility(
                visible = true,
                enter = if (shouldAnimate) {
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        )
                    ) +
                            slideInVertically(
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = FastOutSlowInEasing
                                ),
                                initialOffsetY = { fullHeight -> fullHeight / 3 }
                            ) +
                            scaleIn(
                                initialScale = 0.85f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                } else EnterTransition.None,
                exit = ExitTransition.None
            ) {
                CategoryItem(
                    category = category,
                    onClick = { onCategoryClick(category) },
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1f)
                        .animateItem(
                            placementSpec = tween(300),
                            fadeInSpec = tween(400)
                        )
                )
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
    val hue = (category.id * 137.508f) % 360f
    val startColor = hslToColor(hue, 0.6f, 0.7f)
    val endColor = hslToColor((hue + 30f) % 360f, 0.6f, 0.5f)
    val gradient = Brush.verticalGradient(listOf(startColor, endColor))

    Card(
        modifier = modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(gradient, shape = MaterialTheme.shapes.medium)
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}


// Convert HSL to RGB for unique gradient
// using this in order to get the same color each time rather than using random colors
fun hslToColor(h: Float, s: Float, l: Float): Color {
    val c = (1f - kotlin.math.abs(2 * l - 1)) * s
    val x = c * (1f - kotlin.math.abs((h / 60f) % 2 - 1))
    val m = l - c / 2f
    val (r1, g1, b1) = when {
        h < 60f -> Triple(c, x, 0f)
        h < 120f -> Triple(x, c, 0f)
        h < 180f -> Triple(0f, c, x)
        h < 240f -> Triple(0f, x, c)
        h < 300f -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }
    return Color((r1 + m).coerceIn(0f, 1f), (g1 + m).coerceIn(0f, 1f), (b1 + m).coerceIn(0f, 1f))
}
