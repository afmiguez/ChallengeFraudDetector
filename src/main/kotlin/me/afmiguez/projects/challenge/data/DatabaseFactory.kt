package me.afmiguez.projects.challenge.data


import kotlinx.coroutines.Dispatchers
import me.afmiguez.projects.challenge.data.tables.Transactions


import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db"

        val user="sa"
        val password=""
        val database = Database.connect(jdbcURL, driverClassName, user = user,password=password)

        transaction(database) {
            SchemaUtils.create(Transactions)
//            SchemaUtils.create(Customers)
//            SchemaUtils.create(Orders)
//            SchemaUtils.create(OrderItems)

        }

    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }


}