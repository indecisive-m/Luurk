package com.example.luurk.plugins.db

import org.jetbrains.exposed.v1.core.Table

object UserTable : Table("users") {
    val id = long("id").autoIncrement()
    val email = varchar("email", 50)
}