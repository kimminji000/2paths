package com.example.a2paths

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ConsultProfAdapter(private val consultProfList: ArrayList<ConsultProf>) : RecyclerView.Adapter<ConsultProfAdapter.CustomViewHolder>() {

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
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)!!
        val group = itemView.findViewById<TextView>(R.id.tv_group)!!
        val day = itemView.findViewById<TextView>(R.id.tv_day)!!
        val time = itemView.findViewById<TextView>(R.id.tv_time)!!
        val detail = itemView.findViewById<TextView>(R.id.tv_detail)!!
    }
}