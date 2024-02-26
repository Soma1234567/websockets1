package com.example

import com.example.data.Record
import com.example.data.RecordSeriveImpl
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class ChatApp(
    val service:RecordSeriveImpl

) {
    val users = ConcurrentHashMap<String,WebSocketSession>()
    suspend fun sendMessage(record:Record){
        val actual_data = Json.encodeToString(record)
        users.values.forEach {
            it.send(Frame.Text(actual_data))
        }
        service.addRecord(record)
    }

     fun onJoin(username:String,session:WebSocketSession){
        users[username] = session
         println("-------------------------$username connected ------------------------")
    }
    suspend fun disconnect(username:String){
        users[username]?.close(CloseReason(CloseReason.Codes.NORMAL,"you said bye"))
    }

    suspend fun getAllMessages():List<Record>{
        return service.getMessages()
    }
}