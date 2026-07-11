package com.example.fruitsalad.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruitsalad.R

@Composable
fun WellcomeScreen(modifier: Modifier = Modifier, onContinueClick: () -> Unit) {
    // 1. Water Bobbing Animation
    val infiniteTransition = rememberInfiniteTransition(label = "water_effect")
    val bobbingOffset by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bobbingOffset"
    )

    // 2. Water Ripple Animation
    val rippleScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rippleScale"
    )

    // 3. Button Shimmer Animation
    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = -400f,
        targetValue = 800f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )

    Column(modifier = modifier.fillMaxSize().statusBarsPadding()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.65f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFF8C42),
                            Color(0xFFFF7A2B)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // Static/Ripple Water Layer (Circles)
            Box(contentAlignment = Alignment.Center) {
                // Outer Ripple
                Box(
                    modifier = Modifier
                        .size(310.dp)
                        .graphicsLayer {
                            scaleX = rippleScale
                            scaleY = rippleScale
                            alpha = (1.12f - rippleScale) * 0.4f
                        }
                        .background(Color.White.copy(alpha = 0.1f), shape = CircleShape)
                )
                // Main Water Circle
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.12f),
                            shape = CircleShape
                        )
                )
            }

            Image(
                painter = painterResource(R.drawable.fruit_basket_image),
                contentDescription = null,
                modifier = Modifier
                    .size(170.dp)
                    .graphicsLayer { 
                        translationY = bobbingOffset.dp.toPx()
                        rotationZ = (bobbingOffset / 4f)
                    }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.35f)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Get The Freshest Fruits Salad Combo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF272140),
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "We deliver the best and freshest fruit salad in town. Order for a combo today!!!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF50577E),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Button with Shimmer Line
                Button(
                    onClick = { onContinueClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .drawWithContent {
                            drawContent() // Draw original button
                            // Draw moving white shimmer line
                            val brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0f),
                                    Color.White.copy(alpha = 0.4f),
                                    Color.White.copy(alpha = 0f)
                                ),
                                start = Offset(shimmerTranslate, 0f),
                                end = Offset(shimmerTranslate + 150f, 200f)
                            )
                            drawRect(brush = brush)
                        },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8C42)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "Let's Continue",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun WellcomeScreenPrev(modifier: Modifier = Modifier) {

    WellcomeScreen(onContinueClick = {})
}