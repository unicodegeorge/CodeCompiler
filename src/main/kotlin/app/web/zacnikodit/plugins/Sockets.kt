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

                        val compiler = Compiler(incomingText)
                        val amountOfInputs = CodeScanner(incomingText).getAmountOfInputs()
                        val inputs: MutableList<String> = mutableListOf()
                        send("Running your code on our servers...")

                        for (i in 1..amountOfInputs) {
                            send("Enter input number $i: ")
                            var x = amountOfInputs
                            for (inputFrame in incoming) {
                                when (inputFrame) {

                                    is Frame.Text -> {
                                        val receivedText = inputFrame.readText()
                                        inputs.add(receivedText)
                                        break
                                    }
                                    else -> {

                                    }
                                }
                            }
                        }
                        try {
                            compiler.compile(inputs)
                            val linesOfOutput = compiler.getOutput()

                            for (line in linesOfOutput) {
                                send(line)
                            }
                        } catch (ex: Exception) {
                            send(ex.printStackTrace().toString())
                        }
                    }
                }

            }
        }
    }

}

