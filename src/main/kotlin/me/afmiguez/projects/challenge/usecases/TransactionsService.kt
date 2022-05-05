package me.afmiguez.projects.challenge.usecases

import me.afmiguez.projects.challenge.models.Import
import me.afmiguez.projects.challenge.models.Transaction
import me.afmiguez.projects.challenge.models.Suspicious
import java.time.LocalDate

interface TransactionsService {
    suspend fun saveTransactions(transactions:List<Transaction>,userEmail:String):Int
    suspend fun getAllTransactions(): List<Transaction>
    suspend fun getAllImports(): List<Import>
    suspend fun getById(id: Int): Import?
    suspend fun suspiciousTransactions(month: LocalDate): Suspicious
}
