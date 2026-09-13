package com.example.luurk.data.services

import com.example.luurk.domain.models.User
import com.example.luurk.domain.repository.UserRepository
import com.example.luurk.domain.services.UserService
import com.example.luurk.sayHello

class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService{
    override suspend fun createUser(user: User) {
        userRepository.addUser(user)
    }

    override suspend fun getAllUsers(): List<User> {
        return userRepository.getAllUsers()
    }

}