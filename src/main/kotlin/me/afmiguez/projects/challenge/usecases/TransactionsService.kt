package me.afmiguez.projects.challenge.usecases

import me.afmiguez.projects.challenge.models.Import
import me.afmiguez.projects.challenge.models.Transaction

interface TransactionsService {
    suspend fun saveTransactions(transactions:List<Transaction>):Int
    suspend fun getAllTransactions(): List<Transaction>
    suspend fun getAllImports(): List<Import>
}
