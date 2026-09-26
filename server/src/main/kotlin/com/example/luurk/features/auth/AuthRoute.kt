package com.example.luurk.features.auth

import com.example.luurk.features.user.UserDto
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
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
                authService.login(user)

                when (val response = authService.login(user)) {
                    is LoginResult.Success -> {
                        call.respondText(
                            response.token,
                            status = HttpStatusCode.OK
                        )
                    }

                    is LoginResult.InvalidCredentials -> {
                        call.respondText(
                            response.toString(),
                            status = HttpStatusCode.Unauthorized
                        )
                    }

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


        routing {
            authenticate("auth-jwt") {
                get("/yo") {
                    val principal = call.principal<JWTPrincipal>()
                    val payload = principal!!.payload

                    val id = payload.getClaim("id")

                    println(id)
                    val now = System.currentTimeMillis()
                    val expiresAt = principal.expiresAt?.time
                        ?.minus(now)
                    call.respondText(
                        "Hello, $id! " +
                                "Token is expired at $expiresAt ms."
                    )
                }
            }
        }

    }
}