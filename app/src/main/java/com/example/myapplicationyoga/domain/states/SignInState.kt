package com.example.myapplicationyoga.domain.states

data class SignInState (
    val email: String = "",
    val password: String = "",
    var errorEmail: Boolean = false,
    var errorPassword:Boolean = false
)