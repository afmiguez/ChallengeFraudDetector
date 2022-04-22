package me.afmiguez.projects.challenge.routes

import com.fasterxml.jackson.dataformat.csv.CsvReadException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import me.afmiguez.projects.challenge.models.Transaction
import me.afmiguez.projects.challenge.routes.dtos.TransactionDTO
import me.afmiguez.projects.challenge.usecases.TransactionsService
import me.afmiguez.projects.challenge.usecases.csv.csvToTransactionList
import java.io.InputStream


fun Route.fileUploadRoutes(transactionsService: TransactionsService) {

    post("/upload") {
        try {
            val multipartData = call.receiveMultipart()
            multipartData.forEachPart { part ->
                if (part is PartData.FileItem) {
                    try {
                        return@forEachPart handleResponse(part.streamProvider(),transactionsService,this)
                    } catch (e: Exception) {
                        handleException(e,this)
                    }
                }
            }
        } catch (e: Exception) {
            call.respondText(
                "Content-Type header is required",
                status = HttpStatusCode.BadRequest
            )
        }
        call.respondText(
            "No file was uploaded",
            status = HttpStatusCode.BadRequest
        )
    }

    get("/transactions") {
        call.respond(transactionsService.getAllTransactions().map { TransactionDTO(it) })
    }
}

suspend fun handleResponse(inputStream: InputStream, transactionsService: TransactionsService,  pipelineContext: PipelineContext<Unit, ApplicationCall>) {
    val transactions = csvToTransactionList(inputStream)
    if (transactions.isEmpty()) {
        return
    }
    val validTransactionsCounter = transactionsService.saveTransactions(transactions)
    return if (validTransactionsCounter > 0) {
        pipelineContext.call.respondText("$validTransactionsCounter transactions has been stored")
    } else {
        pipelineContext.call.respondText(
            "Cannot register transactions for day ${transactions[0].timestamp} again",
            status = HttpStatusCode.BadRequest
        )
    }
}

suspend fun handleException(e: Exception, pipelineContext: PipelineContext<Unit, ApplicationCall>) {
    when (e) {
        is MissingKotlinParameterException
        -> {
            pipelineContext.call.respondText(
                "File is not a valid .csv",
                status = HttpStatusCode.BadRequest
            )
        }
        else -> {
            e.printStackTrace()
            pipelineContext.call.respondText(
                "${e.message}",
                status = HttpStatusCode.BadRequest
            )
        }
    }
}
