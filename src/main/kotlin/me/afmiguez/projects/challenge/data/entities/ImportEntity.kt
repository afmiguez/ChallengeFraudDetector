package me.afmiguez.projects.challenge.data.entities

import kotlinx.coroutines.*
import me.afmiguez.projects.challenge.data.DatabaseFactory.dbQuery
import me.afmiguez.projects.challenge.data.DatabaseFactory.myLaunch
import me.afmiguez.projects.challenge.data.tables.Imports
import me.afmiguez.projects.challenge.data.tables.Transactions
import me.afmiguez.projects.challenge.data.tables.Users
import me.afmiguez.projects.challenge.models.Import
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.GlobalContext

class ImportEntity(id: EntityID<Int>) : IntEntity(id) {
    var importDate by Imports.importDate
    var transactionsDate by Imports.transactionsDate
    val transactions by TransactionEntity referrersOn Transactions.import
    var user by UserEntity referencedOn Imports.user

    fun toModel(): Import {
        val self = this
        return Import(
            importDate = importDate,
            transactionsDate = transactionsDate,
            transactions = transactions.map { it.toModel() }
        ).apply {
            user = self.user.toModel()
            id = self.id.value
        }
    }

    companion object : IntEntityClass<ImportEntity>(Imports) {
        fun save(import: Import, userEmail: String): Import = transaction {
            val userFromDb = UserEntity.find {
                Users.email eq userEmail
            }.first()
            val importEntity = ImportEntity.new {
                importDate = import.importDate
                transactionsDate = import.transactionsDate
                user = userFromDb
            }

            import.transactions.forEach {
                TransactionEntity.save(it, importEntity)
            }

            importEntity.toModel()
        }


        suspend fun getAll(): List<Import> {
            return dbQuery {
                ImportEntity.all()
                    .orderBy(Imports.transactionsDate to SortOrder.DESC)
                    .toList().map { it.toModel() }
            }
        }

        suspend fun getById(id: Int): Import? {
            return dbQuery {
                ImportEntity.findById(id)?.toModel()
            }
        }
    }
}