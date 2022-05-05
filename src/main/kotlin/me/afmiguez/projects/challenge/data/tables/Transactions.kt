package me.afmiguez.projects.challenge.data.tables

import me.afmiguez.projects.challenge.models.Transaction
import me.afmiguez.projects.challenge.models.SuspiciousAccount
import me.afmiguez.projects.challenge.models.SuspiciousBranch
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SqlExpressionBuilder.between
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.and

import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.sum
import java.time.LocalDate

object Transactions : IntIdTable() {
    val sourceBankName = varchar("source_bank_name", 50)
    val sourceBranchCode = varchar("source_branch_code", 10)
    val sourceAccountCode = varchar("source_account_code", 10)
    val destinationBankName = varchar("destination_bank_name", 50)
    val destinationBranchCode = varchar("destination_branch_code", 10)
    val destinationAccountCode = varchar("destination_account_code", 10)
    val amount = float("amount")
    val timestamp = date("timestamp")
    val import = reference("import", Imports)

}

private fun getStartMonth(localDate: LocalDate): LocalDate {
    return localDate.withDayOfMonth(1);
}

private fun getEndMonth(localDate: LocalDate): LocalDate {
    return localDate.withDayOfMonth(localDate.month.length(localDate.isLeapYear));
}

fun Transactions.suspiciousTransactionsQuery(month: LocalDate): List<Transaction> {

    return Transactions.select(
        monthPeriod(month).and(
            amount greaterEq 100_000f
        )
    ).map {
        Transaction(
            sourceBankName = it[sourceBankName],
            sourceAccountCode = it[sourceAccountCode],
            sourceBranchCode = it[sourceBranchCode],
            destinationBankName = it[destinationBankName],
            destinationAccountCode = it[destinationAccountCode],
            destinationBranchCode = it[destinationBranchCode],
            amount = it[amount],
            timestamp = it[timestamp]
        )
    }
}

fun monthPeriod(month: LocalDate) = Transactions.timestamp.between(
    getStartMonth(month),
    getEndMonth(month)
)


fun Transactions.suspiciousBranchQuery(
    branchCode: Column<String>,
    bankName: Column<String>,
    month: LocalDate,
    isSource: Boolean
): List<SuspiciousBranch> {

    return Transactions.slice(branchCode, bankName, amount.sum())
        .select(
            monthPeriod(month)
        )

        .groupBy(branchCode, bankName)
        .having { amount.sum() greaterEq 1_000_000.00 }
        .map {

            SuspiciousBranch(
                amount = it[amount.sum()]!!,
                branchCode = it[if (isSource) {
                    sourceBranchCode
                } else {
                    destinationBranchCode
                }],
                accountCode = it[branchCode],
                typeMovimentation = if (isSource) {
                    "Entrada"
                } else {
                    "Saida"
                },
                bankName = it[bankName]

            )
        }

}

fun Transactions.suspiciousAccountQuery(
    accountCode: Column<String>,
    bankName: Column<String>,
    month: LocalDate,
    isSource: Boolean
): List<SuspiciousAccount> {
    return Transactions.slice(accountCode, bankName, amount.sum())
        .select(
            monthPeriod(month)
        )

        .groupBy(accountCode, bankName)
        .having { amount.sum() greaterEq 1_000_000.00 }
        .map {
            SuspiciousAccount(
                amount = it[amount.sum()]!!,
                branchCode = it[if (isSource) {
                    sourceAccountCode
                } else {
                    destinationAccountCode
                }],
                accountCode = it[
                        accountCode
                ],
                typeMovimentation = if (isSource) {
                    "Entrada"
                } else {
                    "Saida"
                },
                bankName = it[bankName]

            )
        }
}