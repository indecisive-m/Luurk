package com.example.luurk.features.auth

import com.example.luurk.features.user.UserDto

interface AuthService {
    suspend fun signup(user: UserDto): SignupResult

    suspend fun login(user: UserDto): LoginResult


}