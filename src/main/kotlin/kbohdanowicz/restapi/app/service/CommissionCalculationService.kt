package kbohdanowicz.restapi.app.service

import kbohdanowicz.restapi.app.model.CommissionCalculationResponse
import kbohdanowicz.restapi.app.service.repository.CommissionCalculationRepository
import org.springframework.stereotype.Service

@Service
class CommissionCalculationService(private val repository: CommissionCalculationRepository) {
    fun saveCalculation(commissionCalculationResponse: CommissionCalculationResponse) {
        repository.saveCalculation(commissionCalculationResponse)
    }
}