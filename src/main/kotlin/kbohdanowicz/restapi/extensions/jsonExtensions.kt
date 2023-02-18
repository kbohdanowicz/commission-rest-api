package kbohdanowicz.restapi.extensions

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

inline fun <reified T> T.toJson() =
    Json.encodeToString(this)