package com.example.luurk.plugins.di

import com.example.luurk.features.auth.AuthService
import com.example.luurk.features.auth.AuthServiceImpl
import com.example.luurk.features.user.PostgresUserRepository
import com.example.luurk.features.user.UserRepository
import com.example.luurk.plugins.db.DbConfig
import com.example.luurk.plugins.db.createDatabase
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

fun appModule(dbConfig: DbConfig) = module {

    single {
        createDatabase(dbConfig)
    }

    single<PostgresUserRepository>() bind UserRepository::class
    single<AuthServiceImpl>() bind AuthService::class

}