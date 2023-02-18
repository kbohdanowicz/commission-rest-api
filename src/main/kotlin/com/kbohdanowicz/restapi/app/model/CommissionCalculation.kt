package com.kbohdanowicz.restapi.app.model

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.math.BigDecimal
import java.time.LocalDateTime

@Document
data class CommissionCalculation(
    val customerId: Long,

    @Field(targetType = FieldType.DECIMAL128)
    val commission: BigDecimal,

    @Field(targetType = FieldType.DATE_TIME)
    val dateOfCalculation: LocalDateTime,
)
