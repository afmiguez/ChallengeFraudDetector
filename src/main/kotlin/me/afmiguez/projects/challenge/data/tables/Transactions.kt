package me.afmiguez.projects.challenge.data.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime

object Transactions:IntIdTable() {
    val sourceBankName=varchar("source_bank_name",50)
    val sourceBranchCode=varchar("source_branch_code",10)
    val sourceAccountCode=varchar("source_account_code",10)
    val destinationBankName=varchar("destination_bank_name",50)
    val destinationBranchCode=varchar("destination_branch_code",10)
    val destinationAccountCode=varchar("destination_account_code",10)
    val amount=float("amount")
    val timestamp=date("timestamp")

}