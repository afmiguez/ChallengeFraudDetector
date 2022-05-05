package me.afmiguez.projects.challenge.routes.dtos

import kotlinx.serialization.Serializable
import me.afmiguez.projects.challenge.models.Import
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val DATE_PATTERN="dd/MM/yyyy"
const val DATE_TIME_PATTERN="dd/MM/yyyy HH:mm:ss"

@Serializable
class ImportDTO(private val id:Int,private val importDate:String, private val transactionsDate:String,private val user:String,private val transactions:List<TransactionDTO> = emptyList()){

    constructor(import: Import):this(
        id=import.id!!,
        importDate = import.importDate.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)),
        transactionsDate = import.transactionsDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN)),
        user = import.user!!.email,
        transactions = import.transactions.map { TransactionDTO(it) }
    )

    fun toModel()=Import(
        importDate = LocalDateTime.parse(importDate),
        transactionsDate=LocalDate.parse(transactionsDate),

    )
}