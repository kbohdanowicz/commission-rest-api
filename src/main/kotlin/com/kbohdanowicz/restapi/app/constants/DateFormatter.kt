package com.kbohdanowicz.restapi.app.constants

import java.time.format.DateTimeFormatter
import java.util.*

val defaultDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
