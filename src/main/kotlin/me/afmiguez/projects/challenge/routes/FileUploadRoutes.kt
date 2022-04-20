package me.afmiguez.projects.challenge.routes

import com.fasterxml.jackson.dataformat.csv.CsvReadException
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.afmiguez.projects.challenge.usecases.TransactionsService
import me.afmiguez.projects.challenge.usecases.csv.csvToTransactionList

fun Route.fileUploadRoutes(transactionsService: TransactionsService) {

    post("/upload") {
        try {
            val multipartData = call.receiveMultipart()

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        try {
                            val transactions = csvToTransactionList(part.streamProvider())
                            val validTransactionsCounter = transactionsService.saveTransactions(transactions)
                            if(validTransactionsCounter>0) {
                                call.respondText("$validTransactionsCounter transactions has been stored")
                            }else{
                                call.respondText("Cannot register transactions for day ${transactions[0].timestamp} again", status = HttpStatusCode.BadRequest)
                            }
                        } catch (e: Exception) {
                            when (e) {
                                is CsvReadException -> {
                                    return@forEachPart call.respondText(
                                        "File is not a valid .csv",
                                        status = HttpStatusCode.BadRequest
                                    )
                                }
                                else -> {
                                    e.printStackTrace()
                                    return@forEachPart call.respondText(
                                        "${e.message}",
                                        status = HttpStatusCode.BadRequest
                                    )
                                }
                            }
                        }

                    }
                    else ->
                        return@forEachPart call.respondText(
                            "No file was uploaded",
                            status = HttpStatusCode.BadRequest
                        )
                }
            }
        } catch (e: Exception) {
            call.respondText(
                "Content-Type header is required",
                status = HttpStatusCode.BadRequest
            )
        }
    }

    get("/transactions") {
        call.respond(transactionsService.getAllTransactions())
    }
}