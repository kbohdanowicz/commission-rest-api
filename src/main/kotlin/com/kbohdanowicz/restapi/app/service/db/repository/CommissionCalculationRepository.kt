package com.kbohdanowicz.restapi.app.service.db.repository

import com.kbohdanowicz.restapi.app.model.CommissionCalculationResponse

interface CommissionCalculationRepository {
    fun saveCalculation(commissionCalculationResponse: CommissionCalculationResponse)
}
