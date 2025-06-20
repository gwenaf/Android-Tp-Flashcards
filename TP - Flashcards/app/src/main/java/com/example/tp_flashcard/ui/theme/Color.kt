package com.example.tp_flashcard.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary               = Color(0xFF9C27B0),
    onPrimary             = Color(0xFFFFFFFF),
    primaryContainer      = Color(0xFFF3E5F5),
    onPrimaryContainer    = Color(0xFF4A148C),

    secondary             = Color(0xFF616161),
    onSecondary           = Color(0xFFFFFFFF),
    secondaryContainer    = Color(0xFFE0E0E0),
    onSecondaryContainer  = Color(0xFF212121),

    tertiary              = Color(0xFFCE93D8),
    onTertiary            = Color(0xFF4A148C),
    tertiaryContainer     = Color(0xFFD1C4E9),
    onTertiaryContainer   = Color(0xFF311B92),

    error                 = Color(0xFFB3261E),
    onError               = Color(0xFFFFFFFF),
    errorContainer        = Color(0xFFF9DEDC),
    onErrorContainer      = Color(0xFF410E0B),

    background            = Color(0xFFFAFAFA),
    onBackground          = Color(0xFF212121),
    surface               = Color(0xFFFFFFFF),
    onSurface             = Color(0xFF212121),

    surfaceVariant        = Color(0xFFEDE7F6),
    onSurfaceVariant      = Color(0xFF311B92),

    outline               = Color(0xFFBDBDBD),

    inverseSurface        = Color(0xFF212121),
    inverseOnSurface      = Color(0xFFFAFAFA),
    inversePrimary        = Color(0xFF9C27B0)
)

private val DarkColors = darkColorScheme(
    primary               = Color(0xFFCE93D8),
    onPrimary             = Color(0xFF4A148C),
    primaryContainer      = Color(0xFF4A148C),
    onPrimaryContainer    = Color(0xFFF3E5F5),

    secondary             = Color(0xFFBDBDBD),
    onSecondary           = Color(0xFF212121),
    secondaryContainer    = Color(0xFF424242),
    onSecondaryContainer  = Color(0xFFE0E0E0),

    tertiary              = Color(0xFFD1C4E9),
    onTertiary            = Color(0xFF311B92),
    tertiaryContainer     = Color(0xFF311B92),
    onTertiaryContainer   = Color(0xFFEDE7F6),

    error                 = Color(0xFFB3261E),
    onError               = Color(0xFFFFFFFF),
    errorContainer        = Color(0xFFF9DEDC),
    onErrorContainer      = Color(0xFF410E0B),

    background            = Color(0xFF212121),
    onBackground          = Color(0xFFFAFAFA),
    surface               = Color(0xFF212121),
    onSurface             = Color(0xFFFAFAFA),

    surfaceVariant        = Color(0xFF49454F),
    onSurfaceVariant      = Color(0xFFE7E0EC),

    outline               = Color(0xFF79747E),

    inverseSurface        = Color(0xFFFFFFFF),
    inverseOnSurface      = Color(0xFF212121),
    inversePrimary        = Color(0xFF6750A4)
)

object FlashcardColor {
    val Light = LightColors
    val Dark  = DarkColors
}
