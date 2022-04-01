package mk.mastertenshi.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import mk.mastertenshi.routes.productRouting

fun Application.configureRouting() {

    routing {
        productRouting()

        get("/") {
            call.respondText("Hello, friend")
        }

        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}
