package com.kbohdanowicz.restapi.app.service.calculation

import com.kbohdanowicz.restapi.app.model.CommissionCalculationResponse

interface CommissionCalculationService {

    fun calculateCommissionForManyUsers(userIds: List<Long>): List<CommissionCalculationResponse>

    fun calculateCommissionForOneUser(userId: Long): CommissionCalculationResponse
}
