package me.afmiguez.projects.challenge.dao

import me.afmiguez.projects.challenge.data.entities.TransactionEntity
import me.afmiguez.projects.challenge.data.tables.Transactions
import me.afmiguez.projects.challenge.models.Transaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class TransactionsDAOImpl : TransactionsDAO {
    override suspend fun save(transaction: Transaction): Transaction {
        return transaction {
            TransactionEntity.save(transaction).toModel()
        }
    }

    override suspend fun getAllTransactions() = transaction {
        TransactionEntity.all().map { it.toModel() }
    }

    override fun hasTransactionsForDay(referenceDate: LocalDate): Boolean {
        return transaction {
            TransactionEntity
                .find { Transactions.timestamp.eq(referenceDate) }.any()
        }
    }
}

