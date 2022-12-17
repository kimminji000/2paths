package com.example.a2paths

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ConsultStuAdapter(private val consultStuList: ArrayList<ConsultStu>) : RecyclerView.Adapter<ConsultStuAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.consult_stu_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return consultStuList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.prof.text = consultStuList[position].prof
        holder.group.text = consultStuList[position].group
        holder.day.text = consultStuList[position].day
        holder.time.text = consultStuList[position].time
        holder.detail.text = consultStuList[position].detail
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val prof = itemView.findViewById<TextView>(R.id.tv_prof)!!
        val group = itemView.findViewById<TextView>(R.id.tv_group)!!
        val day = itemView.findViewById<TextView>(R.id.tv_day)!!
        val time = itemView.findViewById<TextView>(R.id.tv_time)!!
        val detail = itemView.findViewById<TextView>(R.id.tv_detail)!!
    }
}