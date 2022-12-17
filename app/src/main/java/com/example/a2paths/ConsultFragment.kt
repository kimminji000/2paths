package com.example.a2paths

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2paths.databinding.FragmentConsultBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ConsultFragment : Fragment() {

    private var mBinding: FragmentConsultBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val user = Firebase.auth.currentUser
    private val itemList = arrayListOf<ConsultStu>()
    private val adapter = ConsultStuAdapter(itemList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mBinding = FragmentConsultBinding.inflate(inflater, container, false)

        binding.btnConsult.setOnClickListener {
            val intent = Intent(context, ConsultActivity::class.java)
            startActivity(intent)
        }

        firebase.collection("consult")
            .get()
            .addOnSuccessListener { result ->
                itemList.clear()
                for (document in result) {
                    if (document["email"] == user?.email) {
                        val item = ConsultStu(
                            document["prof"] as String,
                            document["group"] as String,
                            document["day"] as String,
                            document["time"] as String,
                            document["detail"] as String
                        )
                        itemList.add(item)
                    }
                }
                adapter.notifyDataSetChanged()
            }

        binding.rvConsult.layoutManager = LinearLayoutManager(requireContext())
        binding.rvConsult.setHasFixedSize(true)
        binding.rvConsult.adapter = adapter

        return binding.root
    }
}