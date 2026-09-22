package com.example.luurk.features.user

import com.example.luurk.domain.models.User
import com.example.luurk.features.auth.SignupResult
import com.example.luurk.features.mappers.toDomain
import com.example.luurk.plugins.db.UserTable
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.insert
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

class PostgresUserRepository(
    private val db: R2dbcDatabase
) : UserRepository {

    override suspend fun addUser(user: User): SignupResult {

        return suspendTransaction(db = db) {

            val isEmailInDatabase = getUserByEmail(user.email) == null

            if (isEmailInDatabase) {
                UserTable.insert {
                    it[email] = user.email
                    it[password_hash] = user.passwordHash
                }

                SignupResult.CREATED

            } else {
                SignupResult.EMAIL_ALREADY_EXISTS
            }

        }
    }

    override suspend fun getAllUsers(): List<User> {
        return suspendTransaction {
            UserTable.selectAll().map { it.toDomain() }.toList()
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return suspendTransaction {
            UserTable.selectAll().where { UserTable.email eq email }.map { it.toDomain() }
                .firstOrNull()
        }

    }

}