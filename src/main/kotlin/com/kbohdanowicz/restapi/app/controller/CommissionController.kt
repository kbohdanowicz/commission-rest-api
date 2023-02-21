package com.kbohdanowicz.restapi.app.controller

import com.kbohdanowicz.restapi.app.service.calculation.CommissionCalculationService
import com.kbohdanowicz.restapi.app.service.db.mongo.CommissionMongoService
import com.kbohdanowicz.restapi.app.service.parsing.CustomerIdParsingService
import com.kbohdanowicz.restapi.app.service.parsing.model.CustomerIdParsingResult
import com.kbohdanowicz.restapi.extensions.toJson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CommissionController(
    @Autowired private val parsingService: CustomerIdParsingService,
    @Autowired private val commissionCalculationService: CommissionCalculationService,
    @Autowired private val mongoService: CommissionMongoService,
) {

    companion object {
        const val COMMISSION_ENDPOINT = "/api"
        const val COMMISSION_CUSTOMER_ID_PARAM = "customer_id"

        const val INVALID_CUSTOMER_ID_JSON_MESSAGE = "{\"Error\": \"Customer ID is invalid\"}"
    }

    @GetMapping(COMMISSION_ENDPOINT)
    fun getUserCommission(@RequestParam(COMMISSION_CUSTOMER_ID_PARAM) customerId: String): String =
        when (val parsingResult = parsingService.parseCustomerId(customerId)) {
            is CustomerIdParsingResult.One ->
                commissionCalculationService
                    .calculateCommissionForOneUser(parsingResult.customerId)
                    .also { mongoService.saveCalculation(it) }
                    .toJson()

            is CustomerIdParsingResult.Many ->
                commissionCalculationService
                    .calculateCommissionForManyUsers(parsingResult.customerIds)
                    .onEach { mongoService.saveCalculation(it) }
                    .toJson()

            is CustomerIdParsingResult.Invalid ->
                INVALID_CUSTOMER_ID_JSON_MESSAGE
                    .toJson()
        }
}