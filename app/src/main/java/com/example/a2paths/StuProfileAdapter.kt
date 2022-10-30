package com.example.a2paths

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class StuProfileAdapter(val stuprofileList: ArrayList<StuProfiles>) :RecyclerView.Adapter<StuProfileAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : StuProfileAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stu_item, parent, false)
        return CustomViewHolder(view)/*.apply {
            itemView.setOnClickListener {
                val curPos : Int = adapterPosition
                val stuprofile: StuProfiles = stuprofileList.get(curPos)
            }
        }*/
    }

    override fun getItemCount(): Int {
        return stuprofileList.size
    }

    override fun onBindViewHolder(holder: StuProfileAdapter.CustomViewHolder, position: Int) {
        holder.name.text = stuprofileList[position].name
        holder.stunum.text = stuprofileList[position].stunum
        holder.grade.text = stuprofileList[position].grade
        holder.state.text = stuprofileList[position].state

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, ProfileActivity::class.java)
            intent.putExtra("stunum", stuprofileList[position].stunum)
            startActivity(holder.itemView.context, intent, null)
        }
    }

    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val stunum = itemView.findViewById<TextView>(R.id.tv_stunum)
        val grade = itemView.findViewById<TextView>(R.id.tv_grade)
        val state = itemView.findViewById<TextView>(R.id.tv_state)
    }


}