package com.example.luurk.features.auth

import com.example.luurk.features.user.UserDto
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
            post {
                val user = call.receive<UserDto>()

                when (val response = authService.login(user)) {
                    LoginResult.SUCCESS -> call.respondText(
                        response.toString(),
                        status = HttpStatusCode.OK
                    )

                    LoginResult.INVALID_CREDENTIALS -> call.respondText(
                        response.toString(),
                        status = HttpStatusCode.Unauthorized
                    )
                }


            }
        }
        route("/signup") {
            post {
                val user = call.receive<UserDto>()

                when (val response = authService.signup(user)) {
                    SignupResult.CREATED -> call.respondText(
                        response.toString(),
                        status = HttpStatusCode.Created
                    )

                    SignupResult.EMAIL_ALREADY_EXISTS -> call.respondText(
                        response.toString(),
                        status = HttpStatusCode.Conflict
                    )
                }


            }
        }
    }
}