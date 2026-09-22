package com.example.luurk.domain.models

data class User(
    val id: Long? = null,
    val email: String,
    val passwordHash: String
)
