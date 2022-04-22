package me.afmiguez.projects.challenge.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.afmiguez.projects.challenge.routes.dtos.ImportDTO
import me.afmiguez.projects.challenge.usecases.TransactionsService

fun Route.transactionImportRoutes(transactionsService: TransactionsService){

    get("/imports") {
        call.respond(transactionsService.getAllImports().map {
            ImportDTO(it)
        })
    }
}