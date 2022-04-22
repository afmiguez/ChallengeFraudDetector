package me.afmiguez.projects.challenge.data.entities

import me.afmiguez.projects.challenge.data.tables.Imports
import me.afmiguez.projects.challenge.models.Import
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction

class ImportEntity(id: EntityID<Int>) : IntEntity(id) {
    var importDate by Imports.importDate
    var transactionsDate by Imports.transactionsDate

    fun toModel() = Import(importDate, transactionsDate)

    companion object : IntEntityClass<ImportEntity>(Imports) {
        fun save(import: Import): Import {
            return transaction {
                ImportEntity.new {
                    importDate = import.importDate
                    transactionsDate = import.transactionsDate
                }.toModel()
            }
        }

        fun getAll(): List<Import> {
            return transaction {
                ImportEntity.all()
                    .orderBy(Imports.transactionsDate to SortOrder.DESC)
                    .toList().map { it.toModel() }
            }
        }
    }
}