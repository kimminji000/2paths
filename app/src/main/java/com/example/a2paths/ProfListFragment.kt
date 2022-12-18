package com.example.a2paths

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2paths.databinding.FragmentProfListBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfListFragment : Fragment() {

    private var mBinding: FragmentProfListBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val prof = Firebase.auth.currentUser
    private val itemList = arrayListOf<ProfProfiles>()
    private val adapter = ProfProfileAdapter(itemList)

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mBinding = FragmentProfListBinding.inflate(inflater, container, false)

        firebase.collection("prof")
            .get()
            .addOnSuccessListener { result ->
                itemList.clear()
                for (document in result) {
                    if (document["id"] == prof?.email) continue
                    val item = ProfProfiles(
                        document["name"] as String,
                        document["office"] as String,
                        document["major"] as String,
                    )
                    itemList.add(item)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("ProfListFragment", "Error getting documents: $exception")
            }

        binding.rvProfprofile.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProfprofile.setHasFixedSize(true)
        binding.rvProfprofile.adapter = adapter

        return binding.root
    }
}