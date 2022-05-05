package me.afmiguez.projects.challenge.usecases.xml


import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import me.afmiguez.projects.challenge.models.Transaction
import java.io.InputStream


fun xmlToTransactionList(stream: InputStream): List<Transaction> {

    return XmlMapper(
        JacksonXmlModule().apply { setDefaultUseWrapper(false) }
    ).registerKotlinModule()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .readValue<List<TransactionXMLDTO>>(stream)
        .map { it.toModel() }


}
