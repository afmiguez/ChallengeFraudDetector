package me.afmiguez.projects.challenge.di

import me.afmiguez.projects.challenge.dao.TransactionsDAO
import me.afmiguez.projects.challenge.dao.TransactionsDAOImpl
import me.afmiguez.projects.challenge.usecases.TransactionsService
import me.afmiguez.projects.challenge.usecases.TransactionsServiceImpl
import org.koin.dsl.module

val koinModules = module {
    single<TransactionsDAO> { TransactionsDAOImpl() }
    single<TransactionsService> { TransactionsServiceImpl(get()) }
}