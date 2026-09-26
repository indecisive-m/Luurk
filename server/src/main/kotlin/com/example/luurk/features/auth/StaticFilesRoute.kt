package com.example.luurk.features.auth

import io.ktor.server.application.Application
import io.ktor.server.http.content.staticFiles
import io.ktor.server.routing.routing
import java.io.File

fun Application.staticFilesRoute() {
    routing {
        staticFiles(".well-known", File("keys"), "jwks.json")

    }

}