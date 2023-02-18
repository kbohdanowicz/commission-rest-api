package kbohdanowicz.restapi.app.service.repository

import kbohdanowicz.restapi.extensions.toLocalDateTime
import kbohdanowicz.restapi.app.input.defaultDateFormatter
import kbohdanowicz.restapi.app.model.CommissionCalculation
import kbohdanowicz.restapi.app.model.CommissionCalculationResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class CommissionCalculationRepositoryImpl : CommissionCalculationRepository {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun saveCalculation(commissionCalculationResponse: CommissionCalculationResponse) {
        with(commissionCalculationResponse) {
            val calculationLog = CommissionCalculation(
                customerId = customerId,
                commission = transactionsFeeValue.toBigDecimal(),
                dateOfCalculation = lastTransactionDate.toLocalDateTime(defaultDateFormatter),
            )
            mongoTemplate.save(calculationLog)
        }
    }
}