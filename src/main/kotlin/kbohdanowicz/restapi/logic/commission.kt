package kbohdanowicz.restapi.logic

import kbohdanowicz.restapi.cache.CsvDataCache
import kbohdanowicz.restapi.cache.TransactionsCache
import kbohdanowicz.restapi.logic.read.input.dateFormatter
import kbohdanowicz.restapi.model.CommissionCalculationResponse
import java.math.BigDecimal
import java.math.RoundingMode

fun calculateCommissionsForAllUsers(): List<CommissionCalculationResponse> =
    calculateCommissionsForManyUsers(TransactionsCache.customerIds)

fun calculateCommissionsForManyUsers(userIds: List<Long>): List<CommissionCalculationResponse> =
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
        lastTransactionDate = lastTransaction.date.format(dateFormatter)
    )
}

fun getFeePercentageForAmount(amount: BigDecimal): BigDecimal =
    CsvDataCache.feeWages
        .find { amount < it.transactionValueLessThanAmount }
        ?.feePercentageOfTransactionValue ?: BigDecimal(0)

private fun BigDecimal.roundDefault() =
    setScale(2, RoundingMode.HALF_UP)
