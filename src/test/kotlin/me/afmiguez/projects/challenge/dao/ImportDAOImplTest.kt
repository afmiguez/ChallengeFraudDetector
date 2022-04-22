package me.afmiguez.projects.challenge.dao



import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import me.afmiguez.projects.challenge.di.koinModules
import me.afmiguez.projects.challenge.models.Import
import me.afmiguez.projects.challenge.plugins.configureDatabaseTest
import org.junit.After
import org.junit.BeforeClass
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ImportDAOImplTest : KoinTest {

    private val importDAO: ImportDAO by inject()

    companion object{

        @BeforeClass
        @JvmStatic
        fun setup() {
            testApplication {
                application {
                    configureDatabaseTest()
                }
            }
            startKoin {
                modules(
                    koinModules
                )
            }
        }
    }



    @After
    fun tearDown(){
        println("Finished")
    }

    @Test
    fun save() {
        runBlocking {

            val result = importDAO.save(
                Import(
                    importDate = LocalDateTime.now(),
                    transactionsDate = LocalDate.now()
                )
            )

            assertNotNull(result)

        }

    }

    @Test
    fun getAllImports() {
        runBlocking {
            val result = importDAO.getAllImports()
            assertEquals(0, result.size)

            importDAO.save(
                Import(
                    importDate = LocalDateTime.now(),
                    transactionsDate = LocalDate.now()
                )
            )
            val resultAfterSave = importDAO.getAllImports()

            assertEquals(1, resultAfterSave.size)

        }
    }
}