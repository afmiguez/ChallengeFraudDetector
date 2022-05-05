package me.afmiguez.projects.challenge.routes

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import me.afmiguez.projects.challenge.models.Transaction
import me.afmiguez.projects.challenge.usecases.TransactionsService
import me.afmiguez.projects.challenge.usecases.csv.csvToTransactionList
import me.afmiguez.projects.challenge.usecases.xml.xmlToTransactionList
import java.io.InputStream


fun Route.fileUploadRoutes(transactionsService: TransactionsService) {

    authenticate("auth-jwt") {
        post("/upload") {
            val principal = call.principal<JWTPrincipal>()
            val userEmail = principal!!.payload.getClaim("username").asString()
            try {
                val multipartData = call.receiveMultipart()
                multipartData.forEachPart { part ->
                    if (part is PartData.FileItem) {
                        try {
                            return@forEachPart handleResponse(part.contentType,part.streamProvider(), transactionsService, userEmail,this)
                        } catch (e: Exception) {
                            handleException(e, this)
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
    }
}

suspend fun handleResponse(
    contentType: ContentType?,
    inputStream: InputStream, transactionsService: TransactionsService, userEmail:String, pipelineContext: PipelineContext<Unit, ApplicationCall>) {
    val transactions:List<Transaction> = when (contentType.toString()) {
        "text/csv" -> {
            csvToTransactionList(inputStream)
        }
        "text/xml" -> {
            xmlToTransactionList(inputStream)
        }
        else -> {
            return throw IllegalArgumentException("Invalid file format ")
        }
    }

    if (transactions.isEmpty()) {
        return
    }
    val validTransactionsCounter = transactionsService.saveTransactions(transactions,userEmail)
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
                "File is not supported",
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
