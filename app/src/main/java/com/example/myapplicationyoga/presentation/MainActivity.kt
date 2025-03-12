package com.example.myapplicationyoga.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplicationyoga.presentation.navigation.Navigation_
import com.example.myapplicationyoga.presentation.screens.splash.SplashScreen
import com.example.myapplicationyoga.presentation.ui.theme.MyApplicationYogaTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            MyApplicationYogaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Navigation_()


                }
            }


        }
    }
}

