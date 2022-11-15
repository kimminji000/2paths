package com.example.a2paths

data class Message(
    val message: String,
    var sendId: String?
){
    constructor():this("","")
}
