package com.example.luurk.features.auth

import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

suspend fun Application.authRoutes() {

    val authService by inject<AuthService>()

    routing {

        route("/login") {
            get {
                call.respondText("login route")

            }
        }
    }
}