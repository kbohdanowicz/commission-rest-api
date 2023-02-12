package kbohdanowicz.restapi.logic.parser

import kbohdanowicz.restapi.cache.TransactionsCache
import kbohdanowicz.restapi.logic.parser.model.CustomerIdParsingResult

fun parseCustomerId(customerId: String): CustomerIdParsingResult =
    when {
        customerId.contains(ID_SEPARATOR) ->
            customerId.toListOfLongs()?.let {
                parseManyCustomerIds(it)
            } ?: CustomerIdParsingResult.Invalid

        customerId in allIdValues ->
            CustomerIdParsingResult.All

        customerId.toLongOrNull() != null -> {
            parseOneCustomerId(customerId.toLong())
        }

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


private fun String.toListOfLongs(): List<Long>? =
    if (this.contains(",")) {
        this.split(',')
            .map {
                it.trim().toLongOrNull() ?: return null
            }
    } else {
        null
    }

private const val ID_SEPARATOR = ","
private val allIdValues: List<String> = listOf("", "ALL")
