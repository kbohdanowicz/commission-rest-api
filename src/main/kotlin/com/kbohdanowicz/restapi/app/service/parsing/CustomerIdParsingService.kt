package com.kbohdanowicz.restapi.app.service.parsing

import com.kbohdanowicz.restapi.app.service.parsing.model.CustomerIdParsingResult

interface CustomerIdParsingService {
    fun parseCustomerId(customerId: String): CustomerIdParsingResult
}
