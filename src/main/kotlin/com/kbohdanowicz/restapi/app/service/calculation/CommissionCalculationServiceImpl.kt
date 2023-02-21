package com.kbohdanowicz.restapi.app.service.calculation

import com.kbohdanowicz.restapi.app.constants.defaultDateFormatter
import com.kbohdanowicz.restapi.app.input.cache.CsvDataCache
import com.kbohdanowicz.restapi.app.input.cache.TransactionsCache
import com.kbohdanowicz.restapi.app.model.CommissionCalculationResponse
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class CommissionCalculationServiceImpl : CommissionCalculationService {

    override fun calculateCommissionForManyUsers(userIds: List<Long>): List<CommissionCalculationResponse> =
        userIds.map { calculateCommissionForOneUser(it) }

    override fun calculateCommissionForOneUser(userId: Long): CommissionCalculationResponse {
        val userTransactions = TransactionsCache[userId]

        val userTransactionValues = userTransactions.map { it.amount }

        val totalTransactionsValue =
            userTransactionValues
                .sumOf { it }
                .roundDefault()

        val totalFeeWagesValue =
            userTransactionValues
                .sumOf { calculateFee(it) }
                .roundDefault()

        val lastTransaction = userTransactions.last()

        return CommissionCalculationResponse(
            firstName = lastTransaction.customerFirstName,
            lastName = lastTransaction.customerLastName,
            customerId = lastTransaction.customerId,
            numberOfTransactions = userTransactions.size.toLong(),
            totalValueOfTransactions = totalTransactionsValue.toString(),
            transactionsFeeValue = totalFeeWagesValue.toString(),
            lastTransactionDate = lastTransaction.date.format(defaultDateFormatter),
        )
    }

    private fun calculateFee(amount: BigDecimal): BigDecimal {
        val feePercentage = CsvDataCache.feeWages
            .find { amount < it.transactionValueLessThanAmount }
            ?.feePercentageOfTransactionValue ?: BigDecimal(0)
        return amount * feePercentage
    }

    private fun BigDecimal.roundDefault(): BigDecimal =
        setScale(2, RoundingMode.HALF_UP)
}
