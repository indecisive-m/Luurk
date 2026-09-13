package com.example.luurk.domain.repository

import com.example.luurk.domain.models.User

interface UserRepository {
    suspend fun addUser(user: User)
    suspend fun getAllUsers(): List<User>

}