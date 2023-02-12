package kbohdanowicz.restapi.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDateTime(formatter: DateTimeFormatter): LocalDateTime =
    LocalDateTime.parse(this, formatter)

fun String.replaceCommaWithDot() =
    this.replace(',', '.')
