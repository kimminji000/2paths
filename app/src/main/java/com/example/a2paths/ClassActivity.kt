package com.example.a2paths

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a2paths.databinding.ActivityClassBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ClassActivity : AppCompatActivity() {

    private var mBinding: ActivityClassBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val itemList = arrayListOf<ClassName>()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        mBinding = ActivityClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvClass.layoutManager = LinearLayoutManager(this)
        binding.rvClass.adapter = RecyclerViewAdapter(itemList)

        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }
    }

    inner class RecyclerViewAdapter(private val className: ArrayList<ClassName>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val data = intent.getStringExtra("email")
        init {
            firebase.collection(data.toString())
                .get()
                .addOnSuccessListener { result ->
                    itemList.clear()
                    for (document in result) {
                        val item = ClassName(
                            document["name"] as String
                        )
                        itemList.add(item)
                    }
                    notifyDataSetChanged()
                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.class_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return className.size
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val name = itemView.findViewById<TextView>(R.id.tv_name)!!
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            holder.name.text = className[position].name
        }
    }
}