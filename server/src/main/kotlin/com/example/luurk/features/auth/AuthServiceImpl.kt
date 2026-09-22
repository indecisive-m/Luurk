package com.example.luurk.features.auth

import com.example.luurk.features.mappers.toUser
import com.example.luurk.features.user.UserDto
import com.example.luurk.features.user.UserRepository
import com.password4j.Password

class AuthServiceImpl(
    private val userRepository: UserRepository
) : AuthService {
    override suspend fun signup(user: UserDto): SignupResult {

        val password = user.password

        val hashedPassword = Password.hash(password).withArgon2()

        val newUser = UserDto(
            email = user.email,
            password = hashedPassword.result
        ).toUser()

        return userRepository.addUser(newUser)


    }

    override suspend fun login(user: UserDto): LoginResult {

        val databaseUser = userRepository.getUserByEmail(user.email)

        val password = user.password

        return if (databaseUser != null) {
            val hashedPassword = Password.check(password, databaseUser.passwordHash).withArgon2()

            if (hashedPassword) {
                LoginResult.SUCCESS
            } else {
                LoginResult.INVALID_CREDENTIALS
            }

        } else {
            LoginResult.INVALID_CREDENTIALS
        }
    }


}