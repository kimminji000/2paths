package com.example.a2paths

import kotlin.collections.HashMap

class ChatList (val users: HashMap<String, Boolean> = HashMap(),
                val comments : HashMap<String, Comment> = HashMap()){
        class Comment(val destinationName: String? = null, val uid: String? = null, val message: String? = null, val time: String? = null)
}
