package com.example.luurk.features.auth

enum class SignupResult {
    CREATED, EMAIL_ALREADY_EXISTS
}

sealed class LoginResult {
    class Success(val token: String) : LoginResult()
    object InvalidCredentials : LoginResult()
}

