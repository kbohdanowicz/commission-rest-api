package kbohdanowicz.restapi.mvc.logic.parser

import kbohdanowicz.restapi.cache.TransactionsCache

private const val ID_SEPARATOR = ","
private val allCustomerIdsTokens = listOf("", "ALL")

fun parseCustomerId(customerId: String): CustomerIdParsingResult =
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

private fun parseManyCustomerIds(customerIds: List<Long>): CustomerIdParsingResult =
    if (customerIds.all { TransactionsCache.isCustomerIdValid(it) })
        CustomerIdParsingResult.Many(customerIds)
    else
        CustomerIdParsingResult.Invalid

private fun parseOneCustomerId(customerId: Long): CustomerIdParsingResult =
    if (TransactionsCache.isCustomerIdValid(customerId))
        CustomerIdParsingResult.One(customerId)
    else
        CustomerIdParsingResult.Invalid


private fun parseAsListOfLongs(string: String): List<Long>? =
    if (string.contains(",")) {
        string.split(',')
            .map {
                it.trim().toLongOrNull() ?: return null
            }
    } else {
        null
    }
