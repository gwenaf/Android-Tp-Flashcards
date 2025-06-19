package com.example.tp_flashcard.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.tp_flashcard.viewmodel.FlashcardViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp_flashcard.model.Flashcard
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun FlashcardScreen(
    flashcardViewModel: FlashcardViewModel,
    onFinished: () -> Unit
) {
    val uiState by flashcardViewModel.uiState.collectAsState()
    val cards = uiState.flashcards

    if (cards.isEmpty() && !uiState.isFinished) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (uiState.isFinished){
        LaunchedEffect(Unit) {
            onFinished()
        }
        return
    }

    val currentIndex = uiState.currentIndex
    val totalCards = uiState.flashcards.size

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LinearProgressIndicator(
        progress = { if (totalCards > 0) currentIndex.toFloat() / totalCards else 0f },
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center){
            AnimatedContent(
                targetState = currentIndex,
                transitionSpec = {
                    (
                        (slideInHorizontally {fullWidth -> fullWidth} + fadeIn())
                            .togetherWith(slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut())
                            .using(SizeTransform(false))
                    )
                }
            ) { index ->
                val flashcard = uiState.flashcards[index]
                FlashcardView(flashcard = flashcard)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { flashcardViewModel.moveToNextCard() },
            enabled = !uiState.isFinished,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = if (currentIndex < totalCards - 1) "Suivant" else "Terminé")
        }
    }
}

@Composable
fun FlashcardView(flashcard: Flashcard){
    val rotation = remember { Animatable(0f) }
    var isFront by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    val density = LocalDensity.current.density

    LaunchedEffect(flashcard.id) {
        rotation.snapTo(0f)
        isFront = true
    }

    Card(
        modifier = Modifier
            .width(250.dp)
            .height(160.dp)
            .graphicsLayer{
                rotationY = rotation.value
                cameraDistance = 8 * density
            }
            .clickable{
                coroutineScope.launch {
                    val targetRotation = if (isFront) 180f else 0f
                    rotation.animateTo(targetRotation, animationSpec = tween(durationMillis = 500))
                    isFront = !isFront
                }
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            if (rotation.value <= 90f) {
                // Afficher le recto (question)
                Text(
                    text = flashcard.question,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            } else {
                // Afficher le verso (réponse) avec texte non inversé
                Box(modifier = Modifier.graphicsLayer { rotationY = 180f }) {
                    Text(
                        text = flashcard.answer,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}