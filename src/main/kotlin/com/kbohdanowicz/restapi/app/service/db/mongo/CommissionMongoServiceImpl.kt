package com.kbohdanowicz.restapi.app.service.db.mongo

import com.kbohdanowicz.restapi.app.model.CommissionCalculationResponse
import com.kbohdanowicz.restapi.app.service.db.repository.CommissionCalculationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommissionMongoServiceImpl(
    @Autowired private val repository: CommissionCalculationRepository,
) : CommissionMongoService {
    override fun saveCalculation(commissionCalculationResponse: CommissionCalculationResponse) {
        repository.saveCalculation(commissionCalculationResponse)
    }
}
