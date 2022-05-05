package me.afmiguez.projects.challenge.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.afmiguez.projects.challenge.routes.dtos.TransactionDTO
import me.afmiguez.projects.challenge.usecases.TransactionsService
import java.time.LocalDate

fun Route.transactions(transactionsService: TransactionsService) {

    route("/transactions"){

        authenticate("auth-jwt") {
            get {
                call.respond(transactionsService.getAllTransactions().map { TransactionDTO(it) })
            }
            get("/suspicious/{month?}"){

                val month = call.parameters["month"] ?: return@get call.respondText(
                    text = "Missing month",
                    status = HttpStatusCode.BadRequest,
                )

                try{
                    call.respond(
                        transactionsService.suspiciousTransactions(
                            LocalDate.parse("$month-01")
                        )
                    )
                }catch (e: Exception){
                    return@get call.respondText(
                        status = HttpStatusCode.BadRequest,
                        text = "Invalid Date"
                    )
                }
            }


        }
    }


}