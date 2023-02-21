package com.kbohdanowicz.restapi.app.service.parsing

import com.kbohdanowicz.restapi.app.input.cache.TransactionsCache
import com.kbohdanowicz.restapi.app.service.parsing.model.CustomerIdParsingResult
import org.springframework.stereotype.Service

@Service
class CustomerIdParsingServiceImpl : CustomerIdParsingService {

    companion object {
        private const val ID_SEPARATOR = ","
        private val allCustomerIdsTokens = listOf("", "ALL")
    }

    override fun parseCustomerId(customerId: String): CustomerIdParsingResult =
        when {
            customerId.contains(ID_SEPARATOR) ->
                parseAsListOfLongs(customerId)?.let {
                    parseManyCustomerIds(it)
                } ?: CustomerIdParsingResult.Invalid

            customerId in allCustomerIdsTokens ->
                CustomerIdParsingResult.Many(TransactionsCache.customerIds)

            customerId.toLongOrNull() != null ->
                parseOneCustomerId(customerId.toLong())

            else ->
                CustomerIdParsingResult.Invalid
        }

    private fun parseManyCustomerIds(customerIds: List<Long>): CustomerIdParsingResult {
        val validCustomerIds = customerIds.filter { TransactionsCache.isCustomerIdValid(it) }
        return when {
            validCustomerIds.isEmpty() ->
                CustomerIdParsingResult.Invalid
            validCustomerIds.size == 1 ->
                CustomerIdParsingResult.One(validCustomerIds.first())
            else ->
                CustomerIdParsingResult.Many(validCustomerIds)
        }
    }

    private fun parseOneCustomerId(customerId: Long): CustomerIdParsingResult =
        if (TransactionsCache.isCustomerIdValid(customerId)) {
            CustomerIdParsingResult.One(customerId)
        } else {
            CustomerIdParsingResult.Invalid
        }

    private fun parseAsListOfLongs(string: String): List<Long>? =
        if (string.contains(",")) {
            string.split(',')
                .map {
                    it.trim().toLongOrNull() ?: return null
                }
        } else {
            null
        }
}
