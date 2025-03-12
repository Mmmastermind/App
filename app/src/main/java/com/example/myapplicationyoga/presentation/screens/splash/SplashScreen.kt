package com.example.myapplicationyoga.presentation.screens.splash

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplicationyoga.R
import com.example.myapplicationyoga.presentation.navigation.NavigationRoutes
import com.example.myapplicationyoga.presentation.ui.theme.Beige1
import kotlinx.coroutines.delay

@SuppressLint("AnimatedVisibility")

@Composable
fun SplashScreen(navController: NavController){

    val isFirstImageVisible = remember { mutableStateOf(false) }
    val isSecondImageVisible = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        isFirstImageVisible.value = true
        delay(1500)
        isSecondImageVisible.value = true
        delay(1500)
        navController.navigate(NavigationRoutes.SIGNIN) {
            popUpTo(NavigationRoutes.SPLASH) {
                inclusive = true
            }
        }
    }



    Box(modifier = Modifier
        .background(Beige1)
        .fillMaxSize()){
        AnimatedVisibility(
            visible = isFirstImageVisible.value,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 1500, easing = LinearEasing)
            ),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 500)
            ),
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.icon_down),
                contentDescription = "First Splash Image",
            )
        }

        AnimatedVisibility(
            visible = isSecondImageVisible.value,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 1500, easing = LinearEasing)
            ),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight/10 },
                animationSpec = tween(durationMillis = 500)
            ),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(width = 270.dp, height = 270.dp)
                    .offset(y = (-70).dp),
                    painter = painterResource(id = R.drawable.icon_up),
                    contentDescription = "Second Splash Image",
                )
            }
        }


    }
}