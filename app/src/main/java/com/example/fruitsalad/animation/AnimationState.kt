package com.example.fruitsalad.animation

import androidx.compose.ui.graphics.Color

enum class AnimationState { IDLE, EXPLODING, SPINNING, DETAIL }

data class CardData(
    val name: String,
    val price: String,
    val imageRes: Int,
    val containerColor: Color = Color.White
)