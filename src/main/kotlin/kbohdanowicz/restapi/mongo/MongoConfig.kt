package kbohdanowicz.restapi.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import kbohdanowicz.restapi.dotenv.EnvironmentVariables
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoRepositories(basePackages = ["kbohdanowicz.restapi.mongo.repository"])
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
            "mongodb://$username:$password@$HOST:$PORT/$DATABASE_NAME?authSource=$AUTH_SOURCE"
        )

        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()
        return MongoClients.create(mongoClientSettings)
    }

    override fun getMappingBasePackages(): MutableCollection<String> =
        mutableListOf("kbohdanowicz.restapi")
}