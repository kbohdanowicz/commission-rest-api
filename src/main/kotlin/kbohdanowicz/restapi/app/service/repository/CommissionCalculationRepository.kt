package kbohdanowicz.restapi.app.service.repository

import kbohdanowicz.restapi.app.model.CommissionCalculationResponse

interface CommissionCalculationRepository {
    fun saveCalculation(commissionCalculationResponse: CommissionCalculationResponse)
}