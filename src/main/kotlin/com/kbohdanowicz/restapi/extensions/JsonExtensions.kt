package com.kbohdanowicz.restapi.extensions

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> T.toJson() =
    Json.encodeToString(this)
