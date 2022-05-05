package me.afmiguez.projects.challenge.data.entities

import me.afmiguez.projects.challenge.data.DatabaseFactory.dbQuery
import me.afmiguez.projects.challenge.data.tables.Transactions
import me.afmiguez.projects.challenge.data.tables.suspiciousBranchQuery
import me.afmiguez.projects.challenge.data.tables.suspiciousAccountQuery
import me.afmiguez.projects.challenge.data.tables.suspiciousTransactionsQuery
import me.afmiguez.projects.challenge.models.Transaction
import me.afmiguez.projects.challenge.models.SuspiciousAccount
import me.afmiguez.projects.challenge.models.SuspiciousBranch
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.month
import org.jetbrains.exposed.sql.javatime.year
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class TransactionEntity(id: EntityID<Int>) : IntEntity(id) {

    var sourceBankName by Transactions.sourceBankName
    var sourceBranchCode by Transactions.sourceBranchCode
    var sourceAccountCode by Transactions.sourceAccountCode
    var destinationBankName by Transactions.destinationBankName
    var destinationBranchCode by Transactions.destinationBranchCode
    var destinationAccountCode by Transactions.destinationAccountCode
    var amount by Transactions.amount
    var timestamp by Transactions.timestamp
    var import by ImportEntity referencedOn Transactions.import

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
        fun save(transaction: Transaction, importEntity: ImportEntity): TransactionEntity {
            return transaction {
                TransactionEntity.new {
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
                        entity.import = importEntity
                    }
                }
            }
        }

        suspend fun transactionsGreaterThan100000(month: LocalDate): List<Transaction> {
            return dbQuery {

                Transactions.suspiciousTransactionsQuery(
                    month
                )
            }
        }

        suspend fun transactionMonths(): List<LocalDate> {
            return dbQuery {
                Transactions
                    .slice(Transactions.timestamp.month(), Transactions.timestamp.year())
                    .selectAll()
                    .groupBy(Transactions.timestamp.month(), Transactions.timestamp.year())
                    .map {
                        LocalDate.of(it[Transactions.timestamp.year()], it[Transactions.timestamp.month()], 1)
                    }

            }
        }

        suspend fun suspiciousAccounts(month: LocalDate): List<SuspiciousAccount> {
            return dbQuery {
                ArrayList<SuspiciousAccount>().apply {
                    addAll(
                        Transactions.suspiciousAccountQuery(
                            Transactions.sourceAccountCode,
                            Transactions.sourceBankName,
                            month,
                            isSource = true
                        )
                    )
                    addAll(
                        Transactions.suspiciousAccountQuery(
                            Transactions.destinationAccountCode,
                            Transactions.destinationBankName,
                            month,
                            isSource = false
                        )
                    )
                }.distinct()
            }
        }

        suspend fun suspiciousBranches(month: LocalDate): List<SuspiciousBranch> {
            return dbQuery {
                ArrayList<SuspiciousBranch>().apply {
                    addAll(
                        Transactions.suspiciousBranchQuery(
                            Transactions.sourceBranchCode,
                            Transactions.sourceBankName,
                            month,
                            isSource = true
                        )
                    )
                    addAll(
                        Transactions.suspiciousBranchQuery(
                            Transactions.destinationBranchCode,
                            Transactions.destinationBankName,
                            month,
                            isSource = false
                        )
                    )
                }.distinct()
            }
        }
    }

}


