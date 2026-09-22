package com.example.luurk.features.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long? = null,
    val email: String,
    val passwordHash: String
)
