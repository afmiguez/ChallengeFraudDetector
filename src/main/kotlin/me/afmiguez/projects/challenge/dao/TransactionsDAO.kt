package me.afmiguez.projects.challenge.dao

import me.afmiguez.projects.challenge.models.Transaction
import me.afmiguez.projects.challenge.models.SuspiciousAccount
import me.afmiguez.projects.challenge.models.SuspiciousBranch
import java.time.LocalDate

interface TransactionsDAO {

    suspend fun getAllTransactions(): List<Transaction>
    suspend fun hasTransactionsForDay(referenceDate: LocalDate): Boolean

    suspend fun transactionsGreaterThan100_000(month:LocalDate): List<Transaction>

    suspend fun getTransactionsMonths(): List<LocalDate>
    suspend fun suspiciousAccounts(month: LocalDate): List<SuspiciousAccount>
    suspend fun suspiciousBranches(month: LocalDate): List<SuspiciousBranch>
}