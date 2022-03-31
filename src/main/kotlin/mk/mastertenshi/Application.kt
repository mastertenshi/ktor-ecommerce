package mk.mastertenshi

import io.ktor.server.application.*
import mk.mastertenshi.plugins.*

fun main(args: Array<String>): Unit =  io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureRouting()
    configureSerialization()
}
