package me.afmiguez.projects.challenge.dao

//import me.afmiguez.projects.challenge.plugins.configureDatabaseTest
import io.ktor.server.testing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import me.afmiguez.projects.challenge.data.entities.TransactionEntity
import me.afmiguez.projects.challenge.data.tables.Imports
import me.afmiguez.projects.challenge.data.tables.Transactions
import me.afmiguez.projects.challenge.data.tables.Users
import me.afmiguez.projects.challenge.di.koinModules
import me.afmiguez.projects.challenge.models.Import
import me.afmiguez.projects.challenge.models.Transaction
import me.afmiguez.projects.challenge.models.User
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class TransactionsDAOImplTest : KoinTest {

    //lazily injects DAO dependency after koin has been started
    private val transactionsDAO: TransactionsDAO by inject()

    companion object {


        /**
         * Configure database infrastructure for all tests
         */
        @BeforeClass
        @JvmStatic
        fun setup() {

            //executes ktor module for test database
            testApplication {
                application {
//                    configureDatabaseTest()
                }
            }

            //executes koin module to inject DAO dependencies
            startKoin {
                modules(
                    koinModules
                )
            }

            populateTestDatabase()
        }

        private fun populateTestDatabase() {
            val user = User(
                name = "name1",
                email = "email1",
                password = "123"
            )

            val userId = transaction {
                Users.insert {
                    it[name] = user.name
                    it[email] = user.email
                    it[password] = user.password
                    it[enable] = user.isEnable
                }
            }

            val imports = listOf(
                Import(
                    importDate = LocalDateTime.now(),
                    transactionsDate = LocalDate.now(),
                    transactions = listOf(
                        Transaction(
                            "bank1",
                            "branch1",
                            "account1",
                            "bank1",
                            "branch2",
                            "account2",
                            90_000f,
                            LocalDate.now(),
                        ),
                        Transaction(
                            "bank1",
                            "branch1",
                            "account1",
                            "bank1",
                            "branch2",
                            "account2",
                            100_000f,
                            LocalDate.now(),
                        ),
                    )
                ),
                Import(
                    importDate = LocalDateTime.now().plusMonths(1),
                    transactionsDate = LocalDate.now().plusMonths(1),
                    transactions = listOf(
                        Transaction(
                            "bank1",
                            "branch1",
                            "account1",
                            "bank1",
                            "branch3",
                            "account3",
                            1_000_000f,
                            LocalDate.now().plusMonths(1),
                        ),
                        Transaction(
                            "bank1",
                            "branch1",
                            "account1",
                            "bank1",
                            "branch4",
                            "account4",
                            1_000_000f,
                            LocalDate.now().plusMonths(1),
                        ),
                    )
                ),
            )

            imports.forEach { currentImport ->
                val importId = transaction {
                    Imports.insert {
                        it[importDate] = currentImport.importDate
                        it[transactionsDate] = currentImport.transactionsDate
                        it[Imports.user] = userId.insertedCount
                    }
                }
                currentImport.transactions.forEach { currentTransaction ->
                    transaction {
                        Transactions.insert {
                            it[import] = importId.insertedCount
                            it[sourceBankName] = currentTransaction.sourceBankName
                            it[sourceAccountCode] = currentTransaction.sourceAccountCode
                            it[sourceBranchCode] = currentTransaction.sourceBranchCode
                            it[destinationBankName] = currentTransaction.destinationBankName
                            it[destinationAccountCode] = currentTransaction.destinationAccountCode
                            it[destinationBranchCode] = currentTransaction.destinationBranchCode
                            it[amount] = currentTransaction.amount
                            it[timestamp] = currentTransaction.timestamp
                        }
                    }
                }
            }
        }

        /**
         * stop Koin in this class test (it could be used again in another class)
         */
        @AfterClass
        @JvmStatic
        fun finish() {
            stopKoin()
        }
    }

    @Test
    fun whenGetTransactionsGreaterThan100kForAMonth_returnsListWith1() {
        runTest {
            val month = LocalDate.now()
            assertEquals(1, transactionsDAO.transactionsGreaterThan100_000(month).size)
        }
    }

    @Test
    fun whenGetTransactionsGreaterThan100kForAMonth_returnsListWith2() {
        runTest {
            val month = LocalDate.now().plusMonths(1)
            assertEquals(2, transactionsDAO.transactionsGreaterThan100_000(month).size)
        }
    }

    @Test
    fun whenGetTransactionsGreaterThan100kForAMonth_returnsListWith0() {
        runTest {
            val month = LocalDate.now().plusMonths(2)
            assertEquals(0, transactionsDAO.transactionsGreaterThan100_000(month).size)
        }
    }

    @Test
    fun whenGetSuspiciousAccountsForAMonth_returnsListWith0() {
        runTest {
            val month = LocalDate.now()
            assertEquals(0, transactionsDAO.suspiciousAccounts(month).size)
        }
    }

    @Test
    fun whenGetSuspiciousAccountsForAMonth_returnsListWith1() {
        runTest {
            val month = LocalDate.now().plusMonths(1)
            assertEquals(3, transactionsDAO.suspiciousAccounts(month).size)
        }
    }

    @Test
    fun whenGetSuspiciousBranchesForAMonth_returnsListWith0() {
        runTest {
            val month = LocalDate.now()
            assertEquals(0, transactionsDAO.suspiciousBranches(month).size)
        }
    }

    @Test
    fun whenGetSuspiciousBranchesForAMonth_returnsListWith1() {
        runTest {
            val month = LocalDate.now().plusMonths(1)
            assertEquals(3, transactionsDAO.suspiciousBranches(month).size)
        }
    }

//    @Test
//    fun hasTransactionsForDay() {

//        runBlocking {
//
//            assertEquals(0, transactionsDAO.getAllTransactions().size)
//            val transactionValid
//                =Transaction(
//                    "sBank",
//                    "sBranch",
//                    "sAccount",
//                    "dBank",
//                    "dBranch",
//                    "dAccount",
//                    10f,
//                    LocalDate.now(),
//            )
//
//            val referenceDate=LocalDate.now()
//            assertFalse(transactionsDAO.hasTransactionsForDay(referenceDate))
//            //transactionsDAO.save(transactionValid)
////            assertTrue(transactionsDAO.hasTransactionsForDay(referenceDate))
//
//
////            assertEquals(1, transactionsDAO.getAllTransactions().size)
//        }
//    }
}