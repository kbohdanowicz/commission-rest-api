package com.kbohdanowicz.restapi.app.config.env
import io.github.cdimascio.dotenv.dotenv

object EnvironmentVariables {

    private val dotenv = dotenv()

    val restApiUsername: String = dotenv["REST_API_USERNAME"]
    val restApiPassword: String = dotenv["REST_API_PASSWORD"]

    val mongoUsername: String = dotenv["MONGO_USERNAME"]
    val mongoPassword: String = dotenv["MONGO_PASSWORD"]
}
