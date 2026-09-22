package com.example.luurk.features.auth

import com.example.luurk.domain.models.User
import com.example.luurk.features.user.UserRepository

class AuthServiceImpl(
    private val userRepository: UserRepository
) : AuthService {
    override suspend fun createUser(user: User) {
        userRepository.addUser(user)
    }

    override suspend fun getAllUsers(): List<User> {
        return userRepository.getAllUsers()
    }


}