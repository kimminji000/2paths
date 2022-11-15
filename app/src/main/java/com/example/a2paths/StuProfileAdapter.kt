package com.example.a2paths

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class StuProfileAdapter(val stuProfileList: ArrayList<StuProfiles>) :RecyclerView.Adapter<StuProfileAdapter.CustomViewHolder>(),Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : StuProfileAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stu_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredstuProfileList.size
    }

    override fun onBindViewHolder(holder: StuProfileAdapter.CustomViewHolder, position: Int) {

        /*
        holder.name.text = stuProfileList[position].name
        holder.stunum.text = stuProfileList[position].stunum
        holder.grade.text = stuProfileList[position].grade
        holder.state.text = stuProfileList[position].state
        */
        holder.name.text = filteredstuProfileList[position].name
        holder.stunum.text = filteredstuProfileList[position].stunum
        holder.grade.text = filteredstuProfileList[position].grade
        holder.state.text = filteredstuProfileList[position].state

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, StuProfileActivity::class.java)
            intent.putExtra("stunum", filteredstuProfileList[position].stunum)
            startActivity(holder.itemView.context, intent, null)

        }
    }

    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val stunum = itemView.findViewById<TextView>(R.id.tv_stunum)
        val grade = itemView.findViewById<TextView>(R.id.tv_grade)
        val state = itemView.findViewById<TextView>(R.id.tv_state)
    }


    var TAG = "StuProfileAdapter"
    val filteredstuProfileList = ArrayList<StuProfiles>()
    var itemFilter = ItemFilter()

    init {
        filteredstuProfileList.addAll(stuProfileList)
    }
    //filter

    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence?): FilterResults {
            var filterString = charSequence.toString()
            var results = FilterResults()
            Log.d(TAG, "charSequence : $charSequence")

            //검색이 x 원본배열 복제
            var filteredList: ArrayList<StuProfiles> = ArrayList<StuProfiles>()
            //공백제외 아무 값이 없을 경우엔 원본배열
            if (filterString.trim().isEmpty()) {
                results.values = stuProfileList
                results.count = stuProfileList.size

                return results
            } else {
                for (student in stuProfileList) {
                    if (student.name.contains(filterString) || student.state.contains(filterString)|| student.grade.contains(filterString)) {
                        filteredList.add(student)
                    }
                }

            }
            results.values = filteredList
            results.count = filteredList.size

            return results
        }


        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredstuProfileList.clear()
            filteredstuProfileList.addAll(results!!.values as ArrayList<StuProfiles>)
            notifyDataSetChanged()
        }
    }

}