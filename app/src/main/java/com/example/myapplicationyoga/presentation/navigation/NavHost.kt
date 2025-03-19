package com.example.myapplicationyoga.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplicationyoga.presentation.screens.main.MainScreen
import com.example.myapplicationyoga.presentation.screens.signin.SignInScreen
import com.example.myapplicationyoga.presentation.screens.signup.SignUpScreen
import com.example.myapplicationyoga.presentation.screens.splash.SplashScreen

@Composable
fun Navigation_(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationRoutes.SPLASH) {
        composable(NavigationRoutes.SPLASH)
        {
            SplashScreen(navController)
        }
        composable(NavigationRoutes.SIGNIN)
        {
            SignInScreen(navController)
        }
        composable(NavigationRoutes.SIGNUP)
        {
            SignUpScreen(navController)
        }
        composable(NavigationRoutes.MAIN)
        {
            MainScreen(navController)
        }
    }
}

