package com.example.a2paths

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.FieldPosition


class StuProfileAdapter(val stuprofileList: ArrayList<StuProfiles>) :RecyclerView.Adapter<StuProfileAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : StuProfileAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stu_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stuprofileList.size
    }

    override fun onBindViewHolder(holder: StuProfileAdapter.CustomViewHolder, position: Int) {
        holder.name.text = stuprofileList[position].name
        holder.stunum.text = stuprofileList[position].stunum
        holder.grade.text = stuprofileList[position].grade
        holder.state.text = stuprofileList[position].state
    }

    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val stunum = itemView.findViewById<TextView>(R.id.tv_stunum)
        val grade = itemView.findViewById<TextView>(R.id.tv_grade)
        val state = itemView.findViewById<TextView>(R.id.tv_state)
    }
}