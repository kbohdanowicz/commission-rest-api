package kbohdanowicz.restapi.app.controller.parsing

sealed class CustomerIdParsingResult {
    object Invalid : CustomerIdParsingResult()
    class One(val customerId: Long) : CustomerIdParsingResult()
    class Many(val customerIds: List<Long>) : CustomerIdParsingResult()
}