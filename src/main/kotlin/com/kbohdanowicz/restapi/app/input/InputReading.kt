package com.kbohdanowicz.restapi.app.input

import com.kbohdanowicz.restapi.app.constants.defaultDateFormatter
import com.kbohdanowicz.restapi.app.input.model.FeeWage
import com.kbohdanowicz.restapi.app.input.model.Transaction
import com.kbohdanowicz.restapi.common.reading.readCSV
import com.kbohdanowicz.restapi.extensions.replaceCommaWithDot
import com.kbohdanowicz.restapi.extensions.toLocalDateTime
import java.math.BigDecimal

fun readFeeWages(path: String): List<FeeWage> =
    readCSV(path).map {
        FeeWage(
            transactionValueLessThanAmount = it[0].replaceCommaWithDot().toBigDecimal(),
            feePercentageOfTransactionValue = it[1].formatAsBigDecimalPercentage(),
        )
    }

fun readTransactions(path: String): List<Transaction> =
    readCSV(path).map {
        Transaction(
            id = it[0].toLong(),
            amount = it[1].replaceCommaWithDot().toBigDecimal(),
            customerFirstName = it[2],
            customerId = it[3].toLong(),
            customerLastName = it[4],
            date = it[5].toLocalDateTime(defaultDateFormatter),
        )
    }

private const val PERCENTAGE_MULTIPLIER = "0.01"

private fun String.formatAsBigDecimalPercentage(): BigDecimal =
    replaceCommaWithDot()
        .toBigDecimal() * PERCENTAGE_MULTIPLIER.toBigDecimal()
