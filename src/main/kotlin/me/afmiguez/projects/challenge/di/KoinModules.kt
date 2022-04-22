package me.afmiguez.projects.challenge.di

import me.afmiguez.projects.challenge.dao.ImportDAO
import me.afmiguez.projects.challenge.dao.ImportDAOImpl
import me.afmiguez.projects.challenge.dao.TransactionsDAO
import me.afmiguez.projects.challenge.dao.TransactionsDAOImpl
import me.afmiguez.projects.challenge.usecases.TransactionsService
import me.afmiguez.projects.challenge.usecases.TransactionsServiceImpl
import org.koin.dsl.module

val koinModules = module {
    single<TransactionsDAO> { TransactionsDAOImpl() }
    single<ImportDAO> { ImportDAOImpl() }
    single<TransactionsService> { TransactionsServiceImpl(get(),get()) }
}