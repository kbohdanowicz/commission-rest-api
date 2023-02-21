package com.kbohdanowicz.restapi.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommissionCalculationResponse(
    @SerialName(FIRST_NAME)
    val firstName: String,

    @SerialName(LAST_NAME)
    val lastName: String,

    @SerialName(CUSTOMER_ID)
    val customerId: Long,

    @SerialName(NUMBER_OF_TRANSACTIONS)
    val numberOfTransactions: Long,

    @SerialName(TOTAL_VALUE_OF_TRANSACTIONS)
    val totalValueOfTransactions: String,

    @SerialName(TRANSACTIONS_FEE_VALUE)
    val transactionsFeeValue: String,

    @SerialName(LAST_TRANSACTION_DATE)
    val lastTransactionDate: String,
) {
    companion object {
        const val FIRST_NAME = "First name"
        const val LAST_NAME = "Last name"
        const val CUSTOMER_ID = "Customer ID"
        const val NUMBER_OF_TRANSACTIONS = "Number of transactions"
        const val TOTAL_VALUE_OF_TRANSACTIONS = "Total value of transactions"
        const val TRANSACTIONS_FEE_VALUE = "Transactions fee value"
        const val LAST_TRANSACTION_DATE = "Last transaction date"
    }
}
