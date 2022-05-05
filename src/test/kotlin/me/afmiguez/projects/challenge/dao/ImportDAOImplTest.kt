package me.afmiguez.projects.challenge.dao


import io.ktor.server.testing.*

import kotlinx.coroutines.runBlocking
import me.afmiguez.projects.challenge.data.tables.Transactions
import me.afmiguez.projects.challenge.di.koinModules
import me.afmiguez.projects.challenge.models.Import
import me.afmiguez.projects.challenge.models.User
//import me.afmiguez.projects.challenge.plugins.configureDatabaseTest
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class ImportDAOImplTest : KoinTest {

    private val userDAO:UserDAO by inject()
    private val importDAO: ImportDAO by inject()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            testApplication {
                application {
//                    configureDatabaseTest()
                }
            }
            startKoin {
                modules(
                    koinModules
                )
            }
        }

        @AfterClass
        @JvmStatic
        fun finish() {
            stopKoin()
        }
    }

    @After
    fun tearDown() {
        transaction {
            Transactions.deleteAll()
        }
    }

    @Test
    fun getAllImports() {
        runBlocking {

            userDAO.createUser(
                User(
                   name = "userName",
                   email = "userEmail",
                   password = "12345"
                )
            )
            val result = importDAO.getAllImports()
            assertEquals(0, result.size)

            importDAO.save(
                Import(
                    importDate = LocalDateTime.now(),
                    transactionsDate = LocalDate.now()
                ),
                userEmail = "userEmail",

            )
            val resultAfterSave = importDAO.getAllImports()
            assertEquals(1, resultAfterSave.size)

        }
    }

}