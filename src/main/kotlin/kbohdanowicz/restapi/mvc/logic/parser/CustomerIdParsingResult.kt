package kbohdanowicz.restapi.mvc.logic.parser

sealed class CustomerIdParsingResult {
    object Invalid : CustomerIdParsingResult()
    class One(val customerId: Long) : CustomerIdParsingResult()
    class Many(val customerIds: List<Long>) : CustomerIdParsingResult()
}