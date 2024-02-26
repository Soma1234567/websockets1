package com.example

import com.example.data.RecordSeriveImpl
import com.example.plugins.*
import io.ktor.server.application.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val db = KMongo.createClient()
        .getDatabase("chat")
        .coroutine
    val service = RecordSeriveImpl(db)
    val app = ChatApp(service)
    configureSockets()
    configureMonitoring()
    configureSerialization()
    configureRouting(app)
}
