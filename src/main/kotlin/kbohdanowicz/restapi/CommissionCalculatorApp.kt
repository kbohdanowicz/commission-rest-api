package kbohdanowicz.restapi

import kbohdanowicz.restapi.cache.TransactionsCache
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CommissionCalculatorApp

fun main() {
    // Object initialization
    TransactionsCache
    runApplication<CommissionCalculatorApp>()
}
