package me.afmiguez.projects.challenge.data.entities

import me.afmiguez.projects.challenge.data.tables.Transactions
import me.afmiguez.projects.challenge.models.Transaction
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TransactionEntity(id: EntityID<Int>) : IntEntity(id) {

    var sourceBankName by Transactions.sourceBankName
    var sourceBranchCode by Transactions.sourceBranchCode
    var sourceAccountCode by Transactions.sourceAccountCode
    var destinationBankName by Transactions.destinationBankName
    var destinationBranchCode by Transactions.destinationBranchCode
    var destinationAccountCode by Transactions.destinationAccountCode
    var amount by Transactions.amount
    var timestamp by Transactions.timestamp

    fun toModel() = Transaction(
        sourceBankName,
        sourceBranchCode,
        sourceAccountCode,
        destinationBankName,
        destinationBranchCode,
        destinationAccountCode,
        amount,
        timestamp
    )

    companion object : IntEntityClass<TransactionEntity>(Transactions) {
        fun save(transaction: Transaction): TransactionEntity {
            return TransactionEntity.new {
                val entity = this
                with(transaction) {
                    entity.sourceBankName = sourceBankName
                    entity.sourceBranchCode = sourceBranchCode
                    entity.sourceAccountCode = sourceAccountCode
                    entity.destinationBankName = destinationBankName
                    entity.destinationBranchCode = destinationBranchCode
                    entity.destinationAccountCode = destinationAccountCode
                    entity.amount = amount
                    entity.timestamp = timestamp
                }
            }
        }
    }

}