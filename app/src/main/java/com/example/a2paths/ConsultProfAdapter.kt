package com.example.a2paths

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ConsultProfAdapter(private val consultProfList: ArrayList<ConsultProf>) : RecyclerView.Adapter<ConsultProfAdapter.CustomViewHolder>() {

    val firebase = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.consult_prof_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return consultProfList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.name.text = consultProfList[position].name
        holder.group.text = consultProfList[position].group
        holder.day.text = consultProfList[position].day
        holder.time.text = consultProfList[position].time
        holder.detail.text = consultProfList[position].detail
        holder.state.text = consultProfList[position].state

        if (consultProfList[position].state != "대기중") {
            holder.layout.visibility = View.GONE
        }

        if (consultProfList[position].state == "대기중") {
            val id = consultProfList[position].name + consultProfList[position].day + consultProfList[position].time
            holder.layout.visibility = View.VISIBLE

            holder.btnAccept.setOnClickListener {
                holder.state.text = "수락"
                holder.layout.visibility = View.GONE
                val data = hashMapOf(
                    "state" to holder.state.text,
                )
                firebase.collection("consult").document(id).set(data, SetOptions.merge())
            }

            holder.btnRefuse.setOnClickListener {
                holder.state.text = "거절"
                holder.layout.visibility = View.GONE
                val data = hashMapOf(
                    "state" to holder.state.text,
                )
                firebase.collection("consult").document(id).set(data, SetOptions.merge())
            }
        }
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)!!
        val group = itemView.findViewById<TextView>(R.id.tv_group)!!
        val day = itemView.findViewById<TextView>(R.id.tv_day)!!
        val time = itemView.findViewById<TextView>(R.id.tv_time)!!
        val detail = itemView.findViewById<TextView>(R.id.tv_detail)!!
        val state = itemView.findViewById<TextView>(R.id.tv_state)!!
        val btnAccept = itemView.findViewById<Button>(R.id.btn_accept)!!
        val btnRefuse = itemView.findViewById<Button>(R.id.btn_refuse)!!
        val layout = itemView.findViewById<LinearLayout>(R.id.linear_accept)!!
    }
}