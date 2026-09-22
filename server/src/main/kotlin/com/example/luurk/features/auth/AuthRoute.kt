package com.example.luurk.features.auth

import com.example.luurk.features.mappers.toUser
import com.example.luurk.features.user.UserDto
import com.password4j.Password
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

suspend fun Application.authRoutes() {

    val authService by inject<AuthService>()

    routing {

        route("/login") {
//            post {
//
//            }
        }
        route("/signup") {
            post {
                val user = call.receive<UserDto>()

                val password = user.passwordHash

                val hashedPassword = Password.hash(password).withArgon2()

                val newUser = UserDto(
                    email = user.email,
                    passwordHash = hashedPassword.result
                )

                authService.createUser(newUser.toUser())

                call.respondText("Customer stored correctly", status = HttpStatusCode.Created)

            }
        }
    }
}