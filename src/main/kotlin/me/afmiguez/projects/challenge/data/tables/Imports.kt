package me.afmiguez.projects.challenge.data.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime

object Imports:IntIdTable() {
    val transactionsDate=date("transactions_date")
    val importDate=datetime("import_date")
}