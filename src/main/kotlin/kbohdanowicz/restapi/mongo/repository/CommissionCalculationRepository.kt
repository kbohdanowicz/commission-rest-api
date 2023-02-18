package kbohdanowicz.restapi.mongo.repository

import kbohdanowicz.restapi.mvc.domain.CommissionCalculationResponse

interface CommissionCalculationRepository {
    fun saveCalculation(commissionCalculationResponse: CommissionCalculationResponse)
}