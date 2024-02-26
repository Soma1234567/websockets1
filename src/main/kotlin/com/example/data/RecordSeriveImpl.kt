package com.example.data

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class RecordSeriveImpl(
    db:CoroutineDatabase
):RecordService {
    val messages = db.getCollection<Record>("messages")


    override suspend fun addRecord(record: Record) {
        messages.insertOne(record)
    }

    override suspend fun getMessages(): List<Record> {
        val message = messages.find().toList()
        return message

    }

}