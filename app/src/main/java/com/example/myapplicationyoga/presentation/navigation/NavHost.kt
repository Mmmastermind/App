package com.example.myapplicationyoga.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplicationyoga.presentation.screens.main.MainScreen
import com.example.myapplicationyoga.presentation.screens.newyoga.NewYogaScreen
import com.example.myapplicationyoga.presentation.screens.signin.SignInScreen
import com.example.myapplicationyoga.presentation.screens.signup.SignUpScreen
import com.example.myapplicationyoga.presentation.screens.splash.SplashScreen
import com.example.myapplicationyoga.presentation.screens.yogadetails.YogaDetailsScreen
import com.example.myapplicationyoga.presentation.screens.yogadetails.YogaDetailsViewMode

@Composable
fun Navigation_() {
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

        composable(NavigationRoutes.NEWYOGA){
            NewYogaScreen(navController)
        }

        composable(
            route = NavigationRoutes.YOGADETAILS + "/{id}", // Определяем маршрут для экрана деталей книги с параметром id
            arguments = listOf(navArgument("id") { // Указываем аргументы, которые будут переданы в маршрут
                type = NavType.StringType // Указываем, что тип аргумента - строка
            })
        ) {
            // Получаем аргументы из навигационного компонента
            val id = it.arguments?.getString("id") // Извлекаем значение аргумента "id"

            // Проверяем, что id не равен null
            if (id != null) {
                // Если id существует, отображаем экран деталей книги, передавая navController и id
                YogaDetailsScreen(navController, id)
            }
        }
    }
}

