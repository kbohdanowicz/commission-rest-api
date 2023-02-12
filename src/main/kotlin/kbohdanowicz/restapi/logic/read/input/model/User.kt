package kbohdanowicz.restapi.logic.read.input.model

import kotlinx.serialization.Serializable

@Serializable
data class User(val username: String, val password: String)