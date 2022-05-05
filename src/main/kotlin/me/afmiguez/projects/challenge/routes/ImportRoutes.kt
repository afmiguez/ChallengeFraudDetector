package me.afmiguez.projects.challenge.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.afmiguez.projects.challenge.routes.dtos.ImportDTO
import me.afmiguez.projects.challenge.usecases.TransactionsService

fun Route.importRoutes(transactionsService: TransactionsService){

    authenticate("auth-jwt") {
        route("/imports"){

            get {
                call.respond(transactionsService.getAllImports().map {
                    ImportDTO(it)
                })
            }

            get("{id?}"){
                val id = call.parameters["id"]?.toInt() ?: return@get call.respondText(
                    text = "Missing id",
                    status = HttpStatusCode.BadRequest,
                )

                val importTransaction=transactionsService.getById(id) ?: return@get call.respondText(
                    text="No import with id $id",
                    status = HttpStatusCode.NotFound
                )

                call.respond(ImportDTO(importTransaction))
            }


        }
    }
}