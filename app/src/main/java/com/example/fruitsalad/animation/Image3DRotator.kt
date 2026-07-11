package com.example.fruitsalad.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.sqrt


@Composable
fun Image3DRotator(
    imageRes: Int,
    modifier: Modifier = Modifier,
    size: Dp = 220.dp,
    maxTiltDegrees: Float = 28f
) {
    val scope = rememberCoroutineScope()
    val rotationX = remember { Animatable(0f) }
    val rotationY = remember { Animatable(0f) }

    val magnitude = sqrt(rotationX.value * rotationX.value + rotationY.value * rotationY.value)
    val tiltFraction = (magnitude / maxTiltDegrees).coerceIn(0f, 1f)

    Box(
        modifier = modifier.size(size + 48.dp),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(size)
                .graphicsLayer {
                    this.rotationY = rotationY.value
                    this.rotationX = -rotationX.value
                    cameraDistance = 18f * density

                    scaleX = 1f + (magnitude / 600f)
                    scaleY = 1f + (magnitude / 600f)
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            scope.launch {
                                rotationX.stop()
                                rotationY.stop()
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()

                            val nextY = (rotationY.value + dragAmount.x * 0.4f)
                                .coerceIn(-maxTiltDegrees, maxTiltDegrees)
                            val nextX = (rotationX.value + dragAmount.y * 0.4f)
                                .coerceIn(-maxTiltDegrees, maxTiltDegrees)

                            scope.launch {
                                rotationY.snapTo(nextY)
                                rotationX.snapTo(nextX)
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                rotationX.animateTo(
                                    0f,
                                    spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow)
                                )
                            }
                            scope.launch {
                                rotationY.animateTo(
                                    0f,
                                    spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow)
                                )
                            }
                        }
                    )
                }
        )

        // Glossy sheen overlay reflection effect
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = (rotationY.value * 0.7f).dp, y = (-rotationX.value * 0.7f).dp)
                .graphicsLayer { alpha = tiltFraction * 0.35f }
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = 0.9f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )
    }
}
