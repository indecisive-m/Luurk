package com.example.luurk.features.user

import com.example.luurk.domain.models.User
import com.example.luurk.features.auth.SignupResult


interface UserRepository {
    suspend fun addUser(user: User): SignupResult
    suspend fun getAllUsers(): List<User>

    suspend fun getUserByEmail(email: String): User?


}