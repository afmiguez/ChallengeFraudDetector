package me.afmiguez.projects.challenge.dao

import me.afmiguez.projects.challenge.data.DatabaseFactory.dbQuery
import me.afmiguez.projects.challenge.data.entities.TransactionEntity
import me.afmiguez.projects.challenge.data.tables.Transactions
import java.time.LocalDate

class TransactionsDAOImpl : TransactionsDAO {

    override suspend fun getAllTransactions() = dbQuery {
        TransactionEntity.all().map { it.toModel() }
    }

    override suspend fun hasTransactionsForDay(referenceDate: LocalDate): Boolean {
        return dbQuery {
            TransactionEntity
                .find { Transactions.timestamp.eq(referenceDate) }.any()
        }
    }

    override suspend fun transactionsGreaterThan100_000(month:LocalDate) = TransactionEntity.transactionsGreaterThan100000(month)
    override suspend fun getTransactionsMonths() = TransactionEntity.transactionMonths()
    override suspend fun suspiciousAccounts(month: LocalDate)=TransactionEntity.suspiciousAccounts(month)

    override suspend fun suspiciousBranches(month: LocalDate)=TransactionEntity.suspiciousBranches(month)

}

