package com.example.luurk.plugins.di

import com.example.luurk.data.repository.PostgresUserRepository
import com.example.luurk.data.services.UserServiceImpl
import com.example.luurk.domain.repository.UserRepository
import com.example.luurk.domain.services.UserService
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
    single<UserServiceImpl>() bind UserService::class

}