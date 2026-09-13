package com.example.luurk.data.mappers

import com.example.luurk.db.UserTable.email
import com.example.luurk.db.UserTable.id
import com.example.luurk.domain.models.User
import org.jetbrains.exposed.v1.core.ResultRow

fun ResultRow.toDomain(): User {
    return User(
        id = this[id],
        email = this[email]
    )
}