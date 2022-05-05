package me.afmiguez.projects.challenge.models

import java.time.LocalDate
import java.time.LocalDateTime

class Import(val importDate: LocalDateTime, val transactionsDate: LocalDate,val transactions:List<Transaction> = emptyList()) {
    var user: User? = null
    var id:Int?=null
}