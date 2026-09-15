package com.example.luurk

import com.example.luurk.domain.models.User
import com.example.luurk.features.auth.AuthService
import com.example.luurk.plugins.db.DbConfig
import com.example.luurk.plugins.di.appModule
import com.example.luurk.plugins.routing.configureRouting
import com.example.luurk.plugins.serialization.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.netty.EngineMain
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin


fun main(args: Array<String>): Unit = EngineMain.main(args)

suspend fun Application.module() {

    val dbConfig = DbConfig(
        url = environment.config.property("ktor.database.url").getString(),
        user = environment.config.property("ktor.database.user").getString(),
        driver = environment.config.property("ktor.database.driver").getString(),
        password = environment.config.property("ktor.database.password").getString(),
    )
    install(Koin) {
        modules(appModule(dbConfig = dbConfig))
    }

    configureSerialization()
    configureRouting()


    val service by inject<AuthService>()



    routing {
        get("/") {
            call.respond(service.createUser(User(email = "jess@test.com")))
            call.respond(service.getAllUsers().forEach { println("${it.id} and ${it.email}") })
        }
    }
}

