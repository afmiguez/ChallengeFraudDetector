package me.afmiguez.projects.challenge.routes.dtos

import kotlinx.serialization.Serializable
import me.afmiguez.projects.challenge.models.Transaction
import java.time.LocalDateTime

@Serializable
data class TransactionDTO(
    val sourceBankName: String, val sourceBranchCode: String, val sourceAccountCode: String,
    val destinationBankName: String, val destinationBranchCode: String, val destinationAccountCode: String,
    val amount: Float, val timestamp: String
) {
    constructor(transaction: Transaction) : this(
        sourceBankName = transaction.sourceBankName,
        sourceBranchCode = transaction.sourceBranchCode,
        sourceAccountCode = transaction.sourceAccountCode,
        destinationBankName = transaction.destinationBankName,
        destinationBranchCode=transaction.destinationBranchCode,
        destinationAccountCode=transaction.destinationAccountCode,
        amount=transaction.amount,
        timestamp=transaction.timestamp.toString()
    )

    fun toModel() = Transaction(
        sourceBankName = sourceBankName,
        sourceBranchCode = sourceBranchCode,
        sourceAccountCode = sourceAccountCode,
        destinationBankName = destinationBankName,
        destinationBranchCode = destinationBranchCode,
        destinationAccountCode = destinationAccountCode,
        amount = amount,
        timestamp = LocalDateTime.parse(timestamp).toLocalDate()
    )
}