package me.afmiguez.projects.challenge.dao

import me.afmiguez.projects.challenge.models.Transaction
import java.time.LocalDate

interface TransactionsDAO {
    suspend fun save(transaction: Transaction):Transaction
    suspend fun getAllTransactions(): List<Transaction>
    fun hasTransactionsForDay(referenceDate: LocalDate): Boolean
}