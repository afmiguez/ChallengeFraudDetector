package me.afmiguez.projects.challenge.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import me.afmiguez.projects.challenge.inject
import me.afmiguez.projects.challenge.routes.fileUploadRoutes
import me.afmiguez.projects.challenge.routes.importRoutes
import me.afmiguez.projects.challenge.routes.transactions
import me.afmiguez.projects.challenge.routes.userRoutes
import me.afmiguez.projects.challenge.usecases.TransactionsService
import me.afmiguez.projects.challenge.usecases.UsersService

fun Application.configureRouting() {

    routing {
        val transactionsServiceImpl by inject<TransactionsService>()
        val usersServiceImpl by inject<UsersService>()
        fileUploadRoutes(transactionsServiceImpl)
        importRoutes(transactionsServiceImpl)
        userRoutes(usersServiceImpl)
        transactions(transactionsServiceImpl)
    }
}
