package com.example.fruitsalad.animation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


private fun splitImageIntoPieces(bitmap: Bitmap, rows: Int, cols: Int): List<Bitmap> {
    val pieces = mutableListOf<Bitmap>()
    val baseWidth = bitmap.width / cols
    val baseHeight = bitmap.height / rows

    for (row in 0 until rows) {
        for (col in 0 until cols) {
            val x = col * baseWidth
            val y = row * baseHeight
            val w = if (col == cols - 1) bitmap.width - x else baseWidth
            val h = if (row == rows - 1) bitmap.height - y else baseHeight
            pieces.add(Bitmap.createBitmap(bitmap, x, y, w, h))
        }
    }
    return pieces
}


@Composable
fun ExplosionOverlay(
    imageRes: Int,
    rows: Int = 2,
    cols: Int = 2, // Changed to 4 pieces (2x2)
    imageSize: Dp = 250.dp,
    onAnimationComplete: () -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current

    val sourceBitmap = remember(imageRes) {
        BitmapFactory.decodeResource(context.resources, imageRes)
    }

    val pieceBitmaps = remember(imageRes, rows, cols) {
        splitImageIntoPieces(sourceBitmap, rows, cols).map { it.asImageBitmap() }
    }

    // Start from 1f (Exploded/Dispersed state)
    val assembleProgress = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        // Pieces smoothly come together - increased duration for "slow & smooth" feel
        assembleProgress.animateTo(
            targetValue = 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessVeryLow // Lower stiffness = slower, smoother reassembly
            )
        )
        delay(400) // Slightly longer hold to let the user see the completed fruit
        onAnimationComplete()
    }

    val progress = assembleProgress.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.45f * progress)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.size(imageSize),
            contentAlignment = Alignment.TopStart
        ) {
            val pieceWidth = imageSize / cols
            val pieceHeight = imageSize / rows

            val centerCol = (cols - 1) / 2f
            val centerRow = (rows - 1) / 2f

            var index = 0
            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    val piece = pieceBitmaps[index]
                    index++

                    val dx = col - centerCol
                    val dy = row - centerRow

                    Image(
                        bitmap = piece,
                        contentDescription = null,
                        modifier = Modifier
                            .offset(x = pieceWidth * col, y = pieceHeight * row)
                            .size(pieceWidth, pieceHeight)
                            .graphicsLayer {
                                // Assembly translation — increased distance for a more dramatic smooth entry
                                translationX = dx * 220f * progress
                                translationY = dy * 220f * progress

                                rotationX = dy * 70f * progress
                                rotationY = dx * 70f * progress
                                rotationZ = (dx - dy) * 25f * progress

                                cameraDistance = 12f * density.density

                                alpha = 1f
                            }
                    )
                }
            }
        }
    }
}