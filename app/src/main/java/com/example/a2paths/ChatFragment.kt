package com.example.a2paths

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList
import com.example.a2paths.Message as Message

class ChatFragment : Fragment() {
    companion object{
        fun newInstance() : ChatFragment {
            return ChatFragment()
        }
    }

    private val fireDatabase = FirebaseDatabase.getInstance().reference

    //메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //프레그먼트를 포함하고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    // 뷰가 생성되었을 때
    // 프레그먼트와 레이아웃을 연결시켜주는 부분
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_chatfragment)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecyclerViewAdapter()

        return view
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>(){
        private val chatList = ArrayList<ChatList>()
        private var uid : String? = null
        private val receiverUser : ArrayList<String> = arrayListOf()

        init{
            uid = Firebase.auth.currentUser?.uid.toString()
            // 불러오는 과정에서 데이터를 어떻게 가져오는게 좋을까나...
            fireDatabase.child("chats").child("messages").orderByChild("sendId").equalTo(true)
                .addValueEventListener(object :  ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    chatList.clear()
                    for (data in snapshot.children) {
                        chatList.add(data.getValue<ChatList>()!!)
                        println(data)
                    }
                    notifyDataSetChanged()
                }
            })
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

            return CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false))
        }

        inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView_title : TextView = itemView.findViewById(R.id.tv_chat_title)
            val textView_lastMessage : TextView = itemView.findViewById(R.id.tv_lastmessaging)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            var ReceiveruId: String? = null
            //채팅방에 있는 유저 모두 체크
            for (user in chatList[position].users.keys) {
                if (user != uid) {
                    ReceiveruId = user
                    receiverUser.add(ReceiveruId)
                }
            }
            fireDatabase.child("receiverId").child("$ReceiveruId").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val profile = snapshot.getValue<StuProfiles>()
                    holder.textView_title.text = profile?.name
                }
            })
            //메세지 내림차순 정렬 후 마지막 메세지의 키값을 가져
            val commentMap = TreeMap<String, ChatList.Comment>(reverseOrder())
            commentMap.putAll(chatList[position].comments)
            val lastMessageKey = commentMap.keys.toTypedArray()[0]
            holder.textView_lastMessage.text = chatList[position].comments[lastMessageKey]?.message

            //채팅창 선택 시 이동
            holder.itemView.setOnClickListener {
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("uid", receiverUser[position])
                context?.startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return chatList.size
        }
    }

}


