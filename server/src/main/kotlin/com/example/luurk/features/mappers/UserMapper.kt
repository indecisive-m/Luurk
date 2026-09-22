package com.example.luurk.features.mappers

import com.example.luurk.domain.models.User
import com.example.luurk.features.user.UserDto

fun UserDto.toUser(): User {
    return User(
        id = id,
        email = email,
        passwordHash = passwordHash
    )
}