package com.example.luurk.plugins.di

import com.example.luurk.features.auth.AuthService
import com.example.luurk.features.auth.AuthServiceImpl
import com.example.luurk.features.auth.JwtService
import com.example.luurk.features.user.PostgresUserRepository
import com.example.luurk.features.user.UserRepository
import com.example.luurk.plugins.jwt.JwtConfig
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

fun appModule(database: R2dbcDatabase, jwtConfig: JwtConfig) = module {

    single {
        database
    }

    single {
        jwtConfig
    }

    single<PostgresUserRepository>() bind UserRepository::class
    single<AuthServiceImpl>() bind AuthService::class
    single<JwtService>()

}