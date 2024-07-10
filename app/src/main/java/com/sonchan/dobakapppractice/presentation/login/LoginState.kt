package com.sonchan.dobakapppractice.presentation.login

data class LoginState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
