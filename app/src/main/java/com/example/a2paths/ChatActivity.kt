package com.example.a2paths

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a2paths.databinding.ActivityChatBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    private var mBinding: ActivityChatBinding? = null
    private val binding get() = mBinding!!

    private val fireDatabase = FirebaseDatabase.getInstance().reference

    val firebase = Firebase.firestore
    private var chatRoomUid: String? = null
    private var destinationUid: String? = null
    private var uid: String? = null
    private var recyclerView: RecyclerView? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val send = findViewById<ImageView>(R.id.iv_send)
        val messageInput = findViewById<TextView>(R.id.et_messageInput)

        //메세지를 보낸 시간
        val time = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("MM월dd일 hh:mm")
        val curTime = dateFormat.format(Date(time)).toString()

        destinationUid = intent.getStringExtra("destinationUid")
        uid = Firebase.auth.currentUser?.uid.toString()
        recyclerView = findViewById(R.id.rv_chatActivity)


        send.setOnClickListener {
            Log.d("클릭 시 dest", "$destinationUid")
            val chatList = ChatList()
            chatList.users.put(uid.toString(), true)
            chatList.users.put(destinationUid!!, true)


            val comment = ChatList.Comment(uid, messageInput.text.toString(), curTime)
            if (chatRoomUid == null) {

                send.isEnabled = false
                fireDatabase.child("chatrooms").push().setValue(chatList).addOnSuccessListener {
                    //채팅방 생성
                    checkChatRoom()
                    //메세지 보내기
                    Handler().postDelayed({
                        println(chatRoomUid)
                        fireDatabase.child("chatrooms").child(chatRoomUid.toString()).child("comments").push().setValue(comment)
                        binding.etMessageInput.text = null
                    }, 1000L)
                    Log.d("chatUidNull dest", "$destinationUid")
                }
            } else {
                fireDatabase.child("chatrooms").child(chatRoomUid.toString()).child("comments").push().setValue(comment)
                binding.etMessageInput.text = null
                Log.d("chatUidNotNull dest", "$destinationUid")
            }
        }
        checkChatRoom()
    }

    private fun checkChatRoom() {
        fireDatabase.child("chatrooms").orderByChild("users/$uid").equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (item in snapshot.children) {
                        println(item)
                        val chatList = item.getValue<ChatList>()
                        if (chatList?.users!!.containsKey(destinationUid)) {
                            chatRoomUid = item.key
                            binding.ivSend.isEnabled = true
                            recyclerView?.layoutManager = LinearLayoutManager(this@ChatActivity)
                            recyclerView?.adapter = RecyclerViewAdapter()
                        }
                    }
                }
            })
    }


    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MessageViewHolder>() {

        private val comments = ArrayList<ChatList.Comment>()
        private var stuProfiles: StuProfiles? = null

        init {
            firebase.collection("user")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (document["uid"] == destinationUid.toString()) {
                            binding.tvTopName.text = document["name"].toString()
                            getMessageList()
                        }
                    }
                }
        }

        private fun getMessageList() {
            fireDatabase.child("chatrooms").child(chatRoomUid.toString()).child("comments")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        comments.clear()
                        for (data in snapshot.children) {
                            val item = data.getValue<ChatList.Comment>()
                            comments.add(item!!)
                            println(comments)
                        }
                        notifyDataSetChanged()
                        //메세지를 보낼 시 화면을 맨 밑으로 내림
                        recyclerView?.scrollToPosition(comments.size - 1)
                    }
                })

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)

            return MessageViewHolder(view)
        }

        @SuppressLint("RtlHardcoded")
        override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
            holder.textView_message.textSize = 20F
            holder.textView_message.text = comments[position].message
            holder.textView_time.text = comments[position].time
            if (comments[position].uid.equals(uid)) { // 본인 채팅
                holder.textView_message.setBackgroundResource(R.drawable.rightbubble)
                holder.textView_name.visibility = View.INVISIBLE
                holder.layout_destination.visibility = View.INVISIBLE
                holder.layout_main.gravity = Gravity.RIGHT
            } else { // 상대방 채팅
                holder.textView_name.text = stuProfiles?.name
                holder.layout_destination.visibility = View.VISIBLE
                holder.textView_name.visibility = View.VISIBLE
                holder.textView_message.setBackgroundResource(R.drawable.leftbubble)
                holder.layout_main.gravity = Gravity.LEFT
            }
        }

        inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView_message: TextView = view.findViewById(R.id.tv_message)
            val textView_name: TextView = view.findViewById(R.id.tv_messageName)
            val layout_destination: LinearLayout = view.findViewById(R.id.messageItem_layout_destination)
            val layout_main: LinearLayout = view.findViewById(R.id.messageItem_linearlayout_main)
            val textView_time: TextView = view.findViewById(R.id.tv_time)
        }

        override fun getItemCount(): Int {
            return comments.size
        }
    }
}