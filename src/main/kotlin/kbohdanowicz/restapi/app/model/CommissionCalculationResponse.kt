package kbohdanowicz.restapi.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommissionCalculationResponse(
    @SerialName("First name")
    val firstName: String,

    @SerialName("Last name")
    val lastName: String,

    @SerialName("Customer ID")
    val customerId: Long,

    @SerialName("Number of transactions")
    val numberOfTransactions: Long,

    @SerialName("Total value of transactions")
    val totalValueOfTransactions: String,

    @SerialName("Transactions fee value")
    val transactionsFeeValue: String,

    @SerialName("Last transaction date")
    val lastTransactionDate: String,
)
