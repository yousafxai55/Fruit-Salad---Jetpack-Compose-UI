package com.example.fruitsalad.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SpinToDetailOverlay(
    imageRes: Int,
    imageSize: Dp = 200.dp,
    onAnimationComplete: () -> Unit
) {
    val density = LocalDensity.current

    val smoothEasing = CubicBezierEasing(0.33f, 0f, 0.2f, 1f)

    val rotationZ = remember { Animatable(0f) }
    val translationX = remember { Animatable(0f) }
    val translationY = remember { Animatable(0f) }
    val scale = remember { Animatable(0.25f) }
    val alpha = remember { Animatable(1f) }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val edgePaddingPx = with(density) { 24.dp.toPx() }
        val imageSizePx = with(density) { imageSize.toPx() }
        val maxWidthPx = with(density) { maxWidth.toPx() }
        val maxHeightPx = with(density) { maxHeight.toPx() }

        // Target: TOP-RIGHT area of the screen
        // Starting from a more central position to better mimic coming from the card
        val targetX = maxWidthPx - imageSizePx - edgePaddingPx * 1.2f
        val targetY = -(maxHeightPx * 0.28f) // Move UP from the center

        val durationMs = 1300 

        LaunchedEffect(Unit) {
            launch {
                rotationZ.animateTo(
                    targetValue = 1080f,
                    animationSpec = tween(durationMillis = durationMs, easing = smoothEasing)
                )
            }
            launch {
                translationX.animateTo(
                    targetValue = targetX,
                    animationSpec = tween(durationMillis = durationMs, easing = smoothEasing)
                )
            }
            launch {
                translationY.animateTo(
                    targetValue = targetY,
                    animationSpec = tween(durationMillis = durationMs, easing = smoothEasing)
                )
            }
            launch {
                scale.animateTo(
                    targetValue = 1f, 
                    animationSpec = tween(durationMillis = durationMs, easing = smoothEasing)
                )
            }

            delay(durationMs.toLong() - 100)

            alpha.animateTo(0f, tween(durationMillis = 300, easing = FastOutLinearInEasing))

            onAnimationComplete()
        }

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart) // Start from center-left (near Recommended Combos)
                .padding(start = 40.dp) // Offset a bit to match LazyRow start
                .size(imageSize)
                .graphicsLayer {
                    this.translationX = translationX.value
                    this.translationY = translationY.value
                    this.rotationZ = rotationZ.value
                    this.scaleX = scale.value
                    this.scaleY = scale.value
                    this.alpha = alpha.value
                }
        )
    }
}