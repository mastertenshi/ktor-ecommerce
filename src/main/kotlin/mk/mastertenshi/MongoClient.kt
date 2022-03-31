package mk.mastertenshi

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients

object MongoClient {
    private val connectionString = ConnectionString(System.getenv("MONGODB_SRV"))
    private val settings: MongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .serverApi(
            ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build()
        )
        .build()
    private val mongoClient = MongoClients.create(settings)

    fun get(): MongoClient = mongoClient

    fun close() {
        mongoClient.close()
    }
}