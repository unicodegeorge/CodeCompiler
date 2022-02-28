package app.web.zacnikodit.plugins

import app.web.zacnikodit.managers.Compiler
import app.web.zacnikodit.utility.CodeScanner
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    routing {
        webSocket("/compiler/") {
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val incomingText = frame.readText()
                        if (incomingText.indexOf("class") != -1) {
                            val compiler = Compiler(incomingText)
                            println(CodeScanner(incomingText).getAmountOfInputs())
                            send("Running your code on our servers...")
                            try {
                                compiler.compile()
                                val linesOfOutput = compiler.getOutput()
                                send("Entered input : ${getStdInput()}")
                                for (line in linesOfOutput) {
                                    send(line)
                                }
                            } catch (ex: Exception) {
                                send(ex.message.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}
