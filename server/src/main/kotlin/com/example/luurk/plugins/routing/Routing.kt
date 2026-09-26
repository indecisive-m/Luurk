package com.example.luurk.plugins.routing

import com.example.luurk.features.auth.authRoutes
import com.example.luurk.features.auth.staticFilesRoute
import io.ktor.server.application.Application

suspend fun Application.configureRouting() {
    authRoutes()
    staticFilesRoute()
}