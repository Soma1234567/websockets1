package com.example.data

import kotlinx.serialization.Serializable

@Serializable
data class Record(
    val from:String,
    val message:String,
    val time:String
)
