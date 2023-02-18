package com.kbohdanowicz.restapi.app.service

import com.kbohdanowicz.restapi.app.model.CommissionCalculationResponse
import com.kbohdanowicz.restapi.app.service.repository.CommissionCalculationRepository
import org.springframework.stereotype.Service

@Service
class CommissionCalculationService(private val repository: CommissionCalculationRepository) {
    fun saveCalculation(commissionCalculationResponse: CommissionCalculationResponse) {
        repository.saveCalculation(commissionCalculationResponse)
    }
}
