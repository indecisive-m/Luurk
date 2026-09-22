package com.example.luurk.features.user

import com.example.luurk.domain.models.User


interface UserRepository {
    suspend fun addUser(user: User)
    suspend fun getAllUsers(): List<User>


}