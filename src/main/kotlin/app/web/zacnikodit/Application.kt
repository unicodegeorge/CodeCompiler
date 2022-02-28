package app.web.zacnikodit

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import app.web.zacnikodit.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
        configureSockets()
    }.start(wait = true)


}
