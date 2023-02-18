package kbohdanowicz.restapi.app.input.cache

import kbohdanowicz.restapi.app.input.model.Transaction

object TransactionsCache {

    operator fun get(id: Long): List<Transaction> =
        userIdsByTransactions[id]!!

    fun isCustomerIdValid(customerId: Long) =
        customerId in customerIds

    val customerIds =
        CsvDataCache.transactions
            .map { it.customerId }
            .distinct()

    private val userIdsByTransactions =
        CsvDataCache.transactions
            .groupBy { it.customerId }
            .map { (customerId, transactions) ->
                customerId to transactions.sortedBy { it.date }
            }
            .toMap()
}