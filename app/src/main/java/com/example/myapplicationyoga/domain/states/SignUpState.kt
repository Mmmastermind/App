package com.example.myapplicationyoga.domain.states

data class SignUpState (
    val email: String = "",
    val password: String = "",
    val confirmpassword: String = "",
    val surname: String = "",
    val username: String = "",
    val image: String? = "",
    val birthday: String = "",
    var errorEmail: Boolean = false,

)