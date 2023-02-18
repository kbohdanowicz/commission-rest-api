package kbohdanowicz.restapi.mvc.controller

import kbohdanowicz.restapi.extensions.toJson
import kbohdanowicz.restapi.mvc.logic.calculateCommissionForOneUser
import kbohdanowicz.restapi.mvc.logic.calculateCommissionForManyUsers
import kbohdanowicz.restapi.mvc.logic.parser.CustomerIdParsingResult
import kbohdanowicz.restapi.mvc.logic.parser.parseCustomerId
import kbohdanowicz.restapi.mvc.service.CommissionCalculationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CommissionController(val service: CommissionCalculationService) {

    companion object {
        const val COMMISSION_ENDPOINT = "/api"
        const val COMMISSION_CUSTOMER_ID_PARAM = "customer_id"

        const val INVALID_CUSTOMER_ID_JSON_MESSAGE = "{\"Error\": \"Customer ID is invalid\"}"
    }

    @GetMapping(COMMISSION_ENDPOINT)
    fun getAllUserCommissions(@RequestParam(COMMISSION_CUSTOMER_ID_PARAM) customerId: String): String =
        when (val parsingResult = parseCustomerId(customerId)) {
            is CustomerIdParsingResult.One ->
                calculateCommissionForOneUser(parsingResult.customerId)
                    .also { service.saveCalculation(it) }
                    .toJson()

            is CustomerIdParsingResult.Many ->
                calculateCommissionForManyUsers(parsingResult.customerIds)
                    .onEach { service.saveCalculation(it) }
                    .toJson()

            is CustomerIdParsingResult.Invalid ->
                INVALID_CUSTOMER_ID_JSON_MESSAGE
                    .toJson()
        }
}