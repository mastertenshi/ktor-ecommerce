package mk.mastertenshi.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.netty.handler.codec.http.HttpResponseStatus
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import mk.mastertenshi.MongoClient
import mk.mastertenshi.models.Product
import org.bson.Document


fun Application.productRouting() {
    routing {
        route("/api/products") {
            val productCollection = MongoClient.get().getDatabase("products").getCollection("product")

            get {
                val products = productCollection.find()
                val productsJson = products.map{ it.toJson() }.joinToString(", ", "[", "]")
                call.respondText(productsJson, ContentType.Application.Json, HttpStatusCode.OK)
            }

            get("/first") {
                val product = productCollection.find().first()
                product?.let {
                    call.respondText(it.toJson(), ContentType.Application.Json, HttpStatusCode.OK)
                } ?: call.respondText("No products in the database", status = HttpStatusCode.NotFound)
            }

            post {
                try {
                    val product = call.receive<Product>()
                    val productJson = Json.encodeToString(product)
                    productCollection.insertOne(Document.parse(productJson))
                    call.respondText("Product added",status = HttpStatusCode.Created)
                }
                catch (e: ConcurrentModificationException) {
                    call.respondText("Invalid Product data", status = HttpStatusCode.BadRequest)
                }
            }

            get("/add_sample") {
                val product = Product(
                    "Title",
                    "Category",
                    "Description",
                    listOf("img_url"),
                    mapOf(
                        "sizes" to listOf("S", "M", "L"),
                        "colors" to listOf("red", "blue", "white")
                    ),
                    mapOf("S" to 100, "M" to 110, "L" to 120)
                )
                val productJson = Json.encodeToString(product)
                productCollection.insertOne(Document.parse(productJson))
                call.respondText("Product added",status = HttpStatusCode.Created)
            }
        }
    }
}