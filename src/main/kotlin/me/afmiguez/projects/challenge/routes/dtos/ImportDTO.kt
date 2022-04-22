package me.afmiguez.projects.challenge.routes.dtos

import kotlinx.serialization.Serializable
import me.afmiguez.projects.challenge.models.Import
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val DATE_PATTERN="dd/MM/yyyy"
const val DATE_TIME_PATTERN="dd/MM/yyyy HH:mm:ss"

@Serializable
class ImportDTO(private val importDate:String, private val transactionsDate:String){

    constructor(import: Import):this(
        importDate = import.importDate.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)),
        transactionsDate = import.transactionsDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN)),
    ){

    }

    fun toModel()=Import(
        importDate = LocalDateTime.parse(importDate),
        transactionsDate=LocalDate.parse(transactionsDate)
    )
}