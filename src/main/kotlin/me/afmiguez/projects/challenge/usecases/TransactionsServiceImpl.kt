package me.afmiguez.projects.challenge.usecases

import me.afmiguez.projects.challenge.dao.ImportDAO
import me.afmiguez.projects.challenge.dao.TransactionsDAO
import me.afmiguez.projects.challenge.models.Import
import me.afmiguez.projects.challenge.models.Transaction
import java.time.LocalDateTime

class TransactionsServiceImpl(private val transactionsDAO: TransactionsDAO, private val importDAO: ImportDAO) :
    TransactionsService {

    override suspend fun saveTransactions(transactions: List<Transaction>): Int {
        val referenceDate = transactions[0].timestamp
        if (transactionsDAO.hasTransactionsForDay(referenceDate)) {
            return -1
        }

        importDAO.save(
            Import(
                importDate = LocalDateTime.now(),
                transactionsDate = referenceDate
            )
        )

        return transactions.filter { it.isValid(referenceDate) }
            .map {
                transactionsDAO.save(it)
            }.size
    }

    override suspend fun getAllImports(): List<Import> {
        return importDAO.getAllImports()
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return transactionsDAO.getAllTransactions()
    }
}