package com.kbohdanowicz.restapi.app.input.cache

import com.kbohdanowicz.restapi.app.input.readFeeWages
import com.kbohdanowicz.restapi.app.input.readTransactions

object CsvDataCache {

    private const val TRANSACTIONS_INPUT_PATH = "input/transactions.csv"
    private const val FEE_WAGES_INPUT_PATH = "input/fee_wages.csv"

    val transactions = readTransactions(TRANSACTIONS_INPUT_PATH)

    val feeWages = readFeeWages(FEE_WAGES_INPUT_PATH)
        .sortedBy { it.transactionValueLessThanAmount }
}
