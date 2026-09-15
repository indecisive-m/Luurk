package com.example.luurk.features.auth

import com.example.luurk.domain.models.User

interface AuthService {
    suspend fun createUser(user: User)
    suspend fun getAllUsers(): List<User>
}