package me.afmiguez.projects.challenge.data

import me.afmiguez.projects.challenge.data.tables.Imports
import me.afmiguez.projects.challenge.data.tables.Transactions
import me.afmiguez.projects.challenge.data.tables.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun createTables() {
    transaction {
        SchemaUtils.create(Transactions)
        SchemaUtils.create(Imports)
        SchemaUtils.create(Users)

    }
}