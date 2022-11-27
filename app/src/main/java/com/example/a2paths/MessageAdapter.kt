package com.example.a2paths

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.collections.ArrayList

class MessageAdapter (private val context: Context, private val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    // 받는 타입을 1 보내는 타입을 2 로 구분하여 View 객체를 받아오기
    private val receive = 1
    private val send = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            ReceiveViewHolder(view)
            }
        else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
            SendViewHolder(view)
        }
    }

    //View와 데이터 연결
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if(holder.javaClass == SendViewHolder::class.java){
            val viewHolder = holder as SendViewHolder
            viewHolder.sendMessage.text = currentMessage.message
            viewHolder.sendTime.text = currentMessage.time
        }else{
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.receiveName.text = currentMessage.receiverName
            viewHolder.receiveMessage.text = currentMessage.message
            viewHolder.sendTime.text = currentMessage.time
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage = messageList[position]

        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.sendId)) {
            send
        }else{
            receive
        }


    }
    // 보낸쪽 View를 전달받아서 객체 생성
    class SendViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sendMessage: TextView = itemView.findViewById(R.id.tv_send_message)
        val sendTime: TextView = itemView.findViewById(R.id.tv_send_time)

    }

    // 받는쪽 View를 전달받아서 객체 생성
    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val receiveName: TextView = itemView.findViewById(R.id.tv_receive_name)
        val receiveMessage: TextView = itemView.findViewById(R.id.tv_receive_message)
        val sendTime: TextView = itemView.findViewById(R.id.tv_send_time)
    }
}