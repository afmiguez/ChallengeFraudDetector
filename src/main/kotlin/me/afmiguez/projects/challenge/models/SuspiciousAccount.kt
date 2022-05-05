package me.afmiguez.projects.challenge.models

import kotlinx.serialization.Serializable

@Serializable
data class SuspiciousAccount(
    val bankName: String,
    val branchCode: String,
    val accountCode: String,
    val amount: Float,
    val typeMovimentation: String
) {
}