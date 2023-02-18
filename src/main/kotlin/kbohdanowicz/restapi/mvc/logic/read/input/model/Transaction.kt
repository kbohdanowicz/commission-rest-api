package kbohdanowicz.restapi.mvc.logic.read.input.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
    val id: Long,
    val amount: BigDecimal,
    val customerFirstName: String,
    val customerId: Long,
    val customerLastName: String,
    val date: LocalDateTime,
)