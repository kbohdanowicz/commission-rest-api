package kbohdanowicz.restapi.app.input.model

import java.math.BigDecimal

data class FeeWage(
    val transactionValueLessThanAmount: BigDecimal,
    val feePercentageOfTransactionValue: BigDecimal,
)