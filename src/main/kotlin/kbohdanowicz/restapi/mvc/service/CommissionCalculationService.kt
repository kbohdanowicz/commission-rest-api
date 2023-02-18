package kbohdanowicz.restapi.mvc.service

import kbohdanowicz.restapi.mvc.domain.CommissionCalculationResponse
import kbohdanowicz.restapi.mongo.repository.CommissionCalculationRepository
import org.springframework.stereotype.Service

@Service
class CommissionCalculationService(private val repository: CommissionCalculationRepository) {
    fun saveCalculation(commissionCalculationResponse: CommissionCalculationResponse) {
        repository.saveCalculation(commissionCalculationResponse)
    }
}