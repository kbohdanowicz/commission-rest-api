package com.kbohdanowicz.restapi.app.service.db.mongo

import com.kbohdanowicz.restapi.app.model.CommissionCalculationResponse

interface CommissionMongoService {
    fun saveCalculation(commissionCalculationResponse: CommissionCalculationResponse)
}
