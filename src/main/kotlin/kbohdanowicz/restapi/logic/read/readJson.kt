package kbohdanowicz.restapi.logic.read

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

inline fun <reified T> readJson(path: String): T =
    Json.decodeFromString(getResourceAsString(path))