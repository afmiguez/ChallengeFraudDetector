package me.afmiguez.projects.challenge.usecases

import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import me.afmiguez.projects.challenge.dao.ImportDAO
import me.afmiguez.projects.challenge.dao.TransactionsDAO
import me.afmiguez.projects.challenge.models.Transaction
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

class TransactionsServiceImplTest {


    val transactionsDao = mockk<TransactionsDAO>(relaxed = true)
    val importDAO = mockk<ImportDAO>(relaxed = true)

    val SUT = TransactionsServiceImpl(transactionsDao, importDAO)

    @Test
    fun whenTransactionGreaterThan100k_includesOnList() {
        runTest {

            val month = LocalDate.now()

            coEvery { transactionsDao.transactionsGreaterThan100_000(month) } returns listOf(
                Transaction(
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    100_000f,
                    LocalDate.now()
                ),
            )


            assertEquals("",1, SUT.suspiciousTransactions(month).transactions.size)

            assertEquals(
                0, SUT.suspiciousTransactions(
                    month.plusMonths(1)
                ).transactions.size
            )

        }


    }

}