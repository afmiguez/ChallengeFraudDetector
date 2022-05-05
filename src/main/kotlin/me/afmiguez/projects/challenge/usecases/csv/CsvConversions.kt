package me.afmiguez.projects.challenge.usecases.csv

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import me.afmiguez.projects.challenge.models.Transaction
import me.afmiguez.projects.challenge.routes.dtos.TransactionDTO
import java.io.InputStream

val csvMapper = CsvMapper().apply {
    registerModule(
        KotlinModule.Builder()

            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, true)
            .configure(KotlinFeature.NullToEmptyMap, true)
            .configure(KotlinFeature.NullIsSameAsDefault, true)
            .configure(KotlinFeature.SingletonSupport, false)
            .configure(KotlinFeature.StrictNullChecks, false)
            .build()
    )
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
}
private fun transactionSchema():CsvSchema{
    return CsvSchema.Builder()
        .addColumn("sourceBankName")
        .addColumn("sourceBranchCode")
        .addColumn("sourceAccountCode")
        .addColumn("destinationBankName")
        .addColumn("destinationBranchCode")
        .addColumn("destinationAccountCode")
        .addColumn("amount",CsvSchema.ColumnType.NUMBER)
        .addColumn("timestamp")
        .build()
}

fun csvToTransactionList(stream: InputStream):List<Transaction>{
    return readCsvStream<TransactionDTO>(stream, schema = transactionSchema()).map { it.toModel() }
}

//based on https://medium.com/att-israel/jackson-csv-reader-writer-using-kotlin-f37ae771bd6d
inline fun <reified T> readCsvStream(stream: InputStream,schema: CsvSchema= CsvSchema.emptySchema()): List<T> {
    return csvMapper.readerFor(T::class.java)
        .with(schema)
        .readValues<T>(stream)
        .readAll()
        .toList()
}

