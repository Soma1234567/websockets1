package com.example.data

interface RecordService {

    suspend fun addRecord(record:Record)

    suspend fun getMessages():List<Record>

}