package com.kbohdanowicz.restapi.app.config.mongo

import com.kbohdanowicz.restapi.app.config.env.EnvironmentVariables
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoRepositories(basePackages = ["com.kbohdanowicz.restapi.app.service.repository"])
@Configuration
class MongoConfig : AbstractMongoClientConfiguration() {

    companion object {
        private const val HOST = "localhost"
        private const val PORT = 27017
        private const val DATABASE_NAME = "commissionsDb"
        private const val AUTH_SOURCE = "admin"
    }

    override fun getDatabaseName(): String =
        DATABASE_NAME

    override fun mongoClient(): MongoClient {
        val username = EnvironmentVariables.mongoUsername
        val password = EnvironmentVariables.mongoPassword

        val connectionString = ConnectionString(
            "mongodb://$username:$password@$HOST:$PORT/$DATABASE_NAME?authSource=$AUTH_SOURCE",
        )

        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()
        return MongoClients.create(mongoClientSettings)
    }

    override fun getMappingBasePackages(): MutableCollection<String> =
        mutableListOf("kbohdanowicz.restapi")
}
