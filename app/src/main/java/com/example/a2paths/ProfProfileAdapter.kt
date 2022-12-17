package com.example.a2paths

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ProfProfileAdapter(private val profProfileList: ArrayList<ProfProfiles>) :
    RecyclerView.Adapter<ProfProfileAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prof_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return profProfileList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.name.text = profProfileList[position].name
        holder.office.text = profProfileList[position].office
        holder.major.text = profProfileList[position].major

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProfProfileActivity::class.java)
            intent.putExtra("name", profProfileList[position].name)
            startActivity(holder.itemView.context, intent, null)
        }
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)!!
        val office = itemView.findViewById<TextView>(R.id.tv_grade)!!
        val major = itemView.findViewById<TextView>(R.id.tv_state)!!
    }
}