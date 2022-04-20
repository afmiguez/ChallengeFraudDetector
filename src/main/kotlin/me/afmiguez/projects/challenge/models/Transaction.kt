package me.afmiguez.projects.challenge.models


import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.LocalDate
import java.time.LocalDateTime



class Transaction(
    val sourceBankName: String, val sourceBranchCode: String, val sourceAccountCode: String,
    val destinationBankName: String, val destinationBranchCode: String, val destinationAccountCode: String,
    val amount: Float, val timestamp: LocalDate
) {

    /*@Transient
    val time=try{
        LocalDateTime.parse(timestamp)
    }catch (e:Exception){
        null
    }*/
    fun isValid(referenceDate: LocalDate): Boolean {

        return sourceBankName.isNotBlank() && sourceBranchCode.isNotBlank() && sourceAccountCode.isNotBlank()
                && destinationBankName.isNotBlank() && destinationBranchCode.isNotBlank() && destinationAccountCode.isNotBlank()
                && amount>0f && referenceDate == timestamp
    }

}

//data class Account(val bankName:String, val branchCode:String, val accountCode:String)