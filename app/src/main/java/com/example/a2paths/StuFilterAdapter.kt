package com.example.a2paths

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class StuFilterAdapter(private val stuProfileList: ArrayList<StuProfiles>) : RecyclerView.Adapter<StuFilterAdapter.CustomViewHolder>(),
    Filterable {

    var tag = "StuFilterAdapter"
    //필터링된 학생 데이터 리스트
    var filteredStuProfileList = ArrayList<StuProfiles>()
    //필터 결과
    var itemFilter = ItemFilter()


    init {
        for(stuUser in stuProfileList){
            filteredStuProfileList.add(stuUser)
        }
    }

    //StuList에 필터 결과 반환
    override fun getFilter(): Filter {
        return itemFilter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stu_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredStuProfileList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.name.text = filteredStuProfileList[position].name
        holder.number.text = filteredStuProfileList[position].number.substring(3 until 5)
        holder.grade.text = filteredStuProfileList[position].grade
        holder.state.text = filteredStuProfileList[position].state

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, StuProfileActivity::class.java)
            intent.putExtra("number", filteredStuProfileList[position].number)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)!!
        val number = itemView.findViewById<TextView>(R.id.tv_number)!!
        val grade = itemView.findViewById<TextView>(R.id.tv_grade)!!
        val state = itemView.findViewById<TextView>(R.id.tv_state)!!
    }


    //필터를 상속받는 클래스
    @Suppress("UNCHECKED_CAST")
    inner class ItemFilter : Filter() {
        //검색 필터링 수행
        override fun performFiltering(charSequence: CharSequence?): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()
            Log.d(tag, "charSequence : $charSequence")

            //검색이 필요없을 경우를 위해 원본배열 복제
            val filteredList: ArrayList<StuProfiles> = ArrayList<StuProfiles>() //: ArrayList<StuProfiles>
            //공백을 제외하고 아무 값이 없을 경우엔 원본 배열 리턴
            if (filterString.trim{it <= ' '}.isEmpty()) {
                results.values = stuProfileList
                results.count = stuProfileList.size

                return results
            } else {    //검색 글자가 있는 경우 해당되는 이름/학번/학년으로 검색
                for (student in stuProfileList) {
                    if (student.name.contains(filterString) || student.number.substring(3 until 5).contains(filterString)|| student.grade.contains(filterString)) {
                        filteredList.add(student)
                    }
                }
            }
            //필터링된(검색) 결과 리턴
            results.values = filteredList
            results.count = filteredList.size

            return results
        }


        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredStuProfileList.clear()
            filteredStuProfileList.addAll(results!!.values as ArrayList<StuProfiles>)
            notifyDataSetChanged()
        }
    }
}
