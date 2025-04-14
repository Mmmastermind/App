package com.example.myapplicationyoga.domain.states

data class SignInState (
    val email: String = "e@mail.ru",
    val password: String = "123456",
    var errorEmail: Boolean = false,
    var errorPassword:Boolean = false
)