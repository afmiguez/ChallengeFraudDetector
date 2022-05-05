package me.afmiguez.projects.challenge.di

import me.afmiguez.projects.challenge.dao.*
import me.afmiguez.projects.challenge.usecases.TransactionsService
import me.afmiguez.projects.challenge.usecases.TransactionsServiceImpl
import me.afmiguez.projects.challenge.usecases.UsersService
import me.afmiguez.projects.challenge.usecases.UsersServiceImpl
import org.koin.dsl.module

val koinModules = module {
    single<TransactionsDAO> { TransactionsDAOImpl() }
    single<ImportDAO> { ImportDAOImpl() }
    single<TransactionsService> { TransactionsServiceImpl(get(),get()) }
    single<UserDAO>{UserDAOImpl()}
    single<UsersService> { UsersServiceImpl(get()) }
}