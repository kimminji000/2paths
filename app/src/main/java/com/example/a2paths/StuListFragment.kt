package com.example.a2paths

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2paths.databinding.FragmentStuListBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StuListFragment : Fragment() {

    private var mBinding: FragmentStuListBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val user = Firebase.auth.currentUser
    private val itemList = arrayListOf<StuProfiles>()
    private val adapter = StuProfileAdapter(itemList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentStuListBinding.inflate(inflater, container, false)

        firebase.collection("user")
            .get()
            .addOnSuccessListener { result ->
                itemList.clear()
                for (document in result) {
                    if (document["id"] == user?.email) continue
                    val item = StuProfiles(
                        document["name"] as String,
                        document["number"] as String,
                        document["grade"] as String,
                        document["state"] as String
                    )
                    itemList.add(item)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("StuListFragment", "Error getting documents: $exception")
            }

        binding.rvStuprofile.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStuprofile.setHasFixedSize(true)
        binding.rvStuprofile.adapter = adapter
        binding.svStulist.setOnQueryTextListener(searchViewTextListener)

        return binding.root
    }

    var TAG = "StuListFragment"

    //서치뷰 리스너
    var searchViewTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    adapter.getFilter().filter(newText)
                    Log.d(TAG, "SearchView Text is changed:$newText")
                }
                return true
            }
        }

}//https://blog.naver.com/joymrk/222381231367