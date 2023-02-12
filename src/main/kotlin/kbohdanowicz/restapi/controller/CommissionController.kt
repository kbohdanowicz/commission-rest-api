package kbohdanowicz.restapi.controller

import kbohdanowicz.restapi.logic.calculateCommissionForOneUser
import kbohdanowicz.restapi.logic.calculateCommissionsForAllUsers
import kbohdanowicz.restapi.logic.calculateCommissionsForManyUsers
import kbohdanowicz.restapi.logic.parser.model.CustomerIdParsingResult
import kbohdanowicz.restapi.logic.parser.parseCustomerId
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CommissionController {
    companion object {
        const val COMMISSION_ENDPOINT = "/api"
        const val COMMISSION_CUSTOMER_ID_PARAM = "customer_id"
    }

    @GetMapping(COMMISSION_ENDPOINT)
    fun getAllUserCommissions(@RequestParam(COMMISSION_CUSTOMER_ID_PARAM) customerId: String): String {
        val invalidCustomerIdMessage = "Error: Customer ID is invalid"
        val result =
            when (val parsingResult = parseCustomerId(customerId)) {
                is CustomerIdParsingResult.One ->
                    Json.encodeToString(calculateCommissionForOneUser(parsingResult.customerId))

                is CustomerIdParsingResult.Many ->
                    Json.encodeToString(calculateCommissionsForManyUsers(parsingResult.customerIds))

                is CustomerIdParsingResult.All ->
                    Json.encodeToString(calculateCommissionsForAllUsers())

                is CustomerIdParsingResult.Invalid ->
                    Json.encodeToString(invalidCustomerIdMessage)
            }

        return result
    }
}