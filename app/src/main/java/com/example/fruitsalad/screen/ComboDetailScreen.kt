package com.example.fruitsalad.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruitsalad.animation.CardData
import com.example.fruitsalad.animation.Image3DRotator

@Composable
fun ComboDetailScreen(cardData: CardData, onBack: () -> Unit) {
    val descriptionDelay = remember(cardData.name) {
        (cardData.name.length * 45L) + 250L
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top hero card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .background(
                    cardData.containerColor,
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
        ) {
            IconButton(
                onClick = onBack, 
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(24.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF272140))
            }

            Image3DRotator(
                imageRes = cardData.imageRes,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp),
                size = 240.dp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                TypewriterText(
                    fullText = cardData.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF272140)
                )

                Spacer(modifier = Modifier.height(12.dp))

                TypewriterText(
                    fullText = "A handpicked combo of the freshest seasonal fruits, mixed and served to bring out the best flavor in every bite.",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    startDelayMillis = descriptionDelay
                )
            }

            Text(
                text = cardData.price,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF08626),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}