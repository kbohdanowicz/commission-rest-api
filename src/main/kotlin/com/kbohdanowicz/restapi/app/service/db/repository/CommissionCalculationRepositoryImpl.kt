package com.kbohdanowicz.restapi.app.service.db.repository

import com.kbohdanowicz.restapi.app.model.CommissionCalculation
import com.kbohdanowicz.restapi.app.model.CommissionCalculationResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CommissionCalculationRepositoryImpl(
    @Autowired private val mongoTemplate: MongoTemplate,
) : CommissionCalculationRepository {

    override fun saveCalculation(commissionCalculationResponse: CommissionCalculationResponse) {
        with(commissionCalculationResponse) {
            val calculationLog = CommissionCalculation(
                customerId = customerId,
                commission = transactionsFeeValue.toBigDecimal(),
                dateOfCalculation = LocalDateTime.now(),
            )
            mongoTemplate.save(calculationLog)
        }
    }
}
