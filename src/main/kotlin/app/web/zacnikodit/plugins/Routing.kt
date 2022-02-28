package app.web.zacnikodit.plugins

import app.web.zacnikodit.models.ParamsModel
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
private var stdInput = "undefined"

fun getStdInput(): String = stdInput


fun Application.configureRouting() {


    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/compile-code/"){
            val bodyParams = call.receive<ParamsModel>()
            stdInput = bodyParams.stdinput

        }
    }
}
