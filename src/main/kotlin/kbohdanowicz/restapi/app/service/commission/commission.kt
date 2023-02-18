package kbohdanowicz.restapi.app.service.commission

import kbohdanowicz.restapi.app.input.cache.CsvDataCache
import kbohdanowicz.restapi.app.input.cache.TransactionsCache
import kbohdanowicz.restapi.app.input.defaultDateFormatter
import kbohdanowicz.restapi.app.model.CommissionCalculationResponse
import java.math.BigDecimal
import java.math.RoundingMode

fun calculateCommissionForManyUsers(userIds: List<Long>): List<CommissionCalculationResponse> =
    userIds.map { calculateCommissionForOneUser(it) }

fun calculateCommissionForOneUser(userId: Long): CommissionCalculationResponse {
    val userTransactions = TransactionsCache[userId]

    val userTransactionValues = userTransactions.map { it.amount }

    val totalTransactionsValue =
        userTransactionValues
            .sumOf { it }
            .roundDefault()

    val totalFeeWagesValue =
        userTransactionValues.fold(BigDecimal(0)) { acc, amount ->
            val fee = amount * getFeePercentageForAmount(amount)
            acc + fee
        }.roundDefault()

    val lastTransaction = userTransactions.last()

    return CommissionCalculationResponse(
        firstName = lastTransaction.customerFirstName,
        lastName = lastTransaction.customerLastName,
        customerId = lastTransaction.customerId,
        numberOfTransactions = userTransactions.size.toLong(),
        totalValueOfTransactions = totalTransactionsValue.toString(),
        transactionsFeeValue = totalFeeWagesValue.toString(),
        lastTransactionDate = lastTransaction.date.format(defaultDateFormatter)
    )
}

private fun getFeePercentageForAmount(amount: BigDecimal): BigDecimal =
    CsvDataCache.feeWages
        .find { amount < it.transactionValueLessThanAmount }
        ?.feePercentageOfTransactionValue ?: BigDecimal(0)

private fun BigDecimal.roundDefault(): BigDecimal =
    setScale(2, RoundingMode.HALF_UP)
