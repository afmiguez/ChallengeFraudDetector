package me.afmiguez.projects.challenge.models

import kotlinx.serialization.Serializable
import me.afmiguez.projects.challenge.routes.dtos.TransactionDTO

@Serializable
class Suspicious(

) {
    val transactions = ArrayList<TransactionDTO>()
    val branches = ArrayList<SuspiciousBranch>()
    val accounts = ArrayList<SuspiciousAccount>()
}