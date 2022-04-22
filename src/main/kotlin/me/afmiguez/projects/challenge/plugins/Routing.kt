package me.afmiguez.projects.challenge.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import me.afmiguez.projects.challenge.inject
import me.afmiguez.projects.challenge.routes.fileUploadRoutes
import me.afmiguez.projects.challenge.routes.transactionImportRoutes
import me.afmiguez.projects.challenge.usecases.TransactionsService

fun Application.configureRouting() {

    routing {
        val transactionsServiceImpl by inject<TransactionsService>()
        fileUploadRoutes(transactionsServiceImpl)
        transactionImportRoutes(transactionsServiceImpl)
    }
}
