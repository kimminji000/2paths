package com.example.a2paths

import com.google.firebase.auth.FirebaseUser

data class Message(
    val message: String,
    var sendId: String?,
    var receiverId: String,
    val time: String?,
    val receiverName: String
    ){

    constructor():this("","","","", "")
}
