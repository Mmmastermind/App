package com.example.myapplicationyoga.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplicationyoga.presentation.ui.theme.Beige1
import com.example.myapplicationyoga.presentation.ui.theme.Brown1

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = viewModel()){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige1)

    ) {
        Column(modifier = Modifier.padding(horizontal = 40.dp, vertical = 50.dp,) .padding(top = 20.dp)) {
            Text(
                "Добрый день!",
                color = Brown1,
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 50.dp)

            )
        }

        }
}