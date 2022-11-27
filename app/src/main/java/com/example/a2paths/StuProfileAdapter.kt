package com.example.a2paths

import android.annotation.SuppressLint
//import android.content.Context
import android.content.Intent
//import android.provider.ContactsContract.Contacts.Photo
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
//import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context


class StuProfileAdapter(private val stuProfileList: ArrayList<StuProfiles>) : RecyclerView.Adapter<StuProfileAdapter.CustomViewHolder>(),Filterable {
    var TAG = "StuProfileAdapter"
    val filteredstuProfileList = ArrayList<StuProfiles>()   //필터링된 학생 데이터 리스트
    var itemFilter = ItemFilter()

    init {
        filteredstuProfileList.addAll(stuProfileList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stu_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredstuProfileList.size
    }

    override fun onBindViewHolder(holder: StuProfileAdapter.CustomViewHolder, position: Int) {

        holder.name.text = filteredstuProfileList[position].name
        holder.number.text = filteredstuProfileList[position].number.substring(3 until 5)
        holder.grade.text = filteredstuProfileList[position].grade
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


    //필터 결과 반환
    override fun getFilter(): Filter {
        return itemFilter
    }

    //검색 필터 수행
    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence?): FilterResults {
            val filterString = charSequence.toString()
            var results = FilterResults()
            Log.d(TAG, "charSequence : $charSequence")

            //검색 내용이 없을 떄는 원본배열 복제
            val filteredList = ArrayList<StuProfiles>() //: ArrayList<StuProfiles>
            //공백을 제외하고 아무 값이 없을 경우엔 원본 배열 리턴
            if (filterString.trim().isEmpty()) {
                results.values = stuProfileList
                results.count = stuProfileList.size

                return results
            } else {
                for (student in stuProfileList) {
                    if (student.name.contains(filterString) || student.number.substring(3 until 5).contains(filterString)|| student.grade.contains(filterString)) {
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
            filteredstuProfileList.addAll(results!!.values as Collection<StuProfiles>)  //as ArrayList<StuProfiles>
            notifyDataSetChanged()
        }
    }

}