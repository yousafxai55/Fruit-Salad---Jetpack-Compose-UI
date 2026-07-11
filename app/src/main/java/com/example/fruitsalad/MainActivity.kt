package com.example.fruitsalad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fruitsalad.animation.AnimationState
import com.example.fruitsalad.animation.CardData
import com.example.fruitsalad.animation.ExplosionOverlay
import com.example.fruitsalad.animation.SpinToDetailOverlay
import com.example.fruitsalad.screen.ComboDetailScreen
import com.example.fruitsalad.screen.FruitDetailScreen
import com.example.fruitsalad.screen.HomeScreen
import com.example.fruitsalad.screen.WellcomeScreen
import com.example.fruitsalad.ui.theme.ZingerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZingerTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var selectedFruit by remember { mutableStateOf<CardData?>(null) }
    var animState by remember { mutableStateOf(AnimationState.IDLE) }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = "welcome") {
            composable("welcome") {
                WellcomeScreen(onContinueClick = {
                    navController.navigate("home") {
                        popUpTo("welcome") { inclusive = true }
                    }
                })
            }
            composable("home") {
                HomeScreen(
                    onCardClick = { fruit ->
                        selectedFruit = fruit
                        animState = AnimationState.EXPLODING
                    },
                    onComboClick = { combo ->
                        selectedFruit = combo
                        animState = AnimationState.SPINNING
                    }
                )
            }
            composable(
                route = "detail",
                enterTransition = {
                    fadeIn(animationSpec = tween(900)) + scaleIn(
                        initialScale = 0.88f,
                        animationSpec = tween(900)
                    )
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(700)) + scaleOut(
                        targetScale = 0.88f,
                        animationSpec = tween(700)
                    )
                }
            ) {
                selectedFruit?.let { fruit ->
                    FruitDetailScreen(cardData = fruit, onBack = {
                        navController.popBackStack()
                    })
                }
            }
            composable(
                route = "combo_detail",
                enterTransition = {
                    fadeIn(animationSpec = tween(700)) + slideInVertically(
                        initialOffsetY = { -it / 3 },
                        animationSpec = tween(700)
                    )
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(700)) + slideOutVertically(
                        targetOffsetY = { it / 3 },
                        animationSpec = tween(700)
                    )
                }
            ) {
                selectedFruit?.let { combo ->
                    ComboDetailScreen(cardData = combo, onBack = {
                        navController.popBackStack()
                    })
                }
            }
        }

        if (animState == AnimationState.EXPLODING && selectedFruit != null) {
            ExplosionOverlay(imageRes = selectedFruit!!.imageRes) {
                animState = AnimationState.IDLE
                navController.navigate("detail")
            }
        }

        if (animState == AnimationState.SPINNING && selectedFruit != null) {
            // Spin overlay
            SpinToDetailOverlay(imageRes = selectedFruit!!.imageRes) {
                animState = AnimationState.IDLE
            }

            // NAVIGATION DELAY:
            LaunchedEffect(selectedFruit) {
                kotlinx.coroutines.delay(1000) 
                navController.navigate("combo_detail")
            }
        }
    }
}
