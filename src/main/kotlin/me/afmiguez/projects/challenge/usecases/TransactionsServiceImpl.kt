package me.afmiguez.projects.challenge.usecases

import me.afmiguez.projects.challenge.dao.TransactionsDAO
import me.afmiguez.projects.challenge.models.Transaction

class TransactionsServiceImpl(private val transactionsDAO: TransactionsDAO):TransactionsService {

    override suspend fun saveTransactions(transactions:List< Transaction>):Int{
        val referenceDate=transactions[0].timestamp
        if(transactionsDAO.hasTransactionsForDay(referenceDate)){
            return -1
        }
        var operations=0
        return transactions.filter { it.isValid(referenceDate) }
            .map{
                transactionsDAO.save(it)
            }.size
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return transactionsDAO.getAllTransactions()
    }
}