package com.example.luurk.di

import com.example.luurk.data.repository.PostgresUserRepository
import com.example.luurk.data.services.UserServiceImpl
import com.example.luurk.db.DbConfig
import com.example.luurk.db.createDatabase
import com.example.luurk.domain.repository.UserRepository
import com.example.luurk.domain.services.UserService
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