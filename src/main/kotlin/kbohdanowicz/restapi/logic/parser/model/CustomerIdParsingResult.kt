package kbohdanowicz.restapi.logic.parser.model

sealed class CustomerIdParsingResult {
    object All : CustomerIdParsingResult()
    object Invalid : CustomerIdParsingResult()
    class One(val customerId: Long) : CustomerIdParsingResult()
    class Many(val customerIds: List<Long>) : CustomerIdParsingResult()
}