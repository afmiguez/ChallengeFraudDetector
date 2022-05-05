package me.afmiguez.projects.challenge.usecases

import me.afmiguez.projects.challenge.dao.ImportDAO
import me.afmiguez.projects.challenge.dao.TransactionsDAO
import me.afmiguez.projects.challenge.models.Import
import me.afmiguez.projects.challenge.models.Transaction
import me.afmiguez.projects.challenge.models.Suspicious
import me.afmiguez.projects.challenge.routes.dtos.TransactionDTO
import java.time.LocalDate
import java.time.LocalDateTime

class TransactionsServiceImpl(private val transactionsDAO: TransactionsDAO, private val importDAO: ImportDAO) :
    TransactionsService {

    override suspend fun saveTransactions(transactions: List<Transaction>, userEmail: String): Int {
        val referenceDate = transactions[0].timestamp
        if (transactionsDAO.hasTransactionsForDay(referenceDate)) {
            return -1
        }

        val validTransactions = transactions.filter { it.isValid(referenceDate) }

        val imports = importDAO.save(
            Import(
                importDate = LocalDateTime.now(),
                transactionsDate = referenceDate,
                transactions = validTransactions
            ),
            userEmail,
        )

        return imports.transactions.size
    }

    override suspend fun getAllImports(): List<Import> {
        return importDAO.getAllImports()
    }

    override suspend fun getById(id: Int): Import? {
        return importDAO.getById(id)
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return transactionsDAO.getAllTransactions()
    }

    override suspend fun suspiciousTransactions(month: LocalDate): Suspicious {

        val suspicious = Suspicious()

        suspicious.transactions.addAll(transactionsDAO.transactionsGreaterThan100_000(month).map { TransactionDTO(it) })
        suspicious.accounts.addAll(transactionsDAO.suspiciousAccounts(month))
        suspicious.branches.addAll(transactionsDAO.suspiciousBranches(month))


        return suspicious
    }
}