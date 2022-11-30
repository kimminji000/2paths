package com.example.a2paths

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a2paths.databinding.ActivityMyClassBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyClassActivity : AppCompatActivity() {

    private var mBinding: ActivityMyClassBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val user = Firebase.auth.currentUser
    private val itemList = arrayListOf<ClassName>()
    private val adapter = ClassAdapter(itemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        mBinding = ActivityMyClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvClass.layoutManager = LinearLayoutManager(this)
        binding.rvClass.setHasFixedSize(true)
        binding.rvClass.adapter = adapter

        getClass()

        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }

        binding.btnAdd.setOnClickListener {
            val data = hashMapOf(
                "name" to binding.etClass.text.toString(),
            )
            firebase.collection(user?.email.toString()).document(binding.etClass.text.toString()).set(data)
            binding.etClass.text = null
            Toast.makeText(this, "수강과목이 추가되었습니다.", Toast.LENGTH_SHORT).show()

            getClass()
        }

        binding.btnDelete.setOnClickListener {

        }
    }

    inner class ClassAdapter(private val className: ArrayList<ClassName>) : RecyclerView.Adapter<ClassAdapter.CustomViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.class_item, parent, false)
            return CustomViewHolder(view)
        }

        override fun getItemCount(): Int {
            return className.size
        }

        inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name = itemView.findViewById<TextView>(R.id.tv_name)!!
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.name.text = className[position].name

            holder.itemView.setOnClickListener {
                //className[position].name
                //Toast.makeText(this, "수강과목이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                //notifyItemRemoved(position)
            }
        }
    }

    private fun getClass() { //내 수강과목 가져오기
        firebase.collection(user?.email.toString())
            .get()
            .addOnSuccessListener { result ->
                itemList.clear()
                for (document in result) {
                    val item = ClassName(
                        document["name"] as String
                    )
                    itemList.add(item)
                }
                adapter.notifyDataSetChanged()
            }
    }
}