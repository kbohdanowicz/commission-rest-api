package com.kbohdanowicz.restapi.app.service.repository

import com.kbohdanowicz.restapi.app.model.CommissionCalculationResponse

interface CommissionCalculationRepository {
    fun saveCalculation(commissionCalculationResponse: CommissionCalculationResponse)
}
