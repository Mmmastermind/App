package com.example.myapplicationyoga.presentation.screens.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplicationyoga.domain.states.ResultStates
import com.example.myapplicationyoga.presentation.navigation.NavigationRoutes
import com.example.myapplicationyoga.presentation.screens.components.Button
import com.example.myapplicationyoga.presentation.screens.components.DividerText
import com.example.myapplicationyoga.presentation.screens.components.TextFieldEmail
import com.example.myapplicationyoga.presentation.screens.components.TextFieldPassword
import com.example.myapplicationyoga.presentation.ui.theme.Beige1
import com.example.myapplicationyoga.presentation.ui.theme.Brown1

@Composable
fun SignInScreen(navController: NavController, signInViewModel: SignInViewModel = viewModel()) {
    val UiState = signInViewModel.UiState
    val ResultState = signInViewModel.resultStates.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige1)

    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 50.dp ).padding(top = 50.dp) ) {
            Text(
                "Добро пожаловать!",
                color = Brown1,
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 50.dp)

            )
            Spacer(Modifier.height(30.dp))
            Text(
                "Email",
                color = Brown1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
            TextFieldEmail(
                value = UiState.email,
                error = false,
                onvaluechange = { it -> signInViewModel.updateState(UiState.copy(email = it)) })
            Spacer(Modifier.height(10.dp))
            Text(
                "Пароль",
                color = Brown1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
            TextFieldPassword(
                value = UiState.password,
                onvaluechange = { it -> signInViewModel.updateState(UiState.copy(password = it)) }
            )

            Spacer(Modifier.height(50.dp))
            when (ResultState.value) {
                is ResultStates.Error -> {
                    Button(
                        "Войти"
                    ) {
                        signInViewModel.SignIn().toString()
                    }
                    Text((ResultState as ResultStates.Error).message)
                }

                is ResultStates.Initialized -> {
                    Button("Войти") {
                        signInViewModel.SignIn().toString()
                    }
                }

                is ResultStates.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is ResultStates.Success -> {
                    LaunchedEffect(Unit) { navController.navigate(NavigationRoutes.MAIN)
                    {
                        popUpTo(NavigationRoutes.SIGNIN) {
                            inclusive = true
                        } }

                    }
                }
            }
            Spacer(Modifier.height(220.dp))
            DividerText()

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {

                Text(
                    "Еще нет аккаунта?",
                    fontSize = 16.sp, color = Brown1, modifier = Modifier.padding(8.dp)
                )
                Text(
                    "Зарегистрируйтесь",
                    fontSize = 16.sp, color = Brown1, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp).clickable {
                        navController.navigate(NavigationRoutes.SIGNUP)}
                        )

            }
        }
    }
}

@Preview(locale = "es")
@Composable
fun PreviewSigIn() {
    SignInScreen(rememberNavController())
}
