package me.afmiguez.projects.challenge.models

import java.time.LocalDate

class Transaction(
    val sourceBankName: String, val sourceBranchCode: String, val sourceAccountCode: String,
    val destinationBankName: String, val destinationBranchCode: String, val destinationAccountCode: String,
    val amount: Float, val timestamp: LocalDate
) {

    fun isValid(referenceDate: LocalDate): Boolean {
        return sourceBankName.isNotBlank() && sourceBranchCode.isNotBlank() && sourceAccountCode.isNotBlank()
                && destinationBankName.isNotBlank() && destinationBranchCode.isNotBlank() && destinationAccountCode.isNotBlank()
                && amount>0f && referenceDate == timestamp
    }

}