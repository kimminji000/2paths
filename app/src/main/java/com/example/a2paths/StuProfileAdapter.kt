package com.example.a2paths

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class StuProfileAdapter(private val stuProfileList: ArrayList<StuProfiles>) : RecyclerView.Adapter<StuProfileAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stu_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stuProfileList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.name.text = stuProfileList[position].name
        holder.number.text = stuProfileList[position].number.substring(2 until 4)
        holder.grade.text = stuProfileList[position].grade
        holder.state.text = stuProfileList[position].state

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, StuProfileActivity::class.java)
            intent.putExtra("number", stuProfileList[position].number)
            startActivity(holder.itemView.context, intent, null)
        }
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)!!
        val number = itemView.findViewById<TextView>(R.id.tv_number)!!
        val grade = itemView.findViewById<TextView>(R.id.tv_grade)!!
        val state = itemView.findViewById<TextView>(R.id.tv_state)!!
    }
}