package kbohdanowicz.restapi

import kbohdanowicz.restapi.app.input.cache.TransactionsCache
import kbohdanowicz.restapi.app.config.env.EnvironmentVariables
import org.springframework.boot.runApplication

fun main() {
    initKotlinObjects()
    runApplication<CommissionCalculatorApp>()
}

private fun initKotlinObjects() {
    TransactionsCache
    EnvironmentVariables
}