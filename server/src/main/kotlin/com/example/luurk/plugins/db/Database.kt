package com.example.luurk.plugins.db

import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase

fun createDatabase(dbConfig: DbConfig): R2dbcDatabase {

    val postgresdb = R2dbcDatabase.connect(
        url = dbConfig.url,
        driver = dbConfig.driver,
        user = dbConfig.user,
        password = dbConfig.password
    )

    return postgresdb
}
