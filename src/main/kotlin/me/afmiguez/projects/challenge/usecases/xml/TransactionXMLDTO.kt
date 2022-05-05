package me.afmiguez.projects.challenge.usecases.xml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import kotlinx.serialization.Serializable
import me.afmiguez.projects.challenge.models.Transaction
import java.time.LocalDateTime

@Serializable
data class AccountDTO(
    @set:JsonProperty( "banco") var bankName:String,
    @set:JsonProperty( "agencia") var branchCode:String,
    @set:JsonProperty( "conta") var accountCode:String
)

@JsonRootName("transacao")
data class TransactionXMLDTO(
    @set:JsonProperty( "origem") var sourceAccount:AccountDTO,
    @set:JsonProperty( "destino") var destinationAccount:AccountDTO,
    @set:JsonProperty( "valor") var amount: Float,
    @set:JsonProperty( "data") var timestamp: String){

    fun toModel() = Transaction(
        sourceBankName = sourceAccount.bankName,
        sourceBranchCode = sourceAccount.branchCode,
        sourceAccountCode = sourceAccount.accountCode,
        destinationBankName = destinationAccount.bankName,
        destinationBranchCode = destinationAccount.branchCode,
        destinationAccountCode = destinationAccount.accountCode,
        amount = amount,
        timestamp = LocalDateTime.parse(timestamp).toLocalDate()
    )
}