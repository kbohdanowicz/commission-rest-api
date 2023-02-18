package kbohdanowicz.restapi.mvc.logic.read.input

import kbohdanowicz.restapi.extensions.replaceCommaWithDot
import kbohdanowicz.restapi.extensions.toLocalDateTime
import kbohdanowicz.restapi.mvc.logic.read.input.model.FeeWage
import kbohdanowicz.restapi.mvc.logic.read.input.model.Transaction
import kbohdanowicz.restapi.mvc.logic.read.readCSV
import java.time.format.DateTimeFormatter
import java.util.*

fun readFeeWages(path: String): List<FeeWage> =
    readCSV(path).map {
        FeeWage(
            transactionValueLessThanAmount = it[0].replaceCommaWithDot().toBigDecimal(),
            feePercentageOfTransactionValue = it[1].formatAsPercentageBigDecimal(),
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

val defaultDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

private const val PERCENTAGE_MULTIPLIER = "0.01"

private fun String.formatAsPercentageBigDecimal() =
    replaceCommaWithDot()
        .toBigDecimal() * PERCENTAGE_MULTIPLIER.toBigDecimal()
