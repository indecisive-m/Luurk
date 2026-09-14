package com.example.luurk.data.mappers

import com.example.luurk.domain.models.User
import com.example.luurk.plugins.db.UserTable.email
import com.example.luurk.plugins.db.UserTable.id
import org.jetbrains.exposed.v1.core.ResultRow

fun ResultRow.toDomain(): User {
    return User(
        id = this[id],
        email = this[email]
    )
}