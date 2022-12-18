package com.example.a2paths

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2paths.databinding.FragmentConsultProfBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ConsultProfFragment : Fragment() {

    private var mBinding: FragmentConsultProfBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val user = Firebase.auth.currentUser
    private val itemList = arrayListOf<ConsultProf>()
    private val adapter = ConsultProfAdapter(itemList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mBinding = FragmentConsultProfBinding.inflate(inflater, container, false)

        firebase.collection("prof")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document["id"].toString() == user?.email) {
                        val name = document["name"].toString()

                        firebase.collection("consult")
                            .get()
                            .addOnSuccessListener { result ->
                                itemList.clear()
                                for (document in result) {
                                    if (document["prof"] == name) {
                                        val item = ConsultProf(
                                            document["name"] as String,
                                            document["group"] as String,
                                            document["day"] as String,
                                            document["time"] as String,
                                            document["detail"] as String,
                                            document["state"] as String
                                        )
                                        itemList.add(item)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                    }
                }
            }

        binding.rvConsult.layoutManager = LinearLayoutManager(requireContext())
        binding.rvConsult.setHasFixedSize(true)
        binding.rvConsult.adapter = adapter

        return binding.root
    }
}