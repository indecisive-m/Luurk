package com.example.luurk.domain.services

import com.example.luurk.domain.models.User

interface UserService {
    suspend fun createUser(user: User)
    suspend fun getAllUsers() : List<User>
}