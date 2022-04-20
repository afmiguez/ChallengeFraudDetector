package me.afmiguez.projects.challenge.usecases

import me.afmiguez.projects.challenge.models.Transaction

interface TransactionsService {
    suspend fun saveTransactions(transactions:List<Transaction>):Int
    suspend fun getAllTransactions(): List<Transaction>
}
