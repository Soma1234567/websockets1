package com.example.plugins

import com.example.ChatApp
import com.example.data.Record
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json
import java.util.*


fun Application.configureRouting(
    app:ChatApp
) {
    routing {
        get("/allmessages") {
            call.respond(
                HttpStatusCode.OK,
                app.getAllMessages()
            )
        }

    }
    routing{
        webSocket("/") {
            val username = call.parameters["username"]!!
            app.onJoin(username,this)
            incoming.consumeEach { frame->
                if(frame is Frame.Text){
                    val data = frame.readText()
                    val message = Json.decodeFromString<Record>(data)
                    app.sendMessage(message)
                }
            }
        }
    }
}
